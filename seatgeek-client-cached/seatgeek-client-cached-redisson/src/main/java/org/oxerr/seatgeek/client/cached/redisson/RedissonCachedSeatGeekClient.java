package org.oxerr.seatgeek.client.cached.redisson;

import org.oxerr.seatgeek.client.SeatGeekClient;
import org.oxerr.seatgeek.client.cached.CachedSeatGeekClient;
import org.redisson.api.RedissonClient;

public class RedissonCachedSeatGeekClient implements CachedSeatGeekClient {

	private final SeatGeekClient client;

	private final RedissonCachedListingService cachedListingService;

	public RedissonCachedSeatGeekClient(
		SeatGeekClient seatGeekClient,
		RedissonClient redissonClient,
		String keyPrefix
	) {
		this(seatGeekClient, redissonClient, keyPrefix, true);
	}

	public RedissonCachedSeatGeekClient(
		SeatGeekClient seatGeekClient,
		RedissonClient redissonClient,
		String keyPrefix,
		boolean create
	) {
		this.client = seatGeekClient;
		this.cachedListingService = new RedissonCachedListingService(
			seatGeekClient.getListingService(),
			redissonClient,
			keyPrefix,
			create
		);
	}

	public SeatGeekClient getClient() {
		return this.client;
	}

	public RedissonCachedListingService getCachedListingService() {
		return this.cachedListingService;
	}

}
