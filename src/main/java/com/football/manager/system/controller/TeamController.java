package com.football.manager.system.controller;

import com.football.manager.system.dto.AppResponse;
import com.football.manager.system.dto.TeamDto;
import com.football.manager.system.model.Team;
import com.football.manager.system.service.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
public class TeamController {
  private final TeamService teamService;

  @GetMapping
  public ResponseEntity<Page<Team>> findAll(@PageableDefault(
      page = 0,
      size = 10,
      sort = {"createdAt"},
      direction = Sort.Direction.DESC) Pageable pageable) {
    Page<Team> teamPage = teamService.findAllTeams(pageable);

    return ResponseEntity.ok(teamPage);
  }

  @GetMapping("/{teamId}")
  public ResponseEntity<TeamDto> findTeamById(@PathVariable("teamId") Long teamId) {
    TeamDto team = teamService.findTeamById(teamId);

    return ResponseEntity.ok(team);
  }

  @PostMapping
  public ResponseEntity<TeamDto> createTeam(@Valid @RequestBody TeamDto teamDto) {
    TeamDto team = teamService.createTeam(teamDto);

    return ResponseEntity.ok(team);
  }

  @PutMapping("/{teamId}")
  public ResponseEntity<TeamDto> updateTeam(@PathVariable("teamId") Long teamId,
                                            @Valid @RequestBody TeamDto teamDto) {
    TeamDto team = teamService.updateTeam(teamId, teamDto);

    return ResponseEntity.ok(team);
  }

  @DeleteMapping("/{teamId}")
  public ResponseEntity<AppResponse> deleteTeamById(@PathVariable("teamId") Long teamId) {
    String message = teamService.deleteTeamById(teamId);
    AppResponse response = new AppResponse(
        HttpStatus.OK.value(), message);
    return ResponseEntity.ok(response);
  }
}
