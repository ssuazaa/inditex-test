package com.susocode.inditextest.price.unit.application.usecase;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.susocode.inditextest.price.application.usecase.FindPriceUseCaseImpl;
import com.susocode.inditextest.price.domain.model.Price;
import com.susocode.inditextest.price.domain.port.output.persistence.PriceRepository;
import com.susocode.inditextest.shared.exception.ObjectNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class FindPriceUseCaseImplTest {

  @Mock
  private PriceRepository priceRepository;

  @InjectMocks
  private FindPriceUseCaseImpl findPriceUseCase;

  @Test
  void shouldReturnPrice_whenPriceFound() {
    // Arrange
    var productId = 35455L;
    var brandId = 1L;
    var filterDate = LocalDateTime.now();
    var price = Price.builder()
        .id(1L)
        .brandId(brandId)
        .startDateTime(filterDate.minusDays(1L))
        .endDateTime(filterDate.plusDays(1L))
        .priceList((short) 1)
        .productId(productId)
        .priority((short) 1)
        .price(BigDecimal.valueOf(10.00))
        .currency(Currency.getInstance("EUR"))
        .build();

    when(priceRepository.findByProductIdAndBrandIdAndDateRange(productId, brandId, filterDate))
        .thenReturn(Mono.just(price));

    // Act
    var result = findPriceUseCase.findByProductIdAndBrandIdAndDateRange(productId, brandId,
        filterDate);

    // Assert
    StepVerifier.create(result)
        .expectNextCount(1)
        .verifyComplete();

    verify(priceRepository, times(1))
        .findByProductIdAndBrandIdAndDateRange(anyLong(), anyLong(), any(LocalDateTime.class));
    verifyNoMoreInteractions(priceRepository);
  }

  @Test
  void shouldHandleObjectNotFoundException_whenPriceNotFound() {
    // Arrange
    var productId = 35455L;
    var brandId = 1L;
    var filterDate = LocalDateTime.now();

    when(priceRepository.findByProductIdAndBrandIdAndDateRange(productId, brandId, filterDate))
        .thenReturn(Mono.empty());

    // Act
    var result = findPriceUseCase.findByProductIdAndBrandIdAndDateRange(productId, brandId,
        filterDate);

    // Assert
    StepVerifier.create(result)
        .verifyErrorMatches(error -> error.getClass().equals(ObjectNotFoundException.class)
            && ((ObjectNotFoundException) error).getKey()
            .equals("PRICE_BY_PRODUCT_ID_OR_BRAND_ID_NOT_FOUND"));

    verify(priceRepository, times(1))
        .findByProductIdAndBrandIdAndDateRange(anyLong(), anyLong(), any(LocalDateTime.class));
    verifyNoMoreInteractions(priceRepository);
  }

}