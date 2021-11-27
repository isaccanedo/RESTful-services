package com.demo.isaccanedo.service.abstractions;

import java.util.List;

import com.demo.isaccanedo.model.Transfer;

public interface ITransferService {

	Transfer createNewTransfer(Transfer transfer);
	
	List<Transfer> findAllTransfersFrom24Hours(Long userId);

}
