package com.susocode.inditextest.price.integration.infrastructure.implementation.input.rest.adapter;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.susocode.inditextest.model.PriceResponseDto;
import java.time.LocalDateTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class RestPriceControllerTest {

  private static final Logger log = LoggerFactory.getLogger(RestPriceControllerTest.class);

  @Autowired
  private WebTestClient webTestClient;

  static Stream<Arguments> filterDatesAndIterations() {
    return Stream.of(
        Arguments.of(LocalDateTime.parse("2020-06-14T10:00:00"), 1),
        Arguments.of(LocalDateTime.parse("2020-06-14T16:00:00"), 2),
        Arguments.of(LocalDateTime.parse("2020-06-14T21:00:00"), 3),
        Arguments.of(LocalDateTime.parse("2020-06-15T10:00:00"), 4),
        Arguments.of(LocalDateTime.parse("2020-06-16T21:00:00"), 5)
    );
  }

  @ParameterizedTest(name = "Iteration: {1} - {0}")
  @MethodSource("filterDatesAndIterations")
  void shouldReturnPriceResponseDto_whenPriceFound(
      LocalDateTime filterDate, Integer iteration) {
    // Arrange
    var productId = 35455L;
    var brandId = 1L;

    // Act & Assert
    webTestClient.get()
        .uri(uriBuilder -> uriBuilder
            .path("/api/v1/prices/product/{productId}/brand/{brandId}")
            .queryParam("applyDate", filterDate)
            .build(productId, brandId))
        .exchange()
        .expectStatus().isOk()
        .expectBody(PriceResponseDto.class)
        .value(dto -> {
          assertNotNull(dto.getProductId());
          assertNotNull(dto.getBrandId());
          assertNotNull(dto.getPriceList());
          assertNotNull(dto.getStartDateTime());
          assertNotNull(dto.getEndDateTime());
          assertNotNull(dto.getPrice());
          log.info(
              "Test {}: petición a las {}:{} del día {} del producto {} para la brand {} (ZARA)",
              iteration, filterDate.getHour(), filterDate.getMinute(), filterDate.getDayOfMonth(),
              productId, brandId);
        });
  }

  @Test
  void shouldReturnNotFound_whenProductDoesNotExist() {
    var nonExistentProductId = 99999L;
    var brandId = 1L;
    var filterDate = LocalDateTime.parse("2025-06-14T10:00:00");

    webTestClient.get()
        .uri(uriBuilder -> uriBuilder
            .path("/api/v1/prices/product/{productId}/brand/{brandId}")
            .queryParam("applyDate", filterDate)
            .build(nonExistentProductId, brandId))
        .exchange()
        .expectStatus().isNotFound();
  }

  @Test
  void shouldReturnBadRequest_whenInvalidDateFormat() {
    var productId = 35455L;
    var brandId = 1L;
    var invalidDate = "invalid-date-format";

    webTestClient.get()
        .uri(uriBuilder -> uriBuilder
            .path("/api/v1/prices/product/{productId}/brand/{brandId}")
            .queryParam("applyDate", invalidDate)
            .build(productId, brandId))
        .exchange()
        .expectStatus().isBadRequest();
  }

}
