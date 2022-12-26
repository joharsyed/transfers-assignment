### Assumptions:

- It's assumed that the transfer input starts from a source account id. The application will not accept the input otherwise.
- If there's more than one account accessed most frequently as source accounts (excluding account having id 0), then all of them will be fetched.
- If there's more than one account with the highest balance but equal (excluding account having id 0), then all of them will be fetched.
- The input containing the transfers expects the transfers' fields in this order: SOURCE_ACCT, DESTINATION_ACCT, AMOUNT, DATE, TRANSFERID

### Execution

- Run 'mvn compile' to compile the application
- Run 'mvn spring-boot:run' to run the application
- Run 'mvn test' to run the tests

### Output

When the application is run, the output is written into a file named "output.txt" (default name if none provided), located in the application's resources folder (src/main/resources).