package org.oxerr.seatgeek.client.rescu;

import java.io.IOException;

import org.oxerr.seatgeek.model.request.CreateListingRequest;
import org.oxerr.seatgeek.model.request.UpdateListingRequest;
import org.oxerr.seatgeek.model.response.MultipleListingsResponse;
import org.oxerr.seatgeek.model.response.Response;
import org.oxerr.seatgeek.model.response.SingleListingResponse;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/")
public interface ListingResource {

	@PUT
	@Path("/listings/single/{ticket_id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	Response createListing(
		@PathParam("ticket_id") String ticketId,
		CreateListingRequest r
	) throws IOException;

	@PATCH
	@Path("/listings/single/{ticket_id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	UpdateListingRequest updateListing(
		@PathParam("ticket_id") String ticketId,
		UpdateListingRequest r
	) throws IOException;

	@GET
	@Path("/listings/single/{ticket_id}")
	SingleListingResponse getListing(
		@PathParam("ticket_id") String ticketId
	) throws IOException;

	/**
	 * Retrieves multiple listings at once.
	 *
	 * @param listingIds Comma-separated ticket_ids.
	 * @param onlyBarcode Defaults to 0. If 1, only return listings that require barcodes.
	 * @param page Which page of results?
	 * @param perPage Default 500. How many orders per page?
	 * @return multiple listings.
	 * @throws IOException indicates any I/O exception.
	 */
	@GET
	@Path("/listings")
	MultipleListingsResponse getListings(
		@QueryParam("listing_ids") String listingIds,
		@QueryParam("only_barcode") Integer onlyBarcode,
		@QueryParam("page") Integer page,
		@QueryParam("per_page") Integer perPage
	) throws IOException;

	@DELETE
	@Path("/listing")
	Response deleteListing(@QueryParam("id") String ticketId) throws IOException;

}
