package com.susocode.inditextest.price.infrastructure.implementation.input.rest.adapter;

import com.susocode.inditextest.api.PricesV1Api;
import com.susocode.inditextest.model.PriceResponseDto;
import com.susocode.inditextest.price.domain.port.input.usecase.FindPriceUseCase;
import com.susocode.inditextest.price.infrastructure.implementation.input.rest.mapper.RestPriceMapper;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class RestPriceController implements PricesV1Api {

  private final FindPriceUseCase findPriceUseCase;
  private final RestPriceMapper mapper;

  @Override
  public Mono<ResponseEntity<PriceResponseDto>> apiV1PricesProductProductIdBrandBrandIdGet(
      Long productId, Long brandId, LocalDateTime applyDate,
      ServerWebExchange exchange) {
    if (Objects.isNull(applyDate)) {
      applyDate = LocalDateTime.now();
    }
    return findPriceUseCase.findByProductIdAndBrandIdAndDateRange(productId, brandId, applyDate)
        .map(mapper::toResponseDto)
        .map(ResponseEntity::ok);
  }

}
