package org.oxerr.seatgeek.client.cached;

import org.oxerr.seatgeek.client.cached.model.SeatGeekEvent;
import org.oxerr.seatgeek.client.cached.model.SeatGeekListing;
import org.oxerr.seatgeek.client.model.request.CreateListingRequest;
import org.oxerr.ticket.inventory.support.cached.CachedListingService;

public interface CachedSeatGeekListingService
	extends CachedListingService<String, String, CreateListingRequest, SeatGeekListing, SeatGeekEvent> {
}
