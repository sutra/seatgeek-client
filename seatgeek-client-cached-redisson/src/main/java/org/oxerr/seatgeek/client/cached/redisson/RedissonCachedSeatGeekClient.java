package org.oxerr.seatgeek.client.cached.redisson;

import org.oxerr.seatgeek.client.SeatGeekClient;
import org.oxerr.seatgeek.client.cached.CachedListingService;
import org.oxerr.seatgeek.client.cached.CachedSeatGeekClient;
import org.redisson.api.RedissonClient;

public class RedissonCachedSeatGeekClient implements CachedSeatGeekClient {

	private final SeatGeekClient client;

	private final CachedListingService cachedListingService;

	public RedissonCachedSeatGeekClient(
		SeatGeekClient seatGeekClient,
		RedissonClient redissonClient,
		String keyPrefix
	) {
		this.client = seatGeekClient;
		this.cachedListingService = new RedissonCachedListingService(
			seatGeekClient.getListingService(),
			redissonClient,
			keyPrefix
		);
	}

	public SeatGeekClient getClient() {
		return this.client;
	}

	public CachedListingService getCachedListingService() {
		return this.cachedListingService;
	}

}
