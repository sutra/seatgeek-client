package org.oxerr.seatgeek.client.cached.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.oxerr.seatgeek.model.request.CreateListingRequest;

public class Listing implements Serializable {

	private static final long serialVersionUID = 2023031901L;

	private String id;

	private CreateListingRequest request;

	public Listing() {
	}

	public Listing(String id, CreateListingRequest request) {
		this.id = id;
		this.request = request;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
		return EqualsBuilder.reflectionEquals(this, obj);
	}

}
