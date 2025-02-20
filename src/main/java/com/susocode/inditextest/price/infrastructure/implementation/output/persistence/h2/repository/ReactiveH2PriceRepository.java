package com.susocode.inditextest.price.infrastructure.implementation.output.persistence.h2.repository;

import com.susocode.inditextest.price.infrastructure.implementation.output.persistence.h2.entity.PriceEntity;
import java.time.LocalDateTime;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ReactiveH2PriceRepository extends ReactiveCrudRepository<PriceEntity, Long> {

  @Query("""
          SELECT * FROM PRICES
          WHERE PRODUCT_ID = :productId
          AND BRAND_ID = :brandId
          AND CAST(:applyDate AS TIMESTAMP) BETWEEN START_DATE AND END_DATE
          ORDER BY PRIORITY DESC
          LIMIT 1
      """)
  Mono<PriceEntity> findApplicablePrice(Long productId, Long brandId, LocalDateTime applyDate);

}
