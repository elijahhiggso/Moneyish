/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dottorsoft.SimpleBlockChain.networking;
import com.dottorsoft.SimpleBlockChain.core.Block;
import com.dottorsoft.SimpleBlockChain.core.TransactionOutput;
import com.dottorsoft.SimpleBlockChain.core.Wallet;
import com.dottorsoft.SimpleBlockChain.util.Parameters;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.net.*;
import java.util.*;
import wallet.CDClientUI;
/**
 *
 * @author jonan
 */
public class RecieveThread implements Runnable{
        public static Socket sock=null;
	BufferedReader recieve=null;
        int port;
        String IP;

	public RecieveThread(Socket sock) {
		this.sock = sock;
                this.port = sock.getPort();
                this.IP = sock.getInetAddress().getHostAddress();
	}
	//end constructor
	
	public void run() {
            while (true){
                
                try{	
			recieve = new BufferedReader(new InputStreamReader(this.sock.getInputStream()));//get inputstream
			String msgRecieved = null;
			while((msgRecieved = recieve.readLine())!= null)
			{
                            Map <String, String> message = new HashMap <String, String>();
                            System.out.println(msgRecieved);//print the message from client
                            ObjectMapper mapper = new ObjectMapper();
                            mapper.enable(SerializationFeature.INDENT_OUTPUT);
                            message = mapper.readValue(msgRecieved.getBytes(), Map.class);
                            if (message.get("message").equals("update") ){
                                        update(message);
                                    } else if(message.get("message").equals("send")){
                                        String returnMsg = send(message);
                                        if (returnMsg.equals("updateBalance")){
                                            SendThread.message = returnMsg;
                                        }
                                    } else if(message.get("message").equals("updateBalance")){
                                        updateBalance();
                                    }
			}
			
			

		}catch(Exception e){System.out.println(e.getMessage());}
            }
            
	}//end run
        public void reconnect(){
            try{
                sock = new Socket(IP, port);
            } catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        
        public static void update(Map <String, String> map){
           Gson gson = new Gson();
           Type empMapType = new TypeToken<Map<String, Block>>() {}.getType();
           
           Map <String, Block> temp = gson.fromJson(map.get("Blockchain"), empMapType);
           for (Map.Entry<String, Block> j : temp.entrySet()){
                if(!Parameters.blockchain.containsKey(j.getKey())){
                    Parameters.blockchain.put(j.getKey(), j.getValue());
                }
            }
           
           empMapType = new TypeToken<Map<String,TransactionOutput>>() {}.getType();
           Map <String, TransactionOutput> tempTrans = gson.fromJson(map.get("UTXOs"), empMapType);
           for (Map.Entry<String, TransactionOutput> j : tempTrans.entrySet()){
                if(!Parameters.UTXOs.containsKey(j.getKey())){
                    Parameters.UTXOs.put(j.getKey(), j.getValue());
                }
            }
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
