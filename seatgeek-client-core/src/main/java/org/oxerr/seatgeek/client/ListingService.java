package org.oxerr.seatgeek.client;

import java.io.IOException;
import java.util.Optional;

import org.oxerr.seatgeek.model.request.CreateListingRequest;
import org.oxerr.seatgeek.model.request.UpdateListingRequest;
import org.oxerr.seatgeek.model.response.Listing;
import org.oxerr.seatgeek.model.response.MultipleListingsResponse;

public interface ListingService {

	void createListing(String ticketId, CreateListingRequest r)
		throws IOException;

	UpdateListingRequest updateListing(String ticketId, UpdateListingRequest r)
		throws IOException;

	Optional<Listing> getListing(String ticketId) throws IOException;

	MultipleListingsResponse getListings(
		Integer page,
		Integer perPage,
		Boolean onlyBarcode,
		String... listingIds
	) throws IOException;

	void deleteListing(String ticketId) throws IOException;

}
