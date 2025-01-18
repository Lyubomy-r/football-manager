package com.football.manager.system.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferDto {
  private Long id;
  @Min(value = 0, message = "transferPrice can't be negative")
  private BigDecimal transferPrice;
  @Min(value = 0, message = "totalPrice can't be negative")
  private BigDecimal totalPrice;
  @NotNull(message = "playerId can't be null")
  @Min(value = 1, message = "playerId can't be negative")
  private Long playerId;
  @NotNull(message = "fromTeamId can't be null")
  @Min(value = 1, message = "fromTeamId can't be negative")
  private Long fromTeamId;
  @NotNull(message = "toTeamId can't be null")
  @Min(value = 1, message = "toTeamId can't be negative")
  private Long toTeamId;
  private LocalDateTime transferDate;
}
