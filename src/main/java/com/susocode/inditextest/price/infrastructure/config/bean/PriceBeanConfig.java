package com.susocode.inditextest.price.infrastructure.config.bean;

import com.susocode.inditextest.price.application.usecase.FindPriceUseCaseImpl;
import com.susocode.inditextest.price.domain.port.input.usecase.FindPriceUseCase;
import com.susocode.inditextest.price.domain.port.output.persistence.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class PriceBeanConfig {

  private final PriceRepository priceRepository;

  @Bean
  public FindPriceUseCase findPriceUseCase() {
    return new FindPriceUseCaseImpl(priceRepository);
  }

}
