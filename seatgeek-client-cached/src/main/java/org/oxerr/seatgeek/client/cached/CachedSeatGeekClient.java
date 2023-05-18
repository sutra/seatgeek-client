package org.oxerr.seatgeek.client.cached;

import org.oxerr.seatgeek.client.SeatGeekClient;

public interface CachedSeatGeekClient {

	SeatGeekClient getClient();

	CachedListingService getCachedListingService();

}
