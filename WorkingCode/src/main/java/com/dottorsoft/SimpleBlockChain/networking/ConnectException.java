/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dottorsoft.SimpleBlockChain.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
/**
 *
 * @author Team2
 */
public class ConnectException {
public static void main(String[] args) {

		//new Thread(new SimpleServer()).start();
		new Thread(new SimpleClient()).start();

	}

	static class SimpleServer implements Runnable {

		
		public void run() {

			ServerSocket serverSocket = null;
			while (true) {
				
				try {
					serverSocket = new ServerSocket(8888);

					Socket clientSocket = serverSocket.accept();

					BufferedReader inputReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					
					System.out.println("Client said :"+inputReader.readLine());

				} catch (IOException e) {
					e.printStackTrace();
				}finally{
					try {
						serverSocket.close();
					
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		}

	}

	static class SimpleClient implements Runnable {

		
		public void run() {

			Socket socket = null;
			try {

				Thread.sleep(3000);
				
				socket = new Socket("195.134.65.75", 8888);
				
			    PrintWriter outWriter = new PrintWriter(socket.getOutputStream(),true);
			    
			    outWriter.println("Hello Mr. Server!");
			   

			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				
				try {
					socket.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		}

	}
}
    

