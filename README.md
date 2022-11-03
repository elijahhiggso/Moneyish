# Moneyish

## Description  ```Beta Version```
Moneyish is a simple blockchain cryptocurrency with a bitcoin core. These are focused around 4 key 4 components:
- Wallet 
- Miner
- Peer to Peer Network
- BlockChain

The [CDCLientUI.java] serves as the main GUI for Moneyish's wallet. 

The [LoginPage.java] serves as the start Login Page for Moneyish

The [Main.java] simulates two people with wallets to send coins to one another. This also runs a local P2P.

## How to Install
Download and Install [NetBeans IDE](https://netbeans.apache.org/)

Download this Github Project and Load it onto your NetBeans 

## SetUp
1. Before running, on Computer 1 , open CDClientUI.java and change the server and client IP's to the other computer's IP. Do the same for Computer 2. (Look at Lines
570, 595, 758, 763)

2. On your Computer 1, remove the multi line comment on line 662. This is important to have the genesis block give your wallet some coins.

3. On the second computer keep everything as is.


4. Run LoginPage.java and you can go and create an account then you will be able to login.


~~5. A QR Code will be created and found on your desktop. You can scan this code with your mobile device and your public and private key will be generated.~~

6. With Computer 1, once you login you are greeted to the main page, the Wallet.


8. On the overview tab you can view your public key and it will show in the output/terminal.


10. Make sure you get Computer 2 public key and use that for Computer 1 to be able to send a transaction.


12. On Computer 1, go to the send tab and use Computer 2 public key to send a transaction.

## Debugging
Common Errors that were experienced in testing. Be mindful this project is still in early development.

```Buid Failed on Intial Use ``` Try adding looking to see if there are errors in your java files especially CDClient.java and LoginPage.java. Look to see if the imports are working properly. If the imports are errored add the dependency for that specific import. 

```Socket Exception``` Currently looking for a solution

#### If you come across other errors plesae create a seperate pull request for future explorers
