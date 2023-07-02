package org.oxerr.seatgeek.client.rescu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.oxerr.seatgeek.client.ListingService;
import org.oxerr.seatgeek.client.SeatGeekClient;
import org.oxerr.seatgeek.client.model.SplitType;
import org.oxerr.seatgeek.client.model.Token;
import org.oxerr.seatgeek.client.model.TokensType;
import org.oxerr.seatgeek.client.model.request.CreateListingRequest;
import org.oxerr.seatgeek.client.model.request.UpdateListingRequest;
import org.oxerr.seatgeek.client.model.response.Listing;
import org.oxerr.seatgeek.client.model.response.MultipleListingsResponse;

class ListingServiceImplTest {

	private final Logger log = LogManager.getLogger();

	private final SeatGeekClient client = ResCUSeatGeekClientTest.getClient();

	private final ListingService listingService = client.getListingService();

	@Test
	@Disabled("Token is required")
	void testCreateListing() throws IOException {
		String ticketId = "1";

		CreateListingRequest r = new CreateListingRequest();
		r.setEvent("Justin Timberlake");
		r.setVenue("Madison Square Garden");
		r.setEventDate(LocalDate.of(2018, 3, 21));
		r.setEventTime(LocalTime.of(20, 0));
		r.setQuantity(3);
		r.setCost(new BigDecimal("150.99"));
		r.setSection("110");
		r.setRow("10");
		r.setSeatFrom(1);
		r.setSeatThru(3);
		r.setNotes("Some notes");
		r.setInHandDate(LocalDate.of(2018, 1, 1));
		r.setEdelivery(true);
		r.setInstant(false);
		r.setSplitType(SplitType.ANY);
		r.setSplits("1,2,3");
		r.setSellerPreviouslyPaidPricePerTicket(new BigDecimal("0.50"));
		r.setTokensType(TokensType.BARCODE);
		r.setTokens(Arrays.asList(new Token(1, "abc123"), new Token(2, "def456"), new Token(3, "ghi789")));

		// Create
		this.listingService.createListing(ticketId, r);

		// Get
		Listing listing = this.listingService.getListing(ticketId).get();
		assertEquals(3, listing.getQuantity().intValue());

		// Update via create API
		r.setQuantity(2);
		this.listingService.createListing(ticketId, r);

		// Get again
		listing = this.listingService.getListing(ticketId).get();
		assertEquals(2, listing.getQuantity().intValue());
		assertEquals(Boolean.FALSE, listing.getInstant());

		// Get multiple listings
		/*
		Integer page = 0;
		Integer perPage = null;
		Boolean onlyBarcode = null;

		MultipleListingsResponse response = this.listingService.getListings(page, perPage, onlyBarcode);
		assertEquals(0, response.getListings().size());
		assertEquals(1, response.getMeta().getPage().intValue());
		assertEquals(200, response.getMeta().getPerPage().intValue());
		assertEquals(0L, response.getMeta().getTotal().longValue());
		assertEquals(200, response.getMeta().getStatus().intValue());
		assertEquals(1L, response.getMeta().getTotal().longValue());
		assertEquals(2, response.getListings().get(0).getQuantity().intValue());
		*/

		// Delete
		this.listingService.deleteListing(ticketId);

		// Delete again
		this.listingService.deleteListing(ticketId);

		listing = this.listingService.getListing(ticketId).get();
		assertNull(listing);
	}

	@Test
	@Disabled("java.net.ProtoclException: Invalid HTTP method: PATCH")
	void testUpdateListing() throws IOException {
		String ticketId = "1";

		UpdateListingRequest r = new UpdateListingRequest();
		r.setQuantity(2);

		this.listingService.updateListing(ticketId, r);
	}

	@Test
	@Disabled("Token is required")
	void testGetListing() throws IOException {
		String ticketId = "1";
		Listing listing = this.listingService.getListing(ticketId).get();
		assertNull(listing);
	}

	@Test
	@Disabled("Token is required")
	void testGetListings() throws IOException {
		Integer page = null;
		Integer perPage = null;
		Boolean onlyBarcode = null;

		MultipleListingsResponse response = this.listingService.getListings(page, perPage, onlyBarcode);
		log.info("response: {}", response);
		assertEquals(200, response.getListings().size());
		assertEquals(1, response.getMeta().getPage().intValue());
		assertEquals(200, response.getMeta().getPerPage().intValue());
		assertEquals(1278205L, response.getMeta().getTotal().longValue());
		assertEquals(200, response.getMeta().getStatus().intValue());
	}

	@Test
	@Disabled("Token is required")
	void testDeleteListing() throws IOException {
		String ticketId = "1";
		this.listingService.deleteListing(ticketId);
		this.listingService.getListing(ticketId);
	}

}
