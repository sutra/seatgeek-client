package org.oxerr.seatgeek.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TokensType {

	@JsonProperty("barcode")
	BARCODE,

	@JsonProperty("ticket_id")
	TICKET_ID;

}
