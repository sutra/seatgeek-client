package org.oxerr.seatgeek.model.request;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.oxerr.seatgeek.model.SplitType;
import org.oxerr.seatgeek.model.TokensType;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

class UpdateListingRequestTest {

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
		try (InputStream inputStream = this.getClass().getResourceAsStream("update-listing.json");) {
			CreateListingRequest listing = objectMapper.readValue(inputStream, CreateListingRequest.class);
			assertEquals(2, listing.getQuantity().intValue());
			assertEquals(new BigDecimal("275.99"), listing.getCost());
			assertEquals("Section 4", listing.getSection());
			assertEquals("3", listing.getRow());
			assertEquals(2, listing.getSeatFrom());
			assertEquals(3, listing.getSeatThru());
			assertEquals("New notes", listing.getNotes());
			assertEquals(LocalDate.of(2018, 3, 14), listing.getInHandDate());
			assertEquals(Boolean.TRUE, listing.getEdelivery());
			assertEquals(Boolean.TRUE, listing.getInstant());
			assertEquals(SplitType.DONTLEAVEONE, listing.getSplitType());
			assertEquals(TokensType.BARCODE, listing.getTokensType());
			assertEquals(2, listing.getTokens().get(0).getSeat().intValue());
			assertEquals("def456", listing.getTokens().get(0).getToken());
			assertEquals(3, listing.getTokens().get(1).getSeat().intValue());
			assertEquals("ghi789", listing.getTokens().get(1).getToken());

			String json = objectMapper.writeValueAsString(listing);
			log.info("json: {}", json);
		}
	}

}
