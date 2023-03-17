package org.oxerr.seatgeek.model.response;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.oxerr.seatgeek.model.request.CreateListingRequest;

public class Listing extends CreateListingRequest {

	private static final long serialVersionUID = 20230315L;

	/**
	 * An identifier for this listing that is unique to your account.
	 */
	private String ticketId;

	private Long eventId;

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
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
