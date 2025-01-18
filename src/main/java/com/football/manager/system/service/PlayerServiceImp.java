package com.football.manager.system.service;

import com.football.manager.system.dto.PlayerDto;
import com.football.manager.system.exception.GeneralException;
import com.football.manager.system.mapper.PlayerMapper;
import com.football.manager.system.model.Player;
import com.football.manager.system.model.Team;
import com.football.manager.system.repository.PlayerRepository;
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
public class PlayerServiceImp implements PlayerService {
  private final PlayerRepository playerRepository;
  private final PlayerMapper playerMapper;
  private final TeamService teamService;

  public static final String NOT_FOUND_BY_ID_MESSAGE_U = "User with ID [%s] not found.";

  @Override
  public Page<PlayerDto> findAllPlayers(Pageable pageable) {
    Page<Player> playerPage = playerRepository.findAll(pageable);

    return playerPage.map(playerMapper::toPlayerDto);
  }

  @Override
  public PlayerDto findPlayerById(Long playerId) {
    Player player = playerRepository.findPlayerInfoById(playerId)
        .orElseThrow(() ->
            new GeneralException(String.format(NOT_FOUND_BY_ID_MESSAGE_U, playerId), HttpStatus.NOT_FOUND));

    return playerMapper.toPlayerDto(player);
  }

  @Override
  @Transactional
  public PlayerDto createPlayer(PlayerDto playerDto) {
    Team team = teamService.findById(playerDto.getTeam().getId());
    Player newPlayer = playerMapper.toPlayerAndSetTeam(playerDto,team);
    newPlayer.setCreatedAt(LocalDateTime.now());

    Player savedPlayer = playerRepository.save(newPlayer);

    return playerMapper.toPlayerDto(savedPlayer);
  }

  @Override
  @Transactional
  public PlayerDto updatePlayerFromDto(Long playerId, PlayerDto playerDto) {
    Player player = playerRepository.findById(playerId)
        .orElseThrow(() ->
            new GeneralException(String.format(NOT_FOUND_BY_ID_MESSAGE_U, playerId), HttpStatus.NOT_FOUND));
    playerMapper.updateToPlayer(player,playerDto);
    Player updatedPlayer = playerRepository.save(player);
    return playerMapper.toPlayerDto(updatedPlayer);
  }

  @Override
  @Transactional
  public Player updatePlayerTeam(Player player, Team toTeam) {
    player.setTeam(toTeam);
    Player updatedPlayer = playerRepository.save(player);
    return updatedPlayer;
  }

  @Override
  @Transactional
  public String deletePlayerById(Long playerId) {
    playerRepository.deleteById(playerId);

    return String.format("Player with Id %s has been deleted", playerId);
  }

  @Override
  public Player findById(Long playerId) {
    Player player = playerRepository.findById(playerId)
        .orElseThrow(() ->
            new GeneralException(String.format(NOT_FOUND_BY_ID_MESSAGE_U, playerId), HttpStatus.NOT_FOUND));

    return player;
  }
}
