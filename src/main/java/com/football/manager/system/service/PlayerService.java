package com.football.manager.system.service;

import com.football.manager.system.dto.PlayerDto;
import com.football.manager.system.model.Player;
import com.football.manager.system.model.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlayerService {
  Page<PlayerDto> findAllPlayers(Pageable pageable);
  PlayerDto findPlayerById(Long playerId);
  PlayerDto createPlayer(PlayerDto playerDto);
  PlayerDto updatePlayerFromDto(Long playerId, PlayerDto playerDto);
  Player updatePlayerTeam(Player player, Team toTeam);
  String deletePlayerById(Long playerId);
  Player findById(Long playerId);
}
