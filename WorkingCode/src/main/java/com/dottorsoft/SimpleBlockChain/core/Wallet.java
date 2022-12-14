package com.dottorsoft.SimpleBlockChain.core;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.net.*;
import java.io.*;
import java.util.*;
import java.net.InetAddress;

import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;

import com.dottorsoft.SimpleBlockChain.Main;
import com.dottorsoft.SimpleBlockChain.util.StringUtil;
import com.dottorsoft.SimpleBlockChain.networking.Peer2Peer;
import com.dottorsoft.SimpleBlockChain.networking.Peer;
import com.dottorsoft.SimpleBlockChain.util.Parameters;
import java.util.LinkedList;

public class Wallet {
	public static float balance = 0;
	private BCECPrivateKey privateKey;
	private String publicKey;
        public static float val =0;
	
	public HashMap<String,TransactionOutput> UTXOs = new HashMap<String,TransactionOutput>();
	
	public Wallet() {
		generateKeyPair();
	}
		
	public void generateKeyPair() {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA","BC");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
			// Initialize the key generator and generate a KeyPair
			keyGen.initialize(ecSpec, random); //256 
	        KeyPair keyPair = keyGen.generateKeyPair();
	        // Set the public and private keys from the keyPair
	        setPrivateKey((BCECPrivateKey) keyPair.getPrivate());
	        setPublicKey(StringUtil.getStringFromKey(keyPair.getPublic()));
	        
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public float getBalance() {
		float total = 0;
		TransactionOutput UTXO;
        for (Map.Entry<String, TransactionOutput> item: Parameters.UTXOs.entrySet()){
            System.out.println("hi my pub key is " + publicKey);
        	UTXO = item.getValue();
            if(UTXO.isMine(publicKey)) { //if output belongs to me ( if coins belong to me )
               	UTXOs.put(UTXO.id,UTXO); //add it to our list of unspent transactions.
            	total += UTXO.value ; 
            }
        }  
        
                if (balance == 0){
                    balance = total;  
                }
                return total;
	}
	public void sendBalance(int value){
           Wallet.balance-=-value;
        }
        public void receiveBalance(int value){
            Wallet.balance+=value;
        }
	public Transaction sendFunds(String _recipient,float value ) throws NoSuchAlgorithmException, InvalidKeySpecException {
		if(Wallet.balance < value) {
			System.out.println("#Not Enough funds to send transaction. Transaction Discarded.");
			return null;
		}
		ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
		
		float total = 0;
		TransactionOutput UTXO;
		for (Map.Entry<String, TransactionOutput> item: UTXOs.entrySet()){
			UTXO= item.getValue();
			total += UTXO.value;
			inputs.add(new TransactionInput(UTXO.id));
			if(total > value) break;
		}
		
		Transaction newTransaction = new Transaction(publicKey, _recipient , value, inputs);
		newTransaction.generateSignature(privateKey);
		
		for(TransactionInput input: inputs){
			UTXOs.remove(input.transactionOutputId);
		}
		
		return newTransaction;
	}

	public BCECPrivateKey getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(BCECPrivateKey privateKey) {
		this.privateKey = privateKey;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
        
        public String findIP() throws Exception{
            InetAddress localhost = InetAddress.getLocalHost();
//            System.out.println("System IP Address : " +
//                          (localhost.getHostAddress()).trim());

            // Find public IP address
            String systemipaddress = "";
            try
            {
                URL url_name = new URL("http://bot.whatismyipaddress.com");

                BufferedReader sc =
                new BufferedReader(new InputStreamReader(url_name.openStream()));

                // reads system IPAddress
                systemipaddress = sc.readLine().trim();
            }
            catch (Exception e)
            {
                systemipaddress = "Cannot Execute Properly";
            }
            //System.out.println("Public IP Address: " + systemipaddress +"\n");
            LinkedList<Peer> p = Peer2Peer.peers;    
            return "b";

            }
	
}

