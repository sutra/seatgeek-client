package org.oxerr.seatgeek.client.rescu;

import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.Properties;

import javax.ws.rs.HeaderParam;

import org.apache.commons.lang3.StringUtils;
import org.oxerr.seatgeek.client.ListingService;
import org.oxerr.seatgeek.client.SeatGeekClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import si.mazi.rescu.ClientConfig;
import si.mazi.rescu.IRestProxyFactory;
import si.mazi.rescu.RestProxyFactory;
import si.mazi.rescu.serialization.jackson.DefaultJacksonObjectMapperFactory;
import si.mazi.rescu.serialization.jackson.JacksonObjectMapperFactory;

public class RescuSeatGeekClient implements SeatGeekClient {

	private static final String RESCU_PROPERTIES = "/rescu.properties";

	private final Logger log = LoggerFactory.getLogger(RescuSeatGeekClient.class);

	private final String baseUrl;

	private final ClientConfig clientConfig;

	private final IRestProxyFactory restProxyFactory;

	private final RateLimitInterceptor rateLimitInterceptor;

	private final ListingService listingService;

	public RescuSeatGeekClient(String token, RateLimiter rateLimiter) {
		this("https://sellerdirect-api.seatgeek.com", token, rateLimiter);
	}

	public RescuSeatGeekClient(String baseUrl, String token, RateLimiter rateLimiter) {
		this.baseUrl = baseUrl;

		JacksonObjectMapperFactory jacksonObjectMapperFactory = new DefaultJacksonObjectMapperFactory() {

			@Override
			public void configureObjectMapper(ObjectMapper objectMapper) {
				super.configureObjectMapper(objectMapper);
				objectMapper.registerModule(new JavaTimeModule());
				objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
				objectMapper.setSerializationInclusion(Include.NON_ABSENT);
				objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
				objectMapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
				objectMapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);
			}

			@Override
			protected ObjectMapper createInstance() {
				return new ObjectMapper();
			}
		};

		loadProxyAuthentication();

		this.clientConfig = new ClientConfig();
		clientConfig.addDefaultParam(HeaderParam.class, "Authorization", "token " + token);
		clientConfig.setJacksonObjectMapperFactory(jacksonObjectMapperFactory);

		this.restProxyFactory = new RestProxyFactorySingletonImpl();
		this.rateLimitInterceptor = new RateLimitInterceptor(rateLimiter);

		this.listingService = new ListingServiceImpl(createProxy(ListingResource.class));
	}

	@Override
	public ListingService getListingService() {
		return this.listingService;
	}

	protected <I> I createProxy(Class<I> restInterface) {
		return this.restProxyFactory.createProxy(restInterface, baseUrl, this.clientConfig, this.rateLimitInterceptor);
	}

	private void loadProxyAuthentication() {
		Properties properties = new Properties();
		InputStream propsStream = RestProxyFactory.class.getResourceAsStream(RESCU_PROPERTIES);
		if (propsStream != null) {
			try {
				properties.load(propsStream);
				log.debug("Loaded properties from {}.", RESCU_PROPERTIES);
			} catch (IOException e) {
				throw new IllegalArgumentException("Error reading " + RESCU_PROPERTIES, e);
			}
		}

		String proxyHost = properties.getProperty("rescu.http.readProxyHost");
		String proxyPort = properties.getProperty("rescu.http.readProxyPort");

		String userName = properties.getProperty("rescu.http.readProxyUserName");
		String password = properties.getProperty("rescu.http.readProxyPassword");

		if (StringUtils.isNotEmpty(proxyHost)
				&& StringUtils.isNotEmpty(proxyPort)
				&& StringUtils.isNotEmpty(userName)
				&& StringUtils.isNotEmpty(password)) {
			this.setPasswordAuthentication(userName, password.toCharArray());
		}
	}

	private void setPasswordAuthentication(String userName, char[] password) {
		// https://bugs.openjdk.org/browse/JDK-8168839
		log.info("Setting property jdk.http.auth.tunneling.disabledSchemes to empty.");
		System.setProperty("jdk.http.auth.tunneling.disabledSchemes", StringUtils.EMPTY);

		final PasswordAuthentication auth = new PasswordAuthentication(userName, password);

		Authenticator authenticator = new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return auth;
			}

		};

		Authenticator.setDefault(authenticator);
	}

}
