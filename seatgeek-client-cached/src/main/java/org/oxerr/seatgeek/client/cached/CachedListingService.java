package org.oxerr.seatgeek.client.cached;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.oxerr.seatgeek.client.ListingService;
import org.oxerr.seatgeek.client.cached.model.CachedListing;
import org.oxerr.seatgeek.client.cached.model.Event;
import org.oxerr.seatgeek.client.cached.model.Listing;
import org.redisson.api.RMapCache;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;

public class CachedListingService {

	private final Logger log = LogManager.getLogger();

	private final ListingService listingService;

	private final RedissonClient redisson;

	/**
	 * The key prefix for Redis entries(lock & cache).
	 */
	private final String keyPrefix;

	// Event ID -> <Ticket ID, CachedListing>
	private final RMapCache<String, Map<String, CachedListing>> listingsCache;

	public CachedListingService(
		ListingService listingService,
		RedissonClient redisson,
		String keyPrefix
	) {
		this.listingService = listingService;
		this.redisson = redisson;
		this.keyPrefix = keyPrefix;
		String cacheName = String.format("%s:listings", keyPrefix);
		this.listingsCache = redisson.getMapCache(cacheName);
	}

	public RMapCache<String, Map<String, CachedListing>> getListingCache() {
		return this.listingsCache;
	}

	public void updateEvent(Event event) {
		String lockName = String.format("%s:event:lock:%s", keyPrefix, event.getId());
		RReadWriteLock rwLock = redisson.getReadWriteLock(lockName);

		rwLock.writeLock().lock();

		try {
			this.doUpdateEvent(event);
		} finally {
			rwLock.writeLock().unlock();
		}
	}

	private void doUpdateEvent(Event event) {
		Map<String, CachedListing> cache = this.getOrCreateCache(event);

		try {
			this.updateEvent(event, cache);
		} finally {
			this.saveCache(event, cache);
		}
	}

	private void updateEvent(Event event, Map<String, CachedListing> cache) {
		// delete
		this.delete(event, cache);

		// create/update
		this.create(event, cache);
	}

	private void delete(Event event, Map<String, CachedListing> cache) {
		Set<String> inventoryTicketIds = event.getListings()
			.stream()
			.map(Listing::getTicketId)
			.collect(Collectors.toSet());

		Set<String> pendingDeleteTicketIds = cache.keySet()
			.stream()
			.filter(t -> !inventoryTicketIds.contains(t))
			.collect(Collectors.toSet());

		for (String ticketId : pendingDeleteTicketIds) {
			log.trace("Deleting {}", ticketId);

			try {
				this.listingService.deleteListing(ticketId);
				cache.remove(ticketId);
			} catch (IOException e) {
				log.warn("Delete ticket {} failed.", ticketId, e);
			}
		}
	}

	private void create(Event event, Map<String, CachedListing> cache) {
		List<Listing> pendingCreateListings = event.getListings()
			.stream()
			.filter(listing -> CachedListing.shouldCreate(listing, cache.get(listing.getTicketId())))
			.collect(Collectors.toList());

		Map<String, CachedListing> pendings = pendingCreateListings
			.stream()
			.collect(Collectors.toMap(Listing::getTicketId, CachedListing::pending));

		cache.putAll(pendings);

		// Make sure the cache is saved even if program is killed
		this.saveCache(event, cache);

		// create
		for (Listing listing : pendingCreateListings) {
			log.trace("Creating {}", listing.getTicketId());

			try {
				this.listingService.createListing(listing.getTicketId(), listing.getRequest());
				cache.put(listing.getTicketId(), CachedListing.listed(listing));
			} catch (IOException e) {
				log.warn("Create ticket {} failed.", listing.getTicketId(), e);
			}
		}
	}

	private Map<String, CachedListing> getOrCreateCache(Event event) {
		Map<String, CachedListing> cache = this.listingsCache.get(event.getId());
		return Optional.ofNullable(cache).orElseGet(HashMap::new);
	}

	private void saveCache(Event event, Map<String, CachedListing> listings) {
		long ttl = ttl(event);
		this.listingsCache.fastPut(event.getId(), listings, ttl, TimeUnit.MINUTES);
	}

	private long ttl(Event event) {
		return Math.max(1, Duration.between(Instant.now(), event.getEventDate()).toMinutes());
	}

}
