package org.oxerr.seatgeek.model.request;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.oxerr.seatgeek.model.AbstractListing;

public class UpdateListingRequest extends AbstractListing {

	private static final long serialVersionUID = 2023031501L;

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
		UpdateListingRequest rhs = (UpdateListingRequest) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

}
