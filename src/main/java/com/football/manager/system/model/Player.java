package com.football.manager.system.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "players")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Player {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;
  @Column(name = "first_name")
  private String firstName;
  @Column(name = "last_name")
  private String lastName;
  @Column(name = "date_of_birth")
  private LocalDate dateOfBirth;
  @Enumerated(EnumType.STRING)
  @Column(name = "position")
  private Position position;
  @Column(name = "experience")
  private Integer experience;
  @Column(name = "age")
  private Integer age;
  @Column(name = "nationality")
  private String nationality;
  @Column(name = "created_at")
  private LocalDateTime createdAt;
  @ManyToOne
  @JoinColumn(name = "team_id")
  private Team team;
}
