package org.oxerr.seatgeek.client.cached.model;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;

import org.oxerr.seatgeek.model.request.CreateListingRequest;
import org.oxerr.ticket.inventory.support.Event;

public class SeatGeekEvent extends Event<CreateListingRequest, SeatGeekListing> {

	private static final long serialVersionUID = 2023031901L;

	public SeatGeekEvent() {
		this(null, null, Collections.emptyList());
	}

	public SeatGeekEvent(String id, OffsetDateTime startDate) {
		this(id, startDate, Collections.emptyList());
	}

	public SeatGeekEvent(String id, OffsetDateTime startDate, List<SeatGeekListing> listings) {
		super(id, startDate, listings);
	}

}
