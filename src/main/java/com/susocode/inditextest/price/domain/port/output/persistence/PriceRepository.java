package com.susocode.inditextest.price.domain.port.output.persistence;

import com.susocode.inditextest.price.domain.model.Price;
import java.time.LocalDateTime;
import reactor.core.publisher.Mono;

public interface PriceRepository {

  Mono<Price> findByProductIdAndBrandIdAndDateRange(Long productId, Long brandId,
      LocalDateTime localDateTime);

}
