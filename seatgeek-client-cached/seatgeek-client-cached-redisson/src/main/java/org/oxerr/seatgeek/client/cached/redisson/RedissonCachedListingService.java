package org.oxerr.seatgeek.client.cached.redisson;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;

import org.oxerr.seatgeek.client.ListingService;
import org.oxerr.seatgeek.client.cached.CachedSeatGeekListingService;
import org.oxerr.seatgeek.client.cached.model.SeatGeekEvent;
import org.oxerr.seatgeek.client.cached.model.SeatGeekListing;
import org.oxerr.seatgeek.client.cached.redisson.model.SeatGeekCachedListing;
import org.oxerr.seatgeek.client.model.request.CreateListingRequest;
import org.oxerr.ticket.inventory.support.cached.redisson.RedissonCachedListingServiceSupport;
import org.oxerr.ticket.inventory.support.cached.redisson.Status;
import org.redisson.api.RedissonClient;

public class RedissonCachedListingService
	extends RedissonCachedListingServiceSupport<String, String, CreateListingRequest, SeatGeekListing, SeatGeekEvent, SeatGeekCachedListing>
	implements CachedSeatGeekListingService {

	private final ListingService listingService;

	public RedissonCachedListingService(
		ListingService listingService,
		RedissonClient redissonClient,
		String keyPrefix,
		boolean create
	) {
		this(listingService, redissonClient, keyPrefix, ForkJoinPool.commonPool(), create);
	}

	public RedissonCachedListingService(
		ListingService listingService,
		RedissonClient redissonClient,
		String keyPrefix,
		Executor executor,
		boolean create
	) {
		super(redissonClient, keyPrefix, executor, create);
		this.listingService = listingService;
	}

	protected void deleteListing(SeatGeekEvent event, String ticketId) throws IOException {
		this.listingService.deleteListing(ticketId);
	}

	protected void createListing(SeatGeekEvent event, SeatGeekListing listing) throws IOException {
		this.listingService.createListing(listing.getId(), listing.getRequest());
	}

	@Override
	protected SeatGeekCachedListing toCached(SeatGeekEvent event, SeatGeekListing listing, Status status) {
		return new SeatGeekCachedListing(listing, status);
	}

}
