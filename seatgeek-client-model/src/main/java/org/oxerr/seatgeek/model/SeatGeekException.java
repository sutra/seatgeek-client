package org.oxerr.seatgeek.model;

import java.io.IOException;

public class SeatGeekException extends IOException {

	private static final long serialVersionUID = 20230317L;

	public SeatGeekException() {
		super();
	}

	public SeatGeekException(String message, Throwable cause) {
		super(message, cause);
	}

	public SeatGeekException(String message) {
		super(message);
	}

	public SeatGeekException(Throwable cause) {
		super(cause);
	}

}
