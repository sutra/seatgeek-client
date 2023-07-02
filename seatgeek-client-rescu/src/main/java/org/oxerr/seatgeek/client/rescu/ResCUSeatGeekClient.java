package org.oxerr.seatgeek.client.rescu;

import jakarta.ws.rs.HeaderParam;

import org.apache.commons.lang3.ArrayUtils;
import org.oxerr.rescu.ext.ratelimiter.RateLimiterInterceptor;
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

import si.mazi.rescu.ClientConfig;
import si.mazi.rescu.IRestProxyFactory;
import si.mazi.rescu.Interceptor;
import si.mazi.rescu.RestProxyFactoryImpl;
import si.mazi.rescu.serialization.jackson.DefaultJacksonObjectMapperFactory;
import si.mazi.rescu.serialization.jackson.JacksonObjectMapperFactory;

public class ResCUSeatGeekClient implements SeatGeekClient {

	private final String baseUrl;

	private final ClientConfig clientConfig;

	private final IRestProxyFactory restProxyFactory;

	private final ListingService listingService;

	public ResCUSeatGeekClient(String token, RateLimiter rateLimiter, Interceptor... interceptors) {
		this("https://sellerdirect-api.seatgeek.com", token, rateLimiter, interceptors);
	}

	public ResCUSeatGeekClient(String baseUrl, String token, RateLimiter rateLimiter, Interceptor... interceptors) {
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

		RateLimiterInterceptor rateLimiterInterceptor = new RateLimiterInterceptor(rateLimiter);
		Interceptor[] allInterceptors = ArrayUtils.addFirst(interceptors, rateLimiterInterceptor);

		this.listingService = new ListingServiceImpl(createProxy(ListingResource.class, allInterceptors));
	}

	@Override
	public ListingService getListingService() {
		return this.listingService;
	}

	protected <I> I createProxy(Class<I> restInterface, Interceptor... interceptors) {
		return this.restProxyFactory.createProxy(restInterface, baseUrl, this.clientConfig, interceptors);
	}

}
