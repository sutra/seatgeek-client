package org.oxerr.seatgeek.client.cached.model;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Event {

	private String id;

	private Instant eventDate;

	private List<Listing> listings;

	public Event() {
		this(null, null, Collections.emptyList());
	}

	public Event(String id, Instant eventDate) {
		this(id, eventDate, Collections.emptyList());
	}

	public Event(String id, Instant eventDate, List<Listing> listings) {
		this.id = id;
		this.eventDate = eventDate;
		this.listings = listings;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Instant getEventDate() {
		return eventDate;
	}

	public void setEventDate(Instant eventDate) {
		this.eventDate = eventDate;
	}

	public List<Listing> getListings() {
		return listings;
	}

	public void setListings(List<Listing> listings) {
		this.listings = listings;
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
		Event rhs = (Event) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

}
