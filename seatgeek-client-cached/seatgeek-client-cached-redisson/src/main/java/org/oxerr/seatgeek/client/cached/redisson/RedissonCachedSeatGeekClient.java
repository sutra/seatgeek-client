package org.oxerr.seatgeek.client.cached.redisson;

import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;

import org.oxerr.seatgeek.client.SeatGeekClient;
import org.oxerr.seatgeek.client.cached.CachedSeatGeekClient;
import org.redisson.api.RedissonClient;

public class RedissonCachedSeatGeekClient implements CachedSeatGeekClient {

	private final SeatGeekClient seatGeekClient;

	private final RedissonCachedListingService cachedListingService;

	public RedissonCachedSeatGeekClient(
		SeatGeekClient seatGeekClient,
		RedissonClient redissonClient,
		String keyPrefix
	) {
		this(seatGeekClient, redissonClient, keyPrefix, ForkJoinPool.commonPool());
	}

	public RedissonCachedSeatGeekClient(
		SeatGeekClient seatGeekClient,
		RedissonClient redissonClient,
		String keyPrefix,
		Executor executor
	) {
		this(seatGeekClient, redissonClient, keyPrefix, executor, true);
	}

	public RedissonCachedSeatGeekClient(
		SeatGeekClient seatGeekClient,
		RedissonClient redissonClient,
		String keyPrefix,
		Executor executor,
		boolean create
	) {
		this(
			seatGeekClient,
			new RedissonCachedListingService(
				seatGeekClient.getListingService(),
				redissonClient,
				keyPrefix,
				executor,
				create
			)
		);
	}

	public RedissonCachedSeatGeekClient(
		SeatGeekClient seatGeekClient,
		RedissonCachedListingService cachedListingService
	) {
		this.seatGeekClient = seatGeekClient;
		this.cachedListingService = cachedListingService;
	}

	@Override
	public SeatGeekClient getClient() {
		return this.seatGeekClient;
	}

	@Override
	public RedissonCachedListingService getCachedListingService() {
		return this.cachedListingService;
	}

}
