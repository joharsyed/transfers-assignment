package com.nium.interview.transfers;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.nium.interview.transfers.repository.AccountRepository;
import com.nium.interview.transfers.repository.TransferRepository;
import com.nium.interview.transfers.service.AccountService;
import com.nium.interview.transfers.service.TransferService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TransfersApplicationTests {

	@Autowired
	private AccountService accountService;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private TransferService transferService;
	@Autowired
	private TransferRepository transferRepository;

	@Test
	void contextLoads() {
		assertNotNull(accountRepository);
		assertNotNull(accountService);
		assertNotNull(transferRepository);
		assertNotNull(transferService);
	}

}
