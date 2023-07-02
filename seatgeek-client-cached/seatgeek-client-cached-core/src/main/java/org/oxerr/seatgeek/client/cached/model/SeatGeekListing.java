package org.oxerr.seatgeek.client.cached.model;

import org.oxerr.seatgeek.client.model.request.CreateListingRequest;
import org.oxerr.ticket.inventory.support.Listing;

public class SeatGeekListing extends Listing<String, CreateListingRequest> {

	private static final long serialVersionUID = 2023031901L;

	public SeatGeekListing() {
		super();
	}

	public SeatGeekListing(String id, CreateListingRequest request) {
		super(id, request);
	}

}
