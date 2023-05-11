package org.oxerr.seatgeek.client.cached;

import org.oxerr.seatgeek.client.SeatGeekClient;
import org.redisson.api.RedissonClient;

public class CachedSeatGeekClient {

	private final SeatGeekClient seatGeekClient;

	private final CachedListingService cachedListingService;

	public CachedSeatGeekClient(
		SeatGeekClient seatGeekClient,
		RedissonClient redissonClient,
		String keyPrefix
	) {
		this.seatGeekClient = seatGeekClient;
		this.cachedListingService = new CachedListingService(
			seatGeekClient.getListingService(),
			redissonClient,
			keyPrefix
		);
	}

	public SeatGeekClient getClient() {
		return this.seatGeekClient;
	}

	public CachedListingService getCachedListingService() {
		return this.cachedListingService;
	}

}
