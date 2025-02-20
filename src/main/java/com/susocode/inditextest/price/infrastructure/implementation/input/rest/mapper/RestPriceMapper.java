package com.susocode.inditextest.price.infrastructure.implementation.input.rest.mapper;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;
import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.susocode.inditextest.model.PriceResponseDto;
import com.susocode.inditextest.price.domain.model.Price;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING, injectionStrategy = CONSTRUCTOR)
public interface RestPriceMapper {

  PriceResponseDto toResponseDto(Price price);

}
