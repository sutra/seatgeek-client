package org.oxerr.seatgeek.client.rescu;

import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;

import java.io.IOException;
import java.util.Optional;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.oxerr.seatgeek.client.ListingService;
import org.oxerr.seatgeek.client.model.SeatGeekException;
import org.oxerr.seatgeek.client.model.request.CreateListingRequest;
import org.oxerr.seatgeek.client.model.request.UpdateListingRequest;
import org.oxerr.seatgeek.client.model.response.Listing;
import org.oxerr.seatgeek.client.model.response.MultipleListingsResponse;
import org.oxerr.seatgeek.client.model.response.Response;

import si.mazi.rescu.HttpStatusIOException;

public class ListingServiceImpl implements ListingService {

	private final ListingResource listingResource;

	public ListingServiceImpl(ListingResource listingResource) {
		this.listingResource = listingResource;
	}

	@Override
	public void createListing(String ticketId, CreateListingRequest r) throws IOException {
		Response response = this.listingResource.createListing(ticketId, r);
		if (!response.getOk().booleanValue()) {
			throw new SeatGeekException();
		}
	}

	@Override
	public UpdateListingRequest updateListing(String ticketId, UpdateListingRequest r) throws IOException {
		return this.listingResource.updateListing(ticketId, r);
	}

	@Override
	public Optional<Listing> getListing(String ticketId) throws IOException {
		try {
			return Optional.ofNullable(this.listingResource.getListing(ticketId).getListing());
		} catch (HttpStatusIOException e) {
			if (e.getHttpStatusCode() == NOT_FOUND.getStatusCode()) {
				return Optional.empty();
			} else {
				throw e;
			}
		}
	}

	@Override
	public MultipleListingsResponse getListings(
		Integer page,
		Integer perPage,
		Boolean onlyBarcode,
		String... listingIds
	) throws IOException {
		String commaSeperatedListingIds = ArrayUtils.getLength(listingIds) == 0 ? null : StringUtils.join(listingIds, ',');
		return this.listingResource.getListings(
			commaSeperatedListingIds,
			BooleanUtils.toIntegerObject(onlyBarcode),
			page,
			perPage
		);
	}

	@Override
	public void deleteListing(String ticketId) throws IOException {
		try {
			this.listingResource.deleteListing(ticketId);
		} catch (HttpStatusIOException e) {
			if (e.getHttpStatusCode() != NOT_FOUND.getStatusCode()) {
				throw e;
			}
		}
	}

}
