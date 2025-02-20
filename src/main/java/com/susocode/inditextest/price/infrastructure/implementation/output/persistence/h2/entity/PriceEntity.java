package com.susocode.inditextest.price.infrastructure.implementation.output.persistence.h2.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table("PRICES")
public class PriceEntity implements Serializable {

  @Id
  @Column("ID")
  private Long id;

  @Column("BRAND_ID")
  private Long brandId;

  @Column("START_DATE")
  private LocalDateTime startDateTime;

  @Column("END_DATE")
  private LocalDateTime endDateTime;

  @Column("PRICE_LIST")
  private Short priceList;

  @Column("PRODUCT_ID")
  private Long productId;

  @Column("PRIORITY")
  private Short priority;

  @Column("PRICE")
  private BigDecimal price;

  @Column("CURR")
  private String currency;

}
