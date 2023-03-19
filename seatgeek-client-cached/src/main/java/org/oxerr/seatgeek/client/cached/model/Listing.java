package org.oxerr.seatgeek.client.cached.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.oxerr.seatgeek.model.request.CreateListingRequest;

public class Listing {

	private String ticketId;

	private CreateListingRequest request;

	public Listing() {
	}

	public Listing(String ticketId, CreateListingRequest request) {
		this.ticketId = ticketId;
		this.request = request;
	}

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
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
		Listing rhs = (Listing) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

}
