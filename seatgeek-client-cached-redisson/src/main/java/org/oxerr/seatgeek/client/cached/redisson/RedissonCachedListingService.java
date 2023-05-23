package org.oxerr.seatgeek.client.cached.redisson;

import java.io.IOException;

import org.oxerr.seatgeek.client.ListingService;
import org.oxerr.seatgeek.client.cached.CachedSeatGeekListingService;
import org.oxerr.seatgeek.client.cached.model.SeatGeekEvent;
import org.oxerr.seatgeek.client.cached.model.SeatGeekListing;
import org.oxerr.seatgeek.model.request.CreateListingRequest;
import org.oxerr.ticket.inventory.support.cached.redisson.RedissonCachedListingServiceSupport;
import org.redisson.api.RedissonClient;

public class RedissonCachedListingService
	extends RedissonCachedListingServiceSupport<CreateListingRequest, SeatGeekListing, SeatGeekEvent>
	implements CachedSeatGeekListingService {

	private final ListingService listingService;

	public RedissonCachedListingService(ListingService listingService, RedissonClient redisson, String keyPrefix) {
		super(redisson, keyPrefix);
		this.listingService = listingService;
	}

	protected void doDelete(String ticketId) throws IOException {
		this.listingService.deleteListing(ticketId);
	}

	protected void doCreate(SeatGeekListing listing) throws IOException {
		this.listingService.createListing(listing.getId(), listing.getRequest());
	}

}
