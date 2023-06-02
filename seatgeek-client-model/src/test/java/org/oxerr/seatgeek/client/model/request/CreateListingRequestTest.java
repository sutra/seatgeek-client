package org.oxerr.seatgeek.client.model.request;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.oxerr.seatgeek.client.model.SplitType;
import org.oxerr.seatgeek.client.model.TokensType;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

class CreateListingRequestTest {

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
		try (InputStream inputStream = this.getClass().getResourceAsStream("create-listing.json");) {
			CreateListingRequest listing = objectMapper.readValue(inputStream, CreateListingRequest.class);
			assertEquals("Justin Timberlake", listing.getEvent());
			assertEquals("Madison Square Garden", listing.getVenue());
			assertEquals(LocalDate.of(2018, 3, 21), listing.getEventDate());
			assertEquals(LocalTime.of(20, 0, 0), listing.getEventTime());
			assertEquals(3, listing.getQuantity().intValue());
			assertEquals(new BigDecimal("150.99"), listing.getCost());
			assertEquals("110", listing.getSection());
			assertEquals("10", listing.getRow());
			assertEquals(1, listing.getSeatFrom());
			assertEquals(3, listing.getSeatThru());
			assertEquals("Some notes", listing.getNotes());
			assertEquals(LocalDate.of(2018, 1, 1), listing.getInHandDate());
			assertEquals(Boolean.TRUE, listing.getEdelivery());
			assertEquals(Boolean.TRUE, listing.getInstant());
			assertEquals(SplitType.CUSTOM, listing.getSplitType());
			assertEquals("1,2,3", listing.getSplits());
			assertEquals(new BigDecimal("0.50"), listing.getSellerPreviouslyPaidPricePerTicket());
			assertEquals("mobile", listing.getStockType());
			assertEquals(TokensType.BARCODE, listing.getTokensType());
			assertEquals(1, listing.getTokens().get(0).getSeat().intValue());
			assertEquals("abc123", listing.getTokens().get(0).getToken());
			assertEquals(2, listing.getTokens().get(1).getSeat().intValue());
			assertEquals("def456", listing.getTokens().get(1).getToken());
			assertEquals(3, listing.getTokens().get(2).getSeat().intValue());
			assertEquals("ghi789", listing.getTokens().get(2).getToken());

			String json = objectMapper.writeValueAsString(listing);
			log.info("json: {}", json);
		}
	}

}
