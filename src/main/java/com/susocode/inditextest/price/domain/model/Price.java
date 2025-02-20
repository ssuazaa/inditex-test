package com.susocode.inditextest.price.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Price {

  private Long id;
  private Long brandId;
  private LocalDateTime startDateTime;
  private LocalDateTime endDateTime;
  private Short priceList;
  private Long productId;
  private Short priority;
  private BigDecimal price;
  private Currency currency;

}
