package block;

import java.util.Date;
import java.time.*;
import java.time.temporal.*;
public class Block {
 
        // Every block contains
        // a hash, previous hash and
        // data of the transaction made
        public String hash;
        public String previousHash;
        private String amount;
        private String sender;
        private String receiver;
        //private long timeStamp;
        String timeStamp;
        // Constructor for the block
        public Block(String amount,
                                String previousHash, String sender, String receiver)
        {
                this.amount = amount;
                this.sender=sender;
                this.receiver=receiver;
                this.previousHash
                        = previousHash;
                //this.timeStamp
                        //= new Date().getTime();
                        LocalDateTime lt
            = LocalDateTime.now();
                        this.timeStamp=lt.toString();
                this.hash
                        = calculateHash();
        }
 
        @Override
        public String toString() {
                return "Block [Hash=" + hash + ", Previous Hash=" + previousHash + ", Sender=" + sender + ", Receiver=" + receiver + ", Amount=" + amount + ", Time Stamp="
                                + timeStamp + "]";
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
                                + timeStamp
                                + amount);
 
                return calculatedhash;
        }
}