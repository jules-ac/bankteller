package lab4;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the SavingsAccount class.
 */
public class SavingsAccountTest {

    private IBankAccount account;

    /**
     * Creates a new savings account before each test.
     */
    @BeforeEach
    public void setUp() {
        account = new SavingsAccount(
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
     * Tests depositing money into the savings account.
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
     * Tests that withdrawing more money than is available
     * throws an InsufficientFundsException.
     */
    @Test
    public void testInsufficientFunds() {
        assertThrows(
                InsufficientFundsException.class,
                () -> account.withdrawal(1500.00)
        );
    }

    /**
     * Tests that the balance does not change after a failed withdrawal.
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
     * Tests the account type.
     */
    @Test
    public void testGetAccountType() {
        assertEquals("savings", account.getAccountType().toLowerCase());
    }

    /**
     * Tests that the account produces a data string.
     */
    @Test
    public void testToDataString() {
        String data = account.toDataString();

        assertNotNull(data);
        assertFalse(data.isEmpty());
        assertTrue(data.contains("jsmith"));
    }

    /**
     * Tests monthly processing for the savings account.
     *
     * Savings accounts commonly add interest during monthly
     * processing, so the balance should increase.
     */
    @Test
    public void testMonthlyProcessing() {

        double startingBalance = account.checkBalance();

        account.monthlyProcessing();

        assertTrue(account.checkBalance() > startingBalance);
    }

    /**
     * Tests that multiple deposits are reflected in the balance.
     */
    @Test
    public void testMultipleDeposits() {
        account.deposit(100.00);
        account.deposit(50.00);
        account.deposit(25.00);

        assertEquals(1175.00, account.checkBalance(), 0.001);
    }

    /**
     * Tests a sequence of deposits and withdrawals.
     */
    @Test
    public void testMultipleTransactions()
            throws InsufficientFundsException {

        account.deposit(500.00);
        account.withdrawal(200.00);
        account.deposit(100.00);
        account.withdrawal(50.00);

        assertEquals(1350.00, account.checkBalance(), 0.001);
    }
}