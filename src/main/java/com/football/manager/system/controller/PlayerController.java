package com.football.manager.system.controller;

import com.football.manager.system.dto.AppResponse;
import com.football.manager.system.dto.PlayerDto;
import com.football.manager.system.service.PlayerService;
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
@RequestMapping("/players")
@RequiredArgsConstructor
public class PlayerController {
  private final PlayerService playerService;

  @GetMapping
  public ResponseEntity<Page<PlayerDto>> findAll(@PageableDefault(
      page = 0,
      size = 10,
      sort = {"createdAt"},
      direction = Sort.Direction.DESC) Pageable pageable){
    Page<PlayerDto> playerPage = playerService.findAllPlayers(pageable);

    return ResponseEntity.ok(playerPage);
  }

  @GetMapping("/{playerId}")
  public ResponseEntity<PlayerDto> findPlayerById(@PathVariable("playerId") Long playerId){
    PlayerDto player = playerService.findPlayerById(playerId);

    return ResponseEntity.ok(player);
  }

  @PostMapping
  public ResponseEntity<PlayerDto> createPlayer(@Valid @RequestBody PlayerDto playerDto){
    PlayerDto player = playerService.createPlayer(playerDto);

    return ResponseEntity.ok(player);
  }

  @PutMapping("/{playerId}")
  public ResponseEntity<PlayerDto> updatePlayer(@Valid @PathVariable("playerId") Long playerId,
                                        @RequestBody PlayerDto playerDto){
    PlayerDto player = playerService.updatePlayerFromDto(playerId,playerDto);

    return ResponseEntity.ok(player);
  }

  @DeleteMapping ("/{playerId}")
  public ResponseEntity<AppResponse> deletePlayer(@PathVariable("playerId") Long playerId){
    String message = playerService.deletePlayerById(playerId);
    AppResponse response = new AppResponse(
        HttpStatus.OK.value(), message);
    return ResponseEntity.ok(response);
  }
}
