package com.susocode.inditextest.price.domain.port.input.usecase;

import com.susocode.inditextest.price.domain.model.Price;
import java.time.LocalDateTime;
import reactor.core.publisher.Mono;

public interface FindPriceUseCase {

  Mono<Price> findByProductIdAndBrandIdAndDateRange(Long productId, Long brandId, LocalDateTime filterDate);

}
