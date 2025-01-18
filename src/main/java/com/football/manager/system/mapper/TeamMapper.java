package com.football.manager.system.mapper;

import com.football.manager.system.dto.TeamDto;
import com.football.manager.system.model.Team;
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
public interface TeamMapper {

  Team toTeam(TeamDto teamDto);
  TeamDto toTeamDto(Team team);
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateToTeam(@MappingTarget Team team, TeamDto teamDto);
}
