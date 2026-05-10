package com.platform.blog.service.shared.analytics;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class AnalyticService {
  private final AtomicInteger totalRequests = new AtomicInteger(0);

  public void incrementRequest() {
    totalRequests.incrementAndGet();
  }

  public int getTotalRequests() {
    return totalRequests.get();
  }
}
