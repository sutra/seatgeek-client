package org.oxerr.seatgeek.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Token implements Serializable {

	private static final long serialVersionUID = 20230314L;

	private Integer seat;

	private String token;

	public Token() {
	}

	public Token(Integer seat, String token) {
		this.seat = seat;
		this.token = token;
	}

	public Integer getSeat() {
		return seat;
	}

	public void setSeat(Integer seat) {
		this.seat = seat;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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
		Token rhs = (Token) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

}
