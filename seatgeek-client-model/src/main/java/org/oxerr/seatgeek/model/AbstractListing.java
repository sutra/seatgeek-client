package org.oxerr.seatgeek.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class AbstractListing implements Serializable {

	private static final long serialVersionUID = 2023031401L;

	/**
	 * The number of seats in this listing.
	 */
	private Integer quantity;

	/**
	 * The broadcast price of the listing.
	 * You will be paid this amount per ticket, less any fees.
	 * Buyers will typically see a higher price than this.
	 */
	private BigDecimal cost;

	/**
	 * What section are the seats in?
	 */
	private String section;

	/**
	 * What row are the seats in?
	 */
	private String row;

	/**
	 * The minimum seat number in this listing.
	 */
	private Integer seatFrom;

	/**
	 * The maximum seat number in this listing.
	 */
	private Integer seatThru;

	/**
	 * Notes about what kind of seats these are, as well as how they will be fulfilled.
	 * See Notes section below for more details.
	 */
	private String notes;

	/**
	 * If you provide a date here, you’re telling SeatGeek to not remind you
	 * to fulfill this listing until this date.
	 * If you don’t, we’ll start reminding you right away.
	 */
	private LocalDate inHandDate;

	/**
	 * Will these tickets be fulfilled electronically?
	 * Should be {@literal true} for PDF, screenshot, and mobile transfer fulfillment,
	 * and {@literal false} for shipped, shipped gift card, venue walk-in,
	 * and will call fulfillment.
	 */
	@JsonProperty("is_edelivery")
	private Boolean edelivery;

	/**
	 * Will you fulfill this order within minutes of a purchase?
	 * Will be set to {@literal false} if Is Electronic is {@literal false}.
	 */
	@JsonProperty("is_instant")
	private Boolean instant;

	/**
	 * How should we choose which quantities to sell in?
	 */
	private SplitType splitType;

	/**
	 * The type of tokens being sent through {@code Tokens}.
	 */
	private TokensType tokensType;

	/**
	 * When seats are not provided, tokens should be in the order
	 * of the seat numbers.
	 * For this sample value, if {@code SeatFrom} is {@literal 1}
	 * and {@code SeatThru} is {@literal 4},
	 * then {@literal 4534k} is seat 1, {@literal 728k} is seat 2, etc.
	 */
	private List<Token> tokens;

	/**
	 * When seats are not provided, barcodes should be in the order
	 * of the seat numbers.
	 * For this sample value, if {@code SeatFrom} is {@literal 1}
	 * and {@code SeatThru} is {@literal 4},
	 * then {@literal 4534k} is seat 1, {@literal 728k} is seat 2, etc.
	 */
	private List<Barcode> barcodes;

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getRow() {
		return row;
	}

	public void setRow(String row) {
		this.row = row;
	}

	public Integer getSeatFrom() {
		return seatFrom;
	}

	public void setSeatFrom(Integer seatFrom) {
		this.seatFrom = seatFrom;
	}

	public Integer getSeatThru() {
		return seatThru;
	}

	public void setSeatThru(Integer seatThru) {
		this.seatThru = seatThru;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public LocalDate getInHandDate() {
		return inHandDate;
	}

	public void setInHandDate(LocalDate inHandDate) {
		this.inHandDate = inHandDate;
	}

	public Boolean getEdelivery() {
		return edelivery;
	}

	public void setEdelivery(Boolean edelivery) {
		this.edelivery = edelivery;
	}

	public Boolean getInstant() {
		return instant;
	}

	public void setInstant(Boolean instant) {
		this.instant = instant;
	}

	public SplitType getSplitType() {
		return splitType;
	}

	public void setSplitType(SplitType splitType) {
		this.splitType = splitType;
	}

	public TokensType getTokensType() {
		return tokensType;
	}

	public void setTokensType(TokensType tokensType) {
		this.tokensType = tokensType;
	}

	public List<Token> getTokens() {
		return tokens;
	}

	public void setTokens(List<Token> tokens) {
		this.tokens = tokens;
	}

	public List<Barcode> getBarcodes() {
		return barcodes;
	}

	public void setBarcodes(List<Barcode> barcodes) {
		this.barcodes = barcodes;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		AbstractListing rhs = (AbstractListing) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

}
