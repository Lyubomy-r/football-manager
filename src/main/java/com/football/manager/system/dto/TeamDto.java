package com.football.manager.system.dto;

import com.football.manager.system.model.Player;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamDto {
  private Long id;
  @NotBlank(message = "Name can't be empty")
  @Pattern(regexp = "^[a-zA-Z ]+$", message = "Name must contain only characters")
  private String name;
  @NotNull(message = "Balance can't be null")
  @Min(value = 0, message = "Balance can't be negative")
  private BigDecimal balance;
  @NotNull(message = "FoundedYear can't be null")
  @Min(value = 0, message = "FoundedYear can't be negative")
  private Integer foundedYear;
  @NotBlank(message = "city can't be empty")
  @Pattern(regexp = "^[a-zA-Z ]+$", message = "city must contain only characters")
  private String city;
  @NotNull(message = "Transfer Commission can't be null")
  @Min(value = 0, message = "Transfer Commission can't be negative")
  private Integer transferCommission;
  private LocalDateTime createdAt;
  private List<Player> playerList;
}
