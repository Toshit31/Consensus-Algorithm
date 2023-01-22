package blockchain;

import java.util.Date;
import java.time.*;
import java.time.temporal.*;
public class Block1 {
 
        // Every block contains
        // a hash, previous hash and
        // data of the transaction made
        public String hash;
        public String previousHash;
        private String amount1,amount2,amount3;
        private String sender1,sender2,sender3;
        private String receiver;
        private int winner;
        //private long timeStamp;
        String timeStamp1,timeStamp2,timeStamp3;
        // Constructor for the block
        public Block1(String[][] transaction,int winner,
                                String previousHash)
        {
                this.amount1 = transaction[0][0];
                this.amount2 = transaction[1][0];
                this.amount3 = transaction[2][0];
                this.sender1=transaction[0][1];
                this.sender2=transaction[1][1];
                this.sender3=transaction[2][1];
                this.timeStamp1=transaction[0][2];
                this.timeStamp2=transaction[1][2];
                this.timeStamp3=transaction[2][2];
                this.previousHash
                        = previousHash;
                        this.winner=winner;
                
                this.hash
                        = calculateHash();
        }
 
        @Override
        public String toString() {
        	//function to display blockchain logs
                return "[Delegate ID "+winner+" Hash=" + hash + ", Previous Hash=" + previousHash + "\n Transaction 1 Sender=" + sender1 + ", Receiver=" + "Coffee shop" + ", Amount=" + amount1 + ", Time Stamp="
                                + timeStamp1 +"\n Transaction 2 Sender=" + sender2 + ", Receiver=" + "Coffee shop" + ", Amount=" + amount2 + ", Time Stamp="
                                        + timeStamp2+"\n Transaction 3 Sender=" + sender3 + ", Receiver=" + "Coffee shop" + ", Amount=" + amount3 + ", Time Stamp="
                                                + timeStamp3+ "]";
        }
 
        // Function to calculate the hash
        public String calculateHash()
        {
                // Calling the "crypt" class
                // to calculate the hash
                // by using the previous hash,
                // timestamp and the data
                String calculatedhash
                        = crypt.sha256(
                                previousHash
                                + timeStamp1+ timeStamp2+ timeStamp3
                                + amount1+ amount2+ amount2);
 
                return calculatedhash;
        }
}