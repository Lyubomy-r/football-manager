package com.football.manager.system.repository;

import com.football.manager.system.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player,Long> {

  @Query("SELECT p FROM Player p JOIN FETCH p.team t where p.id=:playerId")
  Optional<Player> findPlayerInfoById(@Param("playerId") Long playerId);
}
