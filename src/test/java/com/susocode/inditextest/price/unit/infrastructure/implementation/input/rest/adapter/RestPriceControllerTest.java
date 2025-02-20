package com.susocode.inditextest.price.unit.infrastructure.implementation.input.rest.adapter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.susocode.inditextest.model.PriceResponseDto;
import com.susocode.inditextest.price.domain.model.Price;
import com.susocode.inditextest.price.domain.port.input.usecase.FindPriceUseCase;
import com.susocode.inditextest.price.infrastructure.implementation.input.rest.adapter.RestPriceController;
import com.susocode.inditextest.price.infrastructure.implementation.input.rest.mapper.RestPriceMapper;
import com.susocode.inditextest.shared.exception.ObjectNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class RestPriceControllerTest {

  @Mock
  private FindPriceUseCase findPriceUseCase;

  @Mock
  private RestPriceMapper mapper;

  @InjectMocks
  private RestPriceController restPriceController;

  @Test
  void shouldReturnPriceResponseDto_whenPriceFound() {
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
    var priceResponseDto = new PriceResponseDto();
    priceResponseDto.setProductId(productId);
    priceResponseDto.setBrandId(brandId);
    priceResponseDto.setPriceList(1);
    priceResponseDto.setStartDateTime(filterDate.minusDays(1L));
    priceResponseDto.setEndDateTime(filterDate.plusDays(1L));
    priceResponseDto.setPrice(BigDecimal.valueOf(10.00));

    when(findPriceUseCase.findByProductIdAndBrandIdAndDateRange(productId, brandId, filterDate))
        .thenReturn(Mono.just(price));
    when(mapper.toResponseDto(price))
        .thenReturn(priceResponseDto);

    // Act
    var response = restPriceController.apiV1PricesProductProductIdBrandBrandIdGet(productId,
        brandId, filterDate, null);

    // Assert
    StepVerifier.create(response)
        .expectNextMatches(responseEntity -> {
          var dto = responseEntity.getBody();
          return responseEntity.getStatusCode().is2xxSuccessful() &&
              Objects.nonNull(dto) &&
              dto.getProductId().equals(productId) &&
              dto.getBrandId().equals(brandId);
        })
        .verifyComplete();

    verify(findPriceUseCase, times(1))
        .findByProductIdAndBrandIdAndDateRange(anyLong(), anyLong(), any(LocalDateTime.class));
    verify(mapper, times(1)).toResponseDto(any(Price.class));
    verifyNoMoreInteractions(findPriceUseCase, mapper);
  }

  @Test
  void shouldReturnObjectNotFoundException_whenPriceNotFound() {
    // Arrange
    var productId = 35455L;
    var brandId = 1L;
    var filterDate = LocalDateTime.now();

    when(findPriceUseCase.findByProductIdAndBrandIdAndDateRange(productId, brandId, filterDate))
        .thenReturn(Mono.error(
            new ObjectNotFoundException("PRICE_BY_PRODUCT_ID_OR_BRAND_ID_NOT_FOUND", "")));

    // Act
    var response = restPriceController.apiV1PricesProductProductIdBrandBrandIdGet(productId,
        brandId, filterDate, null);

    // Assert
    StepVerifier.create(response)
        .verifyErrorMatches(error -> error.getClass().equals(ObjectNotFoundException.class)
            && ((ObjectNotFoundException) error).getKey()
            .equals("PRICE_BY_PRODUCT_ID_OR_BRAND_ID_NOT_FOUND"));

    verify(findPriceUseCase, times(1))
        .findByProductIdAndBrandIdAndDateRange(anyLong(), anyLong(), any(LocalDateTime.class));
    verifyNoMoreInteractions(findPriceUseCase);
    verifyNoInteractions(mapper);
  }

}
