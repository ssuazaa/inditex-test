package com.susocode.inditextest.price.application.usecase;

import com.susocode.inditextest.price.domain.model.Price;
import com.susocode.inditextest.price.domain.port.input.usecase.FindPriceUseCase;
import com.susocode.inditextest.price.domain.port.output.persistence.PriceRepository;
import com.susocode.inditextest.shared.exception.ObjectNotFoundException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class FindPriceUseCaseImpl implements FindPriceUseCase {

  private final PriceRepository priceRepository;

  @Override
  public Mono<Price> findByProductIdAndBrandIdAndDateRange(Long productId, Long brandId,
      LocalDateTime filterDate) {
    return priceRepository.findByProductIdAndBrandIdAndDateRange(productId, brandId,
            filterDate)
        .switchIfEmpty(
            Mono.error(new ObjectNotFoundException("PRICE_BY_PRODUCT_ID_OR_BRAND_ID_NOT_FOUND",
                String.format("Price for productId ('%d') and brandId ('%d') does not exists.",
                    productId, brandId))));
  }

}
