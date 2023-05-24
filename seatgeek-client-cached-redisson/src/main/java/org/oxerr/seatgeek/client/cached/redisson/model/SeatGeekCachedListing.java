package org.oxerr.seatgeek.client.cached.redisson.model;

import org.oxerr.seatgeek.client.cached.model.SeatGeekListing;
import org.oxerr.seatgeek.model.request.CreateListingRequest;
import org.oxerr.ticket.inventory.support.cached.redisson.Status;

public class SeatGeekCachedListing
	extends org.oxerr.ticket.inventory.support.cached.redisson.CachedListing<CreateListingRequest> {

	private static final long serialVersionUID = 2023031801L;

	public SeatGeekCachedListing() {
	}

	public SeatGeekCachedListing(SeatGeekListing listing, Status status) {
		this(status, listing.getRequest());
	}

	public SeatGeekCachedListing(Status status, CreateListingRequest request) {
		super(status, request);
	}

}
