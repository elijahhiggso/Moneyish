/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dottorsoft.SimpleBlockChain.networking;

import com.dottorsoft.SimpleBlockChain.core.Wallet;
import com.dottorsoft.SimpleBlockChain.util.Parameters;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import wallet.CDClientUI;

/**
 *
 * @author jonan
 */
public class SendThread implements Runnable{
        public static String message = null;
        Socket sock=null;
	PrintWriter print=null;
	BufferedReader brinput=null;

	public SendThread(Socket sock)
	{
		this.sock = sock;
	}//end constructor
	
	public void run(){
	try{
			if(sock.isConnected())
			{
				System.out.println("Connected to Server Successful");
				this.print = new PrintWriter(sock.getOutputStream(), true); 
				while(true){
                                        boolean done =false;
					brinput = new BufferedReader(new InputStreamReader(System.in));
					String msgtoServerString=null;
                                        if (SendThread.message == "sendUpdate"){
                                           message = sendUpdate(message);
                                        } else if(SendThread.message =="send"){
                                            System.out.println("message = send");
                                                message = send();
                                        } else if(SendThread.message =="updateBalance"){
                                            System.out.println("message = updateBalance");
                                            message = updateBalance();
                                        }
                                        if(message != null){
                                            System.out.print(message);
                                            msgtoServerString = message;
                                            this.print.println(msgtoServerString);
                                            this.print.flush();
                                            done = true;
                                        }
                                        message = null;
                                        if (done == true){
                                            SendThread.message = null;
                                        }
					
				}//end while
                                
				}}catch(Exception e){System.out.println(e.getMessage());}
	}//end run method
        public String sendUpdate(String message){
            Gson gson = new Gson();
            HashMap <String, String> messages = new  HashMap<String, String> ();
            messages.put("message", "update");
            messages.put("Blockchain",gson.toJson(Parameters.blockchain));
            messages.put("UTXOs",gson.toJson(Parameters.UTXOs));
            
            return gson.toJson(messages);
        }
         public String send(){
            Gson gson = new Gson();
            HashMap <String, String> messages = new  HashMap<String, String> ();
            messages.put("message", "send");
            String value = String.valueOf(CDClientUI.amountSpinner.getValue()) ;
            Wallet.val = Float.parseFloat(value);
            messages.put("value", value);
            messages.put("pubKey", Parameters.recipient);
            System.out.println("send");
            return gson.toJson(messages);      
        }
        public String updateBalance(){
            Gson gson = new Gson();
            HashMap <String, String> messages = new  HashMap<String, String> ();
            messages.put("message", "updateBalance");
            return gson.toJson(messages);  
        }
}
