package org.oxerr.seatgeek.client.rescu;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.oxerr.seatgeek.client.ListingService;
import org.oxerr.seatgeek.model.SeatGeekException;
import org.oxerr.seatgeek.model.request.CreateListingRequest;
import org.oxerr.seatgeek.model.request.UpdateListingRequest;
import org.oxerr.seatgeek.model.response.Listing;
import org.oxerr.seatgeek.model.response.MultipleListingsResponse;
import org.oxerr.seatgeek.model.response.Response;

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
	public Listing getListing(String ticketId) throws IOException {
		try {
			return this.listingResource.getListing(ticketId).getListing();
		} catch (HttpStatusIOException e) {
			if (e.getHttpStatusCode() == 404) {
				return null;
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
		String commaSeperatedListingIds = StringUtils.join(listingIds, ',');
		return this.listingResource.getListings(
			commaSeperatedListingIds,
			onlyBarcode != null && onlyBarcode.booleanValue() ? 1 : 0,
			page,
			perPage
		);
	}

	@Override
	public void deleteListing(String ticketId) throws IOException {
		try {
			this.listingResource.deleteListing(ticketId);
		} catch (HttpStatusIOException e) {
			if (e.getHttpStatusCode() != 404) {
				throw e;
			}
		}
	}

}
