package org.oxerr.seatgeek.client.cached;

import org.oxerr.seatgeek.client.cached.model.Event;
import org.oxerr.seatgeek.client.cached.model.Status;

public interface CachedListingService {

	void updateEvent(Event event);

	/**
	 * Returns the size of the cache.
	 *
	 * @return the size of the cache.
	 */
	long cacheSize();

	/**
	 * Returns the listing count which status is {@link Status#LISTED}
	 * @return
	 */
	long listedCount();

}
