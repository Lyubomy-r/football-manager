package com.football.manager.system.service;

import com.football.manager.system.dto.TeamDto;
import com.football.manager.system.exception.GeneralException;
import com.football.manager.system.model.Team;
import com.football.manager.system.repository.TeamRepository;
import com.football.manager.system.mapper.TeamMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamServiceImp implements TeamService{

  private final TeamRepository teamRepository;
  private final TeamMapper teamMapper;
  public static final String NOT_FOUND_BY_ID_MESSAGE = "Team with ID [%s] not found.";

  @Override
  public Page<Team> findAllTeams(Pageable pageable) {
    Page<Team> teamPage = teamRepository.findAll(pageable);

    return teamPage;
  }

  @Override
  public TeamDto findTeamById(Long teamId) {
    Team team = teamRepository.findById(teamId)
        .orElseThrow(() ->
            new GeneralException(String.format(NOT_FOUND_BY_ID_MESSAGE, teamId), HttpStatus.NOT_FOUND));

    return teamMapper.toTeamDto(team);
  }

  @Override
  @Transactional
  public TeamDto createTeam(TeamDto teamDto) {
    Team newTeam = teamMapper.toTeam(teamDto);
    newTeam.setCreatedAt(LocalDateTime.now());
    Team savedTeam = teamRepository.save(newTeam);

    return  teamMapper.toTeamDto(savedTeam);
  }

  @Override
  @Transactional
  public TeamDto updateTeam(Long teamId, TeamDto teamDto) {
    Team team = teamRepository.findById(teamId)
        .orElseThrow(() ->
            new GeneralException(String.format(NOT_FOUND_BY_ID_MESSAGE, teamId), HttpStatus.NOT_FOUND));
    teamMapper.updateToTeam(team,teamDto);
    Team updatedTeam = teamRepository.save(team);

    return teamMapper.toTeamDto(updatedTeam);
  }

  @Override
  @Transactional
  public String deleteTeamById(Long teamId) {
    teamRepository.deleteById(teamId);

    return String.format("Team with Id %s has been deleted", teamId);
  }

  @Override
  public Team findById(Long teamId) {
    Team team = teamRepository.findById(teamId)
        .orElseThrow(() ->
            new GeneralException(String.format(NOT_FOUND_BY_ID_MESSAGE, teamId), HttpStatus.NOT_FOUND));

    return team;
  }
}
