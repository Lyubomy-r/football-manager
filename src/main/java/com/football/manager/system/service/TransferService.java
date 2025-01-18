package com.football.manager.system.service;

import com.football.manager.system.dto.TransferDto;
import com.football.manager.system.model.Transfer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransferService {
  Page<Transfer> findAllTransfers(Pageable pageable);
  Transfer findTransferById(Long transferId);
  Transfer createTransfer(TransferDto transferDto);
  Transfer updateTransfer(Long transferId, TransferDto transferDto);
  String deleteTransferById(Long transferId);
}
