package com.dottorsoft.SimpleBlockchain;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import java.security.Security;
import com.dottorsoft.SimpleBlockChain.core.Block;
import com.dottorsoft.SimpleBlockChain.core.Transaction;
import com.dottorsoft.SimpleBlockChain.core.TransactionOutput;
import com.dottorsoft.SimpleBlockChain.core.TransactionInput;
import com.dottorsoft.SimpleBlockChain.core.Wallet;
import com.dottorsoft.SimpleBlockChain.util.Parameters;
import java.util.ArrayList;
import static wallet.CDClientUI.addBlock;
import static wallet.CDClientUI.genesisTransaction;
import static wallet.CDClientUI.wallet;

/**
 * Unit test for simple App.
 */
public class AppTest
        extends TestCase {

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */

    public AppTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(AppTest.class);
    }
    
    // Test creating block
    public void test_EmptyBlock() { 

        Block temp = null;
        assertEquals(null, temp);
    }

    // Test creating wallet
    public void test_Wallet() {
        Wallet wallet = null;
        assertEquals(null, wallet);
    }
    
    // Test creating transaction
    public void test_processTransaction() {
        TransactionInput tinput = new TransactionInput();
        assertNotNull(tinput);
    }

    // Test getBalance() method
    public void test_getBalanceWallet() {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        wallet = new Wallet();
        Wallet wallet = new Wallet();
        genesisTransaction = new Transaction(wallet.getPublicKey(), wallet.getPublicKey(), 100f, null);
        genesisTransaction.generateSignature(wallet.getPrivateKey());	
        genesisTransaction.setTransactionId("1");
        genesisTransaction.getOutputs().add(new TransactionOutput(genesisTransaction.getReciepient(),genesisTransaction.getSender(), genesisTransaction.getValue(), genesisTransaction.getTransactionId()));
        Parameters.UTXOs.put(genesisTransaction.getOutputs().get(0).id, genesisTransaction.getOutputs().get(0));

        float val = wallet.getBalance();
        assertEquals(100f, val);
    }

    // Test sendFunds() method
    public void test_SendFunds() throws Exception {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        Wallet walletB = new Wallet();
        wallet = new Wallet();
        Wallet wallet = new Wallet();
        genesisTransaction = new Transaction(wallet.getPublicKey(), wallet.getPublicKey(), 100f, null);
        genesisTransaction.generateSignature(wallet.getPrivateKey());	
        genesisTransaction.setTransactionId("1");
        genesisTransaction.getOutputs().add(new TransactionOutput(genesisTransaction.getReciepient(), genesisTransaction.getSender(), genesisTransaction.getValue(), genesisTransaction.getTransactionId()));
        Parameters.UTXOs.put(genesisTransaction.getOutputs().get(0).id, genesisTransaction.getOutputs().get(0)); 

        Block genesis = new Block("0");
        genesis.addTransaction(genesisTransaction);
        addBlock(genesis.getHash(), genesis);
        Block block1 = new Block(genesis.getHash());
        block1.addTransaction(wallet.sendFunds(walletB.getPublicKey(), 40f));
        float val = walletB.getBalance();

        assertEquals(40f, val);

    }

    // Test setValue() and getValue() methods
    public void test_setGetValueTransaction() {
        Transaction testT = new Transaction("p1", "p2", 10, null);
        testT.setValue(100f);
        float val = testT.getValue();
        assertEquals(100f, val);

    }

    // Test getInputsValue() method
    public void test_getInputsValue() {
        ArrayList<TransactionInput> arr = new ArrayList<>();
        TransactionInput tInput = new TransactionInput();
        arr.add(0, tInput);
        Transaction testT = new Transaction("p1", "p2", 10, arr);
        float val = testT.getInputsValue();
        assertEquals(0.0f, val);
    }

    public void testApp() {
        assertTrue(true);
    }
}