package org.oxerr.seatgeek.client.cached.model;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Event implements Serializable {

	private static final long serialVersionUID = 2023031901L;

	/**
	 * The event identifier.
	 */
	private String id;

	/**
	 * The date when the event starts.
	 */
	private OffsetDateTime startDate;

	private List<Listing> listings;

	public Event() {
		this(null, null, Collections.emptyList());
	}

	public Event(String id, OffsetDateTime startDate) {
		this(id, startDate, Collections.emptyList());
	}

	public Event(String id, OffsetDateTime startDate, List<Listing> listings) {
		this.id = id;
		this.startDate = startDate;
		this.listings = listings;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public OffsetDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(OffsetDateTime startDate) {
		this.startDate = startDate;
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
		return EqualsBuilder.reflectionEquals(this, obj);
	}

}
