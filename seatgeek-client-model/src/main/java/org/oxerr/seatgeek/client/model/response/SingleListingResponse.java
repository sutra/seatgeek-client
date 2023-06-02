package org.oxerr.seatgeek.client.model.response;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class SingleListingResponse extends Response {

	private static final long serialVersionUID = 2023031501L;

	private Listing listing;

	public Listing getListing() {
		return listing;
	}

	public void setListing(Listing listing) {
		this.listing = listing;
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
		SingleListingResponse rhs = (SingleListingResponse) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

}
