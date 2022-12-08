/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dottorsoft.SimpleBlockChain.networking;
import com.dottorsoft.SimpleBlockChain.core.Wallet;
import com.dottorsoft.SimpleBlockChain.util.Parameters;
import com.google.gson.Gson;
import java.io.*;
import java.net.*;
import java.util.*;
import wallet.CDClientUI;

/**
 *
 * @author jonan
 */
public class SendToClientThread implements Runnable
{
	PrintWriter pwPrintWriter;
	Socket clientSock = null;
        public static String message = null;
	public SendToClientThread(Socket clientSock)
	{
		this.clientSock = clientSock;
	}

	public void run() {
	    
                try{
                    while(true){
                    if (SendToClientThread.message == "sendUpdate"){
                        message = sendUpdate(message);
                    } 
                    pwPrintWriter =new PrintWriter(new OutputStreamWriter(this.clientSock.getOutputStream()));//get outputstream
                    String msgToClientString = null;
                    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));//get userinput
                    if (SendToClientThread.message == "sendUpdate"){
                        message = sendUpdate(message);
                    } else if(SendToClientThread.message =="send"){
                        System.out.println("message = send");
                        message = send();
                    } else if(SendToClientThread.message=="updateBalance"){
                       System.out.println("message = updateBalance");
                       message = updateBalance();
                    }
                    if(message != null){
                        msgToClientString = message;//get message to send to client

                        pwPrintWriter.println(msgToClientString);//send message to client with PrintWriter
                        pwPrintWriter.flush();//flush the PrintWriter
                    }
                        SendToClientThread.message = null;
                    } 		
		}
		catch(Exception ex){System.out.println(ex.getMessage());} 
	}//end run
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
}//end class SendToClientThread

