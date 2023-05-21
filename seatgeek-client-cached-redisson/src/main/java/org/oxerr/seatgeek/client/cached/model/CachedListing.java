package org.oxerr.seatgeek.client.cached.model;

import org.oxerr.seatgeek.client.cached.redisson.model.Status;
import org.oxerr.seatgeek.model.request.CreateListingRequest;

/**
 * @deprecated Use org.oxerr.seatgeek.client.cached.redisson.model.CachedListing instead.
 */
@Deprecated
public class CachedListing extends org.oxerr.seatgeek.client.cached.redisson.model.CachedListing {

	private static final long serialVersionUID = 2023031801L;

	public CachedListing() {
		super();
	}

	public CachedListing(Status status, CreateListingRequest request) {
		super(status, request);
	}

}
