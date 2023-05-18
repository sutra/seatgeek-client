package org.oxerr.seatgeek.client.rescu;

import org.oxerr.rescu.ext.ratelimiter.RateLimitInterceptor;
import org.oxerr.rescu.ext.ratelimiter.RateLimiter;
import org.oxerr.rescu.ext.singleton.RestProxyFactorySingletonImpl;
import org.oxerr.seatgeek.client.ListingService;
import org.oxerr.seatgeek.client.SeatGeekClient;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.ws.rs.HeaderParam;
import si.mazi.rescu.ClientConfig;
import si.mazi.rescu.IRestProxyFactory;
import si.mazi.rescu.RestProxyFactoryImpl;
import si.mazi.rescu.serialization.jackson.DefaultJacksonObjectMapperFactory;
import si.mazi.rescu.serialization.jackson.JacksonObjectMapperFactory;

public class RescuSeatGeekClient implements SeatGeekClient {

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

		this.clientConfig = new ClientConfig();
		clientConfig.addDefaultParam(HeaderParam.class, "Authorization", "token " + token);
		clientConfig.setJacksonObjectMapperFactory(jacksonObjectMapperFactory);

		this.restProxyFactory = new RestProxyFactorySingletonImpl(new RestProxyFactoryImpl());
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

}
