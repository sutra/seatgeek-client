package org.oxerr.seatgeek.client.cached;

import org.oxerr.seatgeek.client.cached.model.Event;

public interface CachedListingService {

	void updateEvent(Event event);

	/**
	 * Returns the size of the cache.
	 *
	 * @return the size of the cache.
	 */
	long cacheSize();

	/**
	 * Returns the listing count which status is LISTED.
	 *
	 * @return the listing count which status is LISTED.
	 */
	long listedCount();

}
