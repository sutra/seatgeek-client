package org.oxerr.seatgeek.client.rescu;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.oxerr.rescu.ext.ratelimiter.RateLimiter;

public class ResCUSeatGeekClientTest {

	private static final Logger LOG = LogManager.getLogger();

	public static ResCUSeatGeekClient getClient() {
		Properties props = new Properties();
		String name = "/seatgeek.properties";
		try (InputStream in = ResCUSeatGeekClientTest.class.getResourceAsStream(name)) {
			if (in != null) {
				props.load(in);
			} else {
				LOG.warn("No resource found: {}", name);
			}
		} catch (IOException e) {
			throw new java.lang.IllegalArgumentException("Read " + name + " failed.");
		}

		String token = props.getProperty("token");

		RateLimiter rateLimiter = new RateLimiter() {

			@Override
			public void acquire() {
			}

		};

		return new ResCUSeatGeekClient(token, rateLimiter);
	}

	@Test
	void testGetClient() {
		assertNotNull(ResCUSeatGeekClientTest.getClient());
	}

}
