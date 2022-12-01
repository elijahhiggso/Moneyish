/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dottorsoft.SimpleBlockChain.networking;

import com.dottorsoft.SimpleBlockChain.core.Block;
import com.dottorsoft.SimpleBlockChain.core.TransactionOutput;
import com.dottorsoft.SimpleBlockChain.core.Wallet;
import com.dottorsoft.SimpleBlockChain.util.Parameters;
import java.io.*;
import java.net.*;
import java.util.*;

import com.fasterxml.jackson.databind.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import wallet.CDClientUI;
/**
 *
 * @author jonan
 */
public class RecieveFromClientThread implements Runnable
{
	private BufferedReader brBufferedReader = null;
        private Socket cSocket = null;
        private int socket = 0;
	public RecieveFromClientThread(Socket clientSocket)
	{
		cSocket = clientSocket;
	}
	//end constructor

	public void run() {
             loop();
	}
        public void loop(){
            try 
		{
			while(true){
                            
                            System.out.println("Connection Established");
                            brBufferedReader = new BufferedReader(new InputStreamReader(this.cSocket.getInputStream()));  

                            String messageString;
                            String json;
				while((messageString = brBufferedReader.readLine())!= null){//assign message from client to messageString
                                    Map <String, String> message = new HashMap <String, String>();
                                    System.out.println(messageString);//print the message from client
                                    ObjectMapper mapper = new ObjectMapper();
                                    mapper.enable(SerializationFeature.INDENT_OUTPUT);
                                    message = mapper.readValue(messageString.getBytes(), Map.class);
                                    if (message.get("message").equals("update") ){
                                        System.out.println("message.get(message) = update");
                                        update(message);
                                    } else if(message.get("message").equals("send")){
                                        System.out.println("message.get(message) = send");
                                        String returnMsg = send(message);
                                        if (returnMsg.equals("updateBalance")){
                                            System.out.println();
                                            SendToClientThread.message = returnMsg;
                                        }
                                    } else if(message.get("message").equals("updateBalance")){
                                        updateBalance();
                                    }
                                }
				
				System.out.println("Peer has Disconnected");
				//this.clientSocket.close();
				//System.exit(0);
			}
		}catch(Exception ex){System.out.println(ex.getMessage());}
                System.out.println("Server Stopped");
        }
        public static void update(Map <String, String> map){
           Gson gson = new Gson();
           Type empMapType = new TypeToken<Map<String, Block>>() {}.getType();
           Parameters.blockchain = gson.fromJson(map.get("Blockchain"), empMapType);
           
           empMapType = new TypeToken<Map<String,TransactionOutput>>() {}.getType();
           Parameters.UTXOs = gson.fromJson(map.get("UTXOs"), empMapType);
        }
        public static String send(Map <String, String> map){
            System.out.println("Param pubKey:" +Parameters.pubKey + "\nIncoming pubKey:"+ map.get("pubKey"));
            if(Parameters.pubKey.equals(map.get("pubKey"))){
                
                float value = Float.parseFloat(map.get("value"));
                value = (float) value;
                Wallet.balance += value;
                CDClientUI.balanceCoins.setText(String.valueOf(Wallet.balance)+ " M");
                return "updateBalance";
                
            }
            return "Invalid PubKey";
        }
        public void updateBalance(){
            Wallet.balance -= Wallet.val;
            System.out.println(Wallet.val);
        }
}
