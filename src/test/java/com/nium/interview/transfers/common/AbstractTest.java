package com.nium.interview.transfers.common;

import java.io.File;
import java.nio.file.Paths;

public abstract class AbstractTest {

    protected void cleanResourcesDirectory() {
        final File dir = Paths.get("src", "main", "resources").toFile();
        for (File file : dir.listFiles()) {
            if (!file.getName().equals("accounts.txt") && !file.getName().equals("transfers.txt") && !file.getName().equals("application.properties")
                    && !file.getName().equals("transfers_incompleteData.txt") && !file.getName().equals("invalid_accounts.txt")) {
                file.delete();
            }
        }
    }
}
