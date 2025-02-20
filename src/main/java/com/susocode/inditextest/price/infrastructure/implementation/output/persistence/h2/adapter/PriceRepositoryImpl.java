package com.susocode.inditextest.price.infrastructure.implementation.output.persistence.h2.adapter;

import com.susocode.inditextest.price.domain.model.Price;
import com.susocode.inditextest.price.domain.port.output.persistence.PriceRepository;
import com.susocode.inditextest.price.infrastructure.implementation.output.persistence.h2.mapper.H2PriceMapper;
import com.susocode.inditextest.price.infrastructure.implementation.output.persistence.h2.repository.ReactiveH2PriceRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PriceRepositoryImpl implements PriceRepository {

  private final H2PriceMapper mapper;
  private final ReactiveH2PriceRepository repository;

  @Override
  public Mono<Price> findByProductIdAndBrandIdAndDateRange(Long productId, Long brandId,
      LocalDateTime applyDate) {
    return repository.findApplicablePrice(productId, brandId, applyDate)
        .map(mapper::toDomain);
  }

}
