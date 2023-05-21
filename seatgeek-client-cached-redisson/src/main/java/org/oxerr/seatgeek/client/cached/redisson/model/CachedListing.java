package org.oxerr.seatgeek.client.cached.redisson.model;

import java.io.Serializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.oxerr.seatgeek.client.cached.model.Listing;
import org.oxerr.seatgeek.model.request.CreateListingRequest;

public class CachedListing implements Serializable {

	private static final long serialVersionUID = 2023031801L;

	private Status status;

	private CreateListingRequest request;

	public static CachedListing pending(Listing listing) {
		return new CachedListing(Status.PENDING_LIST, listing.getRequest());
	}

	public static CachedListing listed(Listing listing) {
		return new CachedListing(Status.LISTED, listing.getRequest());
	}

	public static boolean shouldCreate(
		@Nonnull Listing listing,
		@Nullable  CachedListing cachedListing) {
		return cachedListing == null
			|| cachedListing.getStatus() == Status.PENDING_LIST
			|| !cachedListing.getRequest().equals(listing.getRequest());
	}

	public CachedListing() {
	}

	public CachedListing(Status status, CreateListingRequest request) {
		this.status = status;
		this.request = request;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public CreateListingRequest getRequest() {
		return request;
	}

	public void setRequest(CreateListingRequest request) {
		this.request = request;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		CachedListing rhs = (CachedListing) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

}
