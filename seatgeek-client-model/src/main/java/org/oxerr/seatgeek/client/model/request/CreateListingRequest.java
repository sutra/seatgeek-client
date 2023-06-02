package org.oxerr.seatgeek.client.model.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.oxerr.seatgeek.client.model.AbstractListing;

public class CreateListingRequest extends AbstractListing {

	private static final long serialVersionUID = 2023031401L;

	/**
	 * The event title.
	 * 
	 * <p>For parking passes, prefix the event name with {@literal PARKING:}.</p>
	 */
	private String event;

	/**
	 * The venue name.
	 */
	private String venue;

	/**
	 * The date that the event starts.
	 */
	private LocalDate eventDate;

	/**
	 * The time that the event starts.
	 */
	private LocalTime eventTime;

	/**
	 * What increments should SeatGeek sell in?
	 * If your listing has 3 tickets, and your splits are {@literal 1,3},
	 * then we’ll only let a buyer buy 1 or 3 seats.
	 */
	private String splits;

	/**
	 * The amount per ticket previously paid for this listing.
	 * This is used for calculating sales tax for buyers and to comply
	 * with New York State ticketing requirements (as applicable).
	 */
	private BigDecimal sellerPreviouslyPaidPricePerTicket;

	/**
	 * The ticket type, which we use to determine how the seller plans
	 * to fulfill the orders.
	 * If not provided or the value is not supported,
	 * stock type will be inferred from the listing fields.
	 */
	private String stockType;

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

	public LocalDate getEventDate() {
		return eventDate;
	}

	public void setEventDate(LocalDate eventDate) {
		this.eventDate = eventDate;
	}

	public LocalTime getEventTime() {
		return eventTime;
	}

	public void setEventTime(LocalTime eventTime) {
		this.eventTime = eventTime;
	}

	public String getSplits() {
		return splits;
	}

	public void setSplits(String splits) {
		this.splits = splits;
	}

	public BigDecimal getSellerPreviouslyPaidPricePerTicket() {
		return sellerPreviouslyPaidPricePerTicket;
	}

	public void setSellerPreviouslyPaidPricePerTicket(BigDecimal sellerPreviouslyPaidPricePerTicket) {
		this.sellerPreviouslyPaidPricePerTicket = sellerPreviouslyPaidPricePerTicket;
	}

	public String getStockType() {
		return stockType;
	}

	public void setStockType(String stockType) {
		this.stockType = stockType;
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
		CreateListingRequest rhs = (CreateListingRequest) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

}
