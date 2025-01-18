package com.football.manager.system.mapper;

import com.football.manager.system.dto.PlayerDto;
import com.football.manager.system.model.Player;
import com.football.manager.system.model.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(
    componentModel = "spring",
    injectionStrategy = CONSTRUCTOR,
    nullValuePropertyMappingStrategy = IGNORE
)
public interface PlayerMapper {
  Player toPlayer(PlayerDto playerDto);
  @Mapping(target = "id", source = "playerDto.id")
  @Mapping(target = "createdAt", source = "playerDto.createdAt")
  @Mapping(target = "team", source = "playerTeam")
  Player toPlayerAndSetTeam(PlayerDto playerDto, Team playerTeam);
  @Mapping(target = "age",source = "age")
  PlayerDto toPlayerDto(Player player);
  void updateToPlayer(@MappingTarget Player player, PlayerDto playerDto);
}
