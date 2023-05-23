package org.oxerr.seatgeek.client.cached.redisson.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.oxerr.seatgeek.client.cached.model.SeatGeekListing;
import org.oxerr.seatgeek.model.request.CreateListingRequest;
import org.oxerr.ticket.inventory.support.cached.redisson.Status;

public class SeatGeekCachedListing
	extends org.oxerr.ticket.inventory.support.cached.redisson.CachedListing<CreateListingRequest> {

	private static final long serialVersionUID = 2023031801L;

	public static SeatGeekCachedListing pending(SeatGeekListing listing) {
		return new SeatGeekCachedListing(Status.PENDING_LIST, listing.getRequest());
	}

	public static SeatGeekCachedListing listed(SeatGeekListing listing) {
		return new SeatGeekCachedListing(Status.LISTED, listing.getRequest());
	}

	public static boolean shouldCreate(
		@Nonnull SeatGeekListing listing,
		@Nullable SeatGeekCachedListing cachedListing) {
		return org.oxerr.ticket.inventory.support.cached.redisson.CachedListing.shouldCreate(listing, cachedListing);
	}

	public SeatGeekCachedListing() {
	}

	public SeatGeekCachedListing(Status status, CreateListingRequest request) {
		super(status, request);
	}

}
