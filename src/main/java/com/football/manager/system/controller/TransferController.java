package com.football.manager.system.controller;

import com.football.manager.system.dto.AppResponse;
import com.football.manager.system.dto.TransferDto;
import com.football.manager.system.model.Transfer;
import com.football.manager.system.service.TransferService;
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
@RequestMapping("/transfers")
@RequiredArgsConstructor
public class TransferController {
  private final TransferService transferService;

  @GetMapping
  public ResponseEntity<Page<Transfer>> findAll(@PageableDefault(
      page = 0,
      size = 10,
      sort = {"transferDate"},
      direction = Sort.Direction.DESC) Pageable pageable) {
    Page<Transfer> transferPage = transferService.findAllTransfers(pageable);

    return ResponseEntity.ok(transferPage);
  }

  @GetMapping("/{transferId}")
  public ResponseEntity<Transfer> findTransferById(@PathVariable("transferId") Long transferId) {
    Transfer transfer = transferService.findTransferById(transferId);

    return ResponseEntity.ok(transfer);
  }

  @PostMapping
  public ResponseEntity<Transfer> createTransfer(@Valid @RequestBody TransferDto transferDto) {
    Transfer transfer = transferService.createTransfer(transferDto);

    return ResponseEntity.ok(transfer);
  }

  @PutMapping("/{transferId}")
  public ResponseEntity<Transfer> updateTransfer(@PathVariable("transferId") Long transferId,
                                                 @Valid @RequestBody TransferDto transferDto) {
    Transfer transfer = transferService.updateTransfer(transferId, transferDto);

    return ResponseEntity.ok(transfer);
  }

  @DeleteMapping("/{transferId}")
  public ResponseEntity<AppResponse> deleteTeamById(@PathVariable("transferId") Long transferId){
    String message = transferService.deleteTransferById(transferId);
    AppResponse response = new AppResponse(
        HttpStatus.OK.value(), message);
    return ResponseEntity.ok(response);
  }
}
