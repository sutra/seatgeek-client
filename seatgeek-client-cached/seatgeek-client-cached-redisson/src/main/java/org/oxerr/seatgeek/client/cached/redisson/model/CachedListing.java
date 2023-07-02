package org.oxerr.seatgeek.client.cached.redisson.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.oxerr.seatgeek.client.cached.model.SeatGeekListing;
import org.oxerr.seatgeek.client.model.request.CreateListingRequest;
import org.oxerr.ticket.inventory.support.cached.redisson.Status;

/**
 * 
 * @deprecated Use {@link SeatGeekCachedListing} instead.
 */
@Deprecated
public class CachedListing extends SeatGeekCachedListing {

	private static final long serialVersionUID = 2023031801L;

	public static CachedListing pending(SeatGeekListing listing) {
		return new CachedListing(Status.PENDING_LIST, listing.getRequest());
	}

	public static CachedListing listed(SeatGeekListing listing) {
		return new CachedListing(Status.LISTED, listing.getRequest());
	}

	public static boolean shouldCreate(
		@Nonnull SeatGeekListing listing,
		@Nullable  CachedListing cachedListing) {
		return cachedListing == null
			|| cachedListing.getStatus() == Status.PENDING_LIST
			|| !cachedListing.getRequest().equals(listing.getRequest());
	}

	public CachedListing() {
	}

	public CachedListing(Status status, CreateListingRequest request) {
		super(status, request);
	}

}
