package com.maveric.commentservice.feign;

import org.springframework.cloud.openfeign.FallbackFactory;

public class HystrixFallBackFactory implements FallbackFactory<Feign> {
    @Override
    public Feign create(Throwable cause) {

        return null;

    }
}
