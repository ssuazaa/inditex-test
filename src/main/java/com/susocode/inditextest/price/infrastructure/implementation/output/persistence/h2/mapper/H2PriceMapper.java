package com.susocode.inditextest.price.infrastructure.implementation.output.persistence.h2.mapper;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;
import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.susocode.inditextest.price.domain.model.Price;
import com.susocode.inditextest.price.infrastructure.implementation.output.persistence.h2.entity.PriceEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING, injectionStrategy = CONSTRUCTOR)
public interface H2PriceMapper {

  Price toDomain(PriceEntity priceEntity);

}
