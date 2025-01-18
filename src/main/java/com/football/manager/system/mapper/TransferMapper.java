package com.football.manager.system.mapper;

import com.football.manager.system.dto.TransferDto;
import com.football.manager.system.model.Transfer;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(
    componentModel = "spring",
    injectionStrategy = CONSTRUCTOR,
    nullValuePropertyMappingStrategy = IGNORE
)
public interface TransferMapper {
  Transfer toTransfer(TransferDto transferDto);
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateToTransfer(@MappingTarget Transfer transfer, TransferDto transferDto);
}
