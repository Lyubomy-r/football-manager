package com.football.manager.system.dto;

import com.football.manager.system.model.Position;
import com.football.manager.system.model.Team;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerDto {

  private Long id;
  @NotBlank(message = "first name can't be empty")
  @Pattern(regexp = "^[a-zA-Z ]+$", message = "first name must contain only characters")
  private String firstName;
  @NotBlank(message = "last name can't be empty")
  @Pattern(regexp = "^[a-zA-Z ]+$", message = "last name must contain only characters")
  private String lastName;
  @NotNull(message = "date Of birth can't be null")
  private LocalDate dateOfBirth;
  @NotNull(message = "position can't be null")
  private Position position;
  @NotNull(message = "experience can't be null")
  @Min(value = 0, message = "experience can't be negative")
  private Integer experience;
  @NotNull(message = "age can't be null")
  @Min(value = 0, message = "age can't be negative")
  private Integer age;
  @NotBlank(message = "nationality can't be empty")
  @Pattern(regexp = "^[a-zA-Z ]+$", message = "nationality must contain only characters")
  private String nationality;
  private LocalDateTime createdAt;
  private Team team;
}
