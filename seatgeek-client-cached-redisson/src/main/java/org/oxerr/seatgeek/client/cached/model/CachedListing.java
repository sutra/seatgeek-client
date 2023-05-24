package org.oxerr.seatgeek.client.cached.model;

import org.oxerr.seatgeek.client.cached.redisson.model.SeatGeekCachedListing;
import org.oxerr.seatgeek.model.request.CreateListingRequest;
import org.oxerr.ticket.inventory.support.cached.redisson.Status;

/**
 * @deprecated Use org.oxerr.seatgeek.client.cached.redisson.model.CachedListing instead.
 */
@Deprecated
public class CachedListing extends SeatGeekCachedListing {

	private static final long serialVersionUID = 2023031801L;

	public CachedListing() {
		super();
	}

	public CachedListing(Status status, CreateListingRequest request) {
		super(status, request);
	}

}
