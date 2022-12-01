# Welecome to Moneyish

## Description  ```Beta Version```
Moneyish is a simple blockchain cryptocurrency with a bitcoin core. These are focused around 4 key components:
- Wallet [Wallet.java]
- Miner 
- Peer to Peer Network 
- BlockChain [Parameters.java]

The [CDCLientUI.java] serves as the main GUI for Moneyish's wallet. 

The [LoginPage.java] serves as the start Login Page for Moneyish

## How to Install
Download and Install [NetBeans IDE](https://netbeans.apache.org/)

Download or Clone this github repository's code

Open NetBeans and Open "Working Code" directory as a project

## SetUp
1. On Computer 1, run the project in NetBeans on Computer 1. Create an Account if you do not have one already, then login. You will be prompted for whether you are computer 1 or 2, please type in "1" or "Computer 1". You have successfully set up Computer 1. 

2. On Computer 2, run the project in NetBeans on Computer 2. Create an Account if you do not have one already, then login. You will be prompted for whether you are computer 1 or 2, please type in "2" or "Computer 2". You have sucessfully set up Computer 2. 

3. Click Overview at the top of the Page to get an Overview of your Wallet. The balance, number of transactions and pending amount of coins being verified are displayed on this page.


4. Click Send at the top of the Page to send Coins. Fill in the public key of the opposite machine for "Send To", Enter a Label for your Transaction and enter how much you would like to send. Finally press send to send all of your Coins to the other other computer.


## Debugging
Common Errors that were experienced in testing. Be mindful this project is still in early development.

```Build Failed on Intial Use ``` Try adding looking to see if there are errors in your java files especially CDClient.java and LoginPage.java. Look to see if the imports are working properly. If the imports are errored add the dependency for that specific import. 

```Connection: timeout ``` Make sure your IP address is correctly enterred for Step 2 with no spaces in between. If this persists try switching Computer 1 for Computer 2 and Computer 2 for Computer 1. 

#### If you come across other errors plesae create a seperate pull request for future explorers
