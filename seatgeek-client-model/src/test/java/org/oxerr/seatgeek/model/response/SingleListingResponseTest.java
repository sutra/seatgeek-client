package org.oxerr.seatgeek.model.response;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.oxerr.seatgeek.model.SplitType;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

class SingleListingResponseTest {

	private final Logger log = LogManager.getLogger();

	private final ObjectMapper objectMapper = JsonMapper
		.builder()
		.addModule(new JavaTimeModule())
		.propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
		.enable(SerializationFeature.INDENT_OUTPUT)
		.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
		.build();

	@Test
	void testReadWrite() throws StreamReadException, DatabindException, IOException {
		try (InputStream inputStream = this.getClass().getResourceAsStream("single-listing.json");) {
			SingleListingResponse singleListingResponse = objectMapper.readValue(inputStream, SingleListingResponse.class);
			Listing listing = singleListingResponse.getListing();
			assertEquals("abc123", listing.getTicketId());
			assertEquals(5660418, listing.getEventId());
			assertEquals("Justin Timberlake", listing.getEvent());
			assertEquals("Madison Square Garden", listing.getVenue());
			assertEquals(LocalDate.of(2018, 3, 21), listing.getEventDate());
			assertEquals(LocalTime.of(20, 0, 0), listing.getEventTime());
			assertEquals(2, listing.getQuantity().intValue());
			assertEquals(new BigDecimal("275.99"), listing.getCost());
			assertEquals("110", listing.getSection());
			assertEquals("10", listing.getRow());
			assertEquals(2, listing.getSeatFrom());
			assertEquals(3, listing.getSeatThru());
			assertEquals("New notes", listing.getNotes());
			assertEquals(LocalDate.of(2018, 3, 14), listing.getInHandDate());
			assertEquals(Boolean.TRUE, listing.getEdelivery());
			assertEquals(Boolean.TRUE, listing.getInstant());
			assertEquals(SplitType.CUSTOM, listing.getSplitType());
			assertEquals("1,2", listing.getSplits());
			assertEquals(new BigDecimal("1.40"), listing.getSellerPreviouslyPaidPricePerTicket());
			assertEquals(2, listing.getBarcodes().get(0).getSeat().intValue());
			assertEquals("def456", listing.getBarcodes().get(0).getBarcode());
			assertEquals(3, listing.getBarcodes().get(1).getSeat().intValue());
			assertEquals("ghi789", listing.getBarcodes().get(1).getBarcode());

			String json = objectMapper.writeValueAsString(listing);
			log.info("json: {}", json);
		}
	}

}
