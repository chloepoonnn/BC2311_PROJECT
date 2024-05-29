package com.vtxlab.project.bc_stock_finnhub.config;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.vtxlab.project.bc_stock_finnhub.infra.Mapper;
import com.vtxlab.project.bc_stock_finnhub.model.CompanyProfile;
import com.vtxlab.project.bc_stock_finnhub.model.Quote;
import com.vtxlab.project.bc_stock_finnhub.redis.RedisHelper;
import com.vtxlab.project.bc_stock_finnhub.service.FinnhubService;

@Component
@EnableScheduling
public class ScheduledConfig {

  @Autowired
  FinnhubService finnhubService;

  @Autowired
  RedisHelper redisHelper;

  @Autowired
  Mapper mapper;

  @Value("${redis-key.symbol}")
  String stockSymbol;


  private static final int CACHE_EXPIRATION_TIME = 60;
  private static final String quoteKey = "stock:finnhub:quote:";
  private static final String profileKey = "stock:finnhub:profile:";

  @Scheduled(fixedRate = 60000)
  public void scheduleFixedRateTask() throws Exception {
    Arrays.stream(stockSymbol.split(","))//
        .forEach(e -> {
          Quote targetQuote = finnhubService.getQuote(e);
          redisHelper.set(quoteKey + e, targetQuote, CACHE_EXPIRATION_TIME);

          CompanyProfile companyProfile = finnhubService.getProfile(e);
          redisHelper.set(profileKey + e, companyProfile,
              CACHE_EXPIRATION_TIME);
        });
  }


  // @Scheduled(fixedDelay = 3000)
  public void scheduleFixedDelayTask() {
    System.out
        .println("Fixed delay task - " + System.currentTimeMillis() / 1000);
  }


  // @Scheduled(cron = "*/5 * * * * ?")
  @Async
  public void scheduleTaskUsingCronExpression() throws InterruptedException {
    System.out.println("Cron Task - " + System.currentTimeMillis() / 1000);
    // Thread.sleep(10000);
    System.out.println("End Cron ");
  }

  // @Scheduled(cron = "10 * * * * ?")
  @Async
  public void cronTask() throws InterruptedException {
    System.out.println("Cron Task - " + System.currentTimeMillis() / 1000);
    // Thread.sleep(10000);
    System.out.println("End Cron ");
  }


}
