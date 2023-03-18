package org.oxerr.seatgeek.client.cached;

import org.oxerr.seatgeek.client.SeatGeekClient;
import org.redisson.api.RedissonClient;

public class CachedSeatGeekClient {

	private final CachedListingService cachedListingService;

	public CachedSeatGeekClient(
		SeatGeekClient seatGeekClient,
		RedissonClient redissonClient,
		String keyPrefix
	) {
		this.cachedListingService = new CachedListingService(
			seatGeekClient.getListingService(),
			redissonClient,
			keyPrefix
		);
	}

	public CachedListingService getCachedListingService() {
		return this.cachedListingService;
	}

}
