package org.oxerr.seatgeek.client.rescu;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class RescuSeatGeekClientTest {

	public static RescuSeatGeekClient getClient() {
		Properties props = new Properties();
		try (InputStream in = RescuSeatGeekClientTest.class.getResourceAsStream("/seatgeek.properties")) {
			props.load(in);
		} catch (IOException e) {
			throw new java.lang.IllegalArgumentException("Read /seatgeek.properties failed.");
		}

		String token = props.getProperty("token");
		return new RescuSeatGeekClient(token);
	}

}
