package org.oxerr.seatgeek.client.cached.redisson;

import java.io.IOException;

import org.oxerr.seatgeek.client.ListingService;
import org.oxerr.seatgeek.client.cached.CachedListingService;
import org.oxerr.seatgeek.client.cached.model.Listing;
import org.redisson.api.RedissonClient;

public class RedissonCachedListingService extends RedissonCachedListingServiceSupport implements CachedListingService {

	private final ListingService listingService;

	public RedissonCachedListingService(ListingService listingService, RedissonClient redisson, String keyPrefix) {
		super(redisson, keyPrefix);
		this.listingService = listingService;
	}

	protected void doDelete(String ticketId) throws IOException {
		this.listingService.deleteListing(ticketId);
	}

	protected void doCreate(Listing listing) throws IOException {
		this.listingService.createListing(listing.getId(), listing.getRequest());
	}

}
