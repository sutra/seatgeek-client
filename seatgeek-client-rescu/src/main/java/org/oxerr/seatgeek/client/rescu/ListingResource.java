package org.oxerr.seatgeek.client.rescu;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.oxerr.seatgeek.model.request.CreateListingRequest;
import org.oxerr.seatgeek.model.request.UpdateListingRequest;
import org.oxerr.seatgeek.model.response.MultipleListingsResponse;
import org.oxerr.seatgeek.model.response.Response;
import org.oxerr.seatgeek.model.response.SingleListingResponse;

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
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
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
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
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
