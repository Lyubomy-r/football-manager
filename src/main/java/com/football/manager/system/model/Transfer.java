package com.football.manager.system.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transfers")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transfer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;
  @Column(name = "transfer_price")
  private BigDecimal transferPrice;
  @Column(name = "total_price")
  private BigDecimal totalPrice;
  @OneToOne
  @JoinColumn(name = "player_id")
  private Player player;
  @OneToOne
  @JoinColumn(name = "from_team_id")
  private Team fromTeam;
  @OneToOne
  @JoinColumn(name = "to_team_id")
  private Team toTeam;
  @Column(name = "transfer_date")
  private LocalDateTime transferDate;
}
