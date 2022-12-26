package com.nium.interview.transfers;

import com.nium.interview.transfers.service.AccountService;
import com.nium.interview.transfers.service.ResultService;
import com.nium.interview.transfers.service.TransferService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * <p>
 * A Command-line application to parse and process a transfers file and provide the business requirements, namely:
 * 	<ul>
 * 	    <li>1. Print the final balances on all bank accounts</li>
 * 	    <li>2. Print the bank account with the highest balance</li>
 * 	    <li>3. Print the most frequently used source bank account</li>
 * 	</ul>
 * </p>
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.nium.interview.transfers")
@Log4j2
@EnableJpaRepositories(basePackages = "com.nium.interview.transfers.repository")
@ComponentScan("com.nium.interview.transfers")
public class TransfersApplication implements CommandLineRunner {

    @Autowired
    private AccountService accountService;
    @Autowired
    private TransferService transferService;
    @Autowired
    private ResultService resultService;

    /**
     * @param args containing the input to the program.
     */
    public static void main(String... args) {
        log.info("Application is starting");
        SpringApplication.run(TransfersApplication.class, args);
    }

    @Override
    public void run(final String... args) {
        accountService.initAccounts(null);
        transferService.doTransfers(null);
        resultService.writeResultToFile(resultService.prepareResult(), null);
    }
}
