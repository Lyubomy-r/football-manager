package com.football.manager.system.service;

import com.football.manager.system.dto.TransferDto;
import com.football.manager.system.exception.GeneralException;
import com.football.manager.system.mapper.TransferMapper;
import com.football.manager.system.model.Player;
import com.football.manager.system.model.Team;
import com.football.manager.system.model.Transfer;
import com.football.manager.system.repository.TransferRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class TransferServiceImp implements TransferService {

  private final TransferRepository transferRepository;
  private final TransferMapper transferMapper;
  private final PlayerService playerService;
  private final TeamService teamService;
  public static final String NOT_FOUND_BY_ID_MESSAGE = "Transfer with ID [%s] not found.";

  @Override
  public Page<Transfer> findAllTransfers(Pageable pageable) {
    Page<Transfer> transferPage = transferRepository.findAll(pageable);

    return transferPage;
  }

  @Override
  public Transfer findTransferById(Long transferId) {
    Transfer transfer = transferRepository.findById(transferId)
        .orElseThrow(() ->
            new GeneralException(String.format(NOT_FOUND_BY_ID_MESSAGE, transferId), HttpStatus.NOT_FOUND));

    return transfer;
  }

  @Override
  @Transactional(isolation = Isolation.SERIALIZABLE)
  public Transfer createTransfer(TransferDto transferDto) {
    Transfer newTransfer = new Transfer();
    Player player = playerService.findById(transferDto.getPlayerId());
    if(player.getTeam().getId().equals(transferDto.getToTeamId())){
     throw new GeneralException("The player is already on this team.", HttpStatus.CONFLICT);
    }
    Team fromTeam = teamService.findById(transferDto.getFromTeamId());
    Team toTeam = teamService.findById(transferDto.getToTeamId());
    BigDecimal transferPrice = calculateTransferPrice(player.getAge(), player.getExperience());
    BigDecimal totalPrice = calculateTransferTotalPrice(transferPrice, fromTeam.getTransferCommission());
    transferFundsBetweenTeams(fromTeam, toTeam, totalPrice);
    newTransfer.setTransferPrice(transferPrice);
    newTransfer.setTotalPrice(totalPrice);
    newTransfer.setPlayer(player);
    newTransfer.setFromTeam(fromTeam);
    newTransfer.setToTeam(toTeam);
    newTransfer.setTransferDate(LocalDateTime.now());
    Transfer savedTransfer = transferRepository.save(newTransfer);
    playerService.updatePlayerTeam(player, toTeam);

    return savedTransfer;
  }

  @Override
  @Transactional
  public Transfer updateTransfer(Long transferId, TransferDto transferDto) {
    Transfer transfer = transferRepository.findById(transferId)
        .orElseThrow(() ->
            new GeneralException(String.format(NOT_FOUND_BY_ID_MESSAGE, transferId), HttpStatus.NOT_FOUND));
    transferMapper.updateToTransfer(transfer, transferDto);

    return transferRepository.save(transfer);
  }

  @Override
  @Transactional
  public String deleteTransferById(Long transferId) {
    transferRepository.deleteById(transferId);

    return String.format("Transfer with Id %s has been deleted", transferId);
  }

  private BigDecimal calculateTransferPrice(Integer playerAge, Integer playerExperience) {

    System.out.println(String.format("playerAge {%s} ,playerExperience {%s} ", playerAge, playerExperience));
    if ((playerExperience == null || playerExperience < 0) || playerAge == null || playerAge < 0) {
      throw new GeneralException("Player experience must be greater than 0. Experience and Age must not be null.", HttpStatus.BAD_REQUEST);
    }
    BigDecimal age = BigDecimal.valueOf(playerAge);
    BigDecimal experience = BigDecimal.valueOf(playerExperience);

    return age.multiply(BigDecimal.valueOf(100000)).divide(experience, 2, RoundingMode.HALF_UP);
  }

  private BigDecimal calculateTransferTotalPrice(BigDecimal transferPrice, Integer transferCommission) {
    log.info("transferPrice {} . transferCommission {}",transferPrice, transferCommission);
    if ((transferPrice == null || transferPrice.compareTo(BigDecimal.ZERO) < 0) || transferCommission == null || transferCommission <= 0) {
      throw new GeneralException("transferPrice  must be greater than 0. transferCommission and transferPrice must not be null.", HttpStatus.BAD_REQUEST);
    }
    BigDecimal commissionRate = BigDecimal.valueOf(transferCommission).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    BigDecimal commission = transferPrice.multiply(commissionRate);

    return transferPrice.add(commission);
  }

  void transferFundsBetweenTeams(Team fromTeam, Team toTeam, BigDecimal totalPrice) {
    BigDecimal minusBalance = toTeam.getBalance().subtract(totalPrice);
    if (minusBalance.compareTo(BigDecimal.ZERO) < 0) {
      throw new GeneralException(String.format("Teams with ID (%s) do not have enough funds on their balance sheet", toTeam.getId()), HttpStatus.BAD_REQUEST);
    }
    toTeam.setBalance(minusBalance);
    fromTeam.setBalance(fromTeam.getBalance().add(totalPrice));
  }
}
