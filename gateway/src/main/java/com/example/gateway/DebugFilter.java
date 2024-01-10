package com.example.gateway;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DebugFilter extends AbstractGatewayFilterFactory<DebugFilter.Config> {

  @Autowired
  public DebugFilter() {
    super(Config.class);
  }

  @Override
  public GatewayFilter apply(Config config) {
    return (exchange, chain) -> {
      ServerHttpRequest request = exchange.getRequest();

      log.info("Request received " + request.getURI().getRawPath());
      return chain.filter(exchange);
    };
  }


  @Getter
  @Setter
  @AllArgsConstructor
  public static class Config {
    private String delegateId;
  }
}
