package org.oxerr.seatgeek.client.cached.redisson;

import java.io.IOException;

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
	extends RedissonCachedListingServiceSupport<CreateListingRequest, SeatGeekListing, SeatGeekEvent, SeatGeekCachedListing>
	implements CachedSeatGeekListingService {

	private final ListingService listingService;

	public RedissonCachedListingService(
		ListingService listingService,
		RedissonClient redissonClient,
		String keyPrefix,
		boolean create
	) {
		super(redissonClient, keyPrefix, create);
		this.listingService = listingService;
	}

	protected void doDelete(String ticketId) throws IOException {
		this.listingService.deleteListing(ticketId);
	}

	protected void doCreate(SeatGeekEvent event, SeatGeekListing listing) throws IOException {
		this.listingService.createListing(listing.getId(), listing.getRequest());
	}

	@Override
	protected SeatGeekCachedListing toCached(SeatGeekEvent event, SeatGeekListing listing, Status status) {
		return new SeatGeekCachedListing(listing, status);
	}

}
