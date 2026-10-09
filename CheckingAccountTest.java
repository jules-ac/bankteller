package lab4;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests a class that implements the IBankAccount interface.
 */
public class CheckingAccountTest {

    private IBankAccount account;

    /**
     * Creates a fresh account before each test.
     */
    @BeforeEach
    public void setUp() {
        account = new CheckingAccount(
                "jsmith",
                "password123",
                1000.00
        );
    }

    /**
     * Tests that the initial balance is correct.
     */
    @Test
    public void testInitialBalance() {
        assertEquals(1000.00, account.checkBalance(), 0.001);
    }

    /**
     * Tests depositing money into the account.
     */
    @Test
    public void testDeposit() {
        account.deposit(250.00);

        assertEquals(1250.00, account.checkBalance(), 0.001);
    }

    /**
     * Tests making a valid withdrawal.
     */
    @Test
    public void testWithdrawal() throws InsufficientFundsException {
        account.withdrawal(200.00);

        assertEquals(800.00, account.checkBalance(), 0.001);
    }

    /**
     * Tests withdrawing the entire account balance.
     */
    @Test
    public void testWithdrawEntireBalance()
            throws InsufficientFundsException {

        account.withdrawal(1000.00);

        assertEquals(0.00, account.checkBalance(), 0.001);
    }

    /**
     * Tests that attempting to withdraw more money than is available
     * causes an InsufficientFundsException.
     */
    @Test
    public void testInsufficientFunds() {
        assertThrows(
                InsufficientFundsException.class,
                () -> account.withdrawal(1500.00)
        );
    }

    /**
     * Tests that the balance does not change when an
     * InsufficientFundsException occurs.
     */
    @Test
    public void testBalanceUnchangedAfterFailedWithdrawal() {
        assertThrows(
                InsufficientFundsException.class,
                () -> account.withdrawal(1500.00)
        );

        assertEquals(1000.00, account.checkBalance(), 0.001);
    }

    /**
     * Tests the username getter.
     */
    @Test
    public void testGetUsername() {
        assertEquals("jsmith", account.getUsername());
    }

    /**
     * Tests the password getter.
     */
    @Test
    public void testGetPassword() {
        assertEquals("password123", account.getPassword());
    }

    /**
     * Tests that getAccountType returns an expected value.
     */
    @Test
    public void testGetAccountType() {
        assertEquals("checking", account.getAccountType().toLowerCase());
    }

    /**
     * Tests that the account can be converted to a data string.
     */
    @Test
    public void testToDataString() {
        String data = account.toDataString();

        assertNotNull(data);
        assertFalse(data.isEmpty());

        assertTrue(data.contains("jsmith"));
    }

    /**
     * Tests monthly account processing.
     *
     * The expected result will depend on how monthlyProcessing()
     * is implemented in the account class.
     */
    @Test
    public void testMonthlyProcessing() {

        double startingBalance = account.checkBalance();

        account.monthlyProcessing();

        assertTrue(account.checkBalance() < startingBalance);
    }
}