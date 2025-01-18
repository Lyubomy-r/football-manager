package com.football.manager.system.service;

import com.football.manager.system.dto.TeamDto;
import com.football.manager.system.model.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TeamService {
  Page<Team> findAllTeams(Pageable pageable);
  TeamDto findTeamById(Long teamId);
  TeamDto createTeam(TeamDto teamDto);
  TeamDto updateTeam(Long teamId, TeamDto teamDto);
  String deleteTeamById(Long teamId);
  Team findById(Long teamId);
}
