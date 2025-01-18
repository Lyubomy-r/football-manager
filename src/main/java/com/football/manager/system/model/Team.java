package com.football.manager.system.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "teams")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Team {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;
  @Column(name = "name")
  private String name;
  @Column(name = "balance")
  private BigDecimal balance;
  @Column(name = "founded_year")
  private Integer foundedYear;
  @Column(name = "city")
  private String city;
  @Column(name = "transfer_commission")
  private Integer transferCommission;
  @Column(name = "created_at")
  private LocalDateTime createdAt;
  @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
  @JsonIgnore
  private List<Player> playerList;
}
