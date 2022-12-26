package com.nium.interview.transfers.service.impl;

import com.nium.interview.transfers.model.Transfer;
import com.nium.interview.transfers.repository.TransferRepository;
import com.nium.interview.transfers.service.AccountService;
import com.nium.interview.transfers.service.TransferService;
import com.nium.interview.transfers.util.DateUtil;
import com.nium.interview.transfers.util.FileUtil;
import com.nium.interview.transfers.util.Validator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The service is used to carry out transactions
 */
@Service
public class TransferServiceImpl implements TransferService {

    private static final String DEFAULT_INPUT_FILE = "transfers.txt";

    private final AccountService accountService;
    private final TransferRepository transferRepository;

    @Autowired
    public TransferServiceImpl(final AccountService accountService, final TransferRepository transferRepository) {
        this.accountService = accountService;
        this.transferRepository = transferRepository;
    }

    /**
     * Carries out transfers according to the given file
     * @param transferFileName the name of the file containing the transfer details
     */
    @Override
    public void doTransfers(final String transferFileName) {
        final String fileName = transferFileName == null ? DEFAULT_INPUT_FILE : transferFileName;
        FileUtil.readLinesFromFile(fileName).stream().map(String::trim).filter(this::isTransferData).forEach(this::doTransfer);
    }

    private boolean isTransferData(final String str) {
        return StringUtils.isNumeric(String.valueOf(str.charAt(0)));
    }

    private Transfer doTransfer(final String transferData) {
        final String[] transferFields = transferData.split(",");
        if (transferFields.length != 5) {
            throw new IllegalArgumentException("Transfer data is invalid");
        }
        final Transfer transfer = new Transfer();
        transfer.setSourceAccountId(Long.parseLong(Validator.validateId(transferFields[0].trim())));
        transfer.setDestinationAccountId(Long.parseLong(Validator.validateId(transferFields[1].trim())));
        transfer.setAmount(Double.parseDouble(transferFields[2].trim()));
        transfer.setDate(DateUtil.toLocalDate(transferFields[3].trim(), "dd/MM/yyyy"));
        transfer.setId(Long.parseLong(Validator.validateId(transferFields[4].trim())));
        accountService.updateAccounts(transfer.getSourceAccountId(), transfer.getDestinationAccountId(), transfer.getAmount());
        return transferRepository.save(transfer);
    }
}
