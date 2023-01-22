package blockchain;

import java.util.*;
import java.io.*;
import java.util.Date;
import java.time.*;
import java.time.temporal.*;
public class Blockchain1
{
    double profit=50.0;
    double fee=20.0;
    ArrayList<Block1> blockchain=new ArrayList<Block1>();
   
    User userA,userB,userC;
    Delegate delegate1,delegate2,delegate3;
    public Blockchain1()
    {
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        //three objects representing three user nodes are instantiated with initial balance
        userA=new User(1000.0,1,"userA");
        userB=new User(3000.0,2,"userB");
        userC=new User(2000.0,3,"userC");
      //three objects representing three delegate nodes are instantiated with 0 initial balance
        delegate1=new Delegate(0.0,1,"delegate1");
        delegate2=new Delegate(0.0,2,"delegate2");
        delegate3=new Delegate(0.0,3,"delegate3");
        
    } 
    public static void main(String args[])throws Exception
    {
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        Blockchain1 ob=new Blockchain1();
        ArrayList<String> user=new ArrayList<String>();
        ArrayList<Integer> balance=new ArrayList<Integer>();
        int x=0;
        //since 3 transactions form an block for this problem an 2d array to store transaction details is created
        String transaction[][]=new String[3][4];
        while(true)
        {
            
            //menu driven log recording
            //System.out.println("Press 1 to add a user details");
            System.out.println("Press 1 to add a transaction");
            System.out.println("Press 2 to display existing logs, balances and fees collected");
            System.out.println("Press 0 to exit");
            int choice=Integer.parseInt(br.readLine());
            if(choice==0)
            {
                break;
            }
            
            else if(choice==1)
            {
                //to collect transaction details 
                
                System.out.println("Enter sender");
                String sender =br.readLine();
                 System.out.println("Enter Amount");
                String amt1=br.readLine();
                System.out.println("Enter available Balance");
               String bal=br.readLine();
                LocalDateTime lt
                = LocalDateTime.now();
                String timeStamp=lt.toString();
                transaction[x][0]=sender;
                transaction[x][1]=amt1;
                transaction[x][2]=timeStamp;
                transaction[x][3]=bal;
                x++;
                if(x==3) 
                {
                    x=0;
                    //once all three transactions are collected delegate node is elected with election
                    //function which will add the block
                    ob.Election(transaction);
                    transaction=new String[3][4];
                }
                    
            }
            else
            {
            	//show function to display the blockchain and the attributes of the different nodes
                ob.show();
            }
        }
    }
    
    public boolean createBlock(String[][] transaction,int winner)throws Exception
    {
        //function to add transaction to blockchain
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw=new PrintWriter(System.out);
        
       if(!verify(transaction,winner))
        {
            return false;
        }
        
        //After the delegate node is elected the node verifies the transactions and then the block is added to the blockchain of the respective delegate node
        //if delegate1 node is elected
       if(winner==1)
        {
        	Block1 block1;
            if(delegate1.block_chain.size()==0)
            {
                block1=new Block1(transaction,winner,"0");
                delegate1.block_chain.add(block1);
                blockchain=new ArrayList<Block1>(delegate1.block_chain);
                update();
                return true;
                
            }
            String previousHash=delegate1.block_chain.get(blockchain.size()-1).hash;
            block1=new Block1(transaction,winner,previousHash);
            delegate1.block_chain.add(block1);
            blockchain=new ArrayList<Block1>(delegate1.block_chain);
            update();
            
        }	
     //if delegate2 node is elected
        if(winner==2)
        {
        	Block1 block1;
            if(delegate2.block_chain.size()==0)
            {
                block1=new Block1(transaction,winner,"0");
                delegate1.block_chain.add(block1);
                blockchain=new ArrayList<Block1>(delegate2.block_chain);
                update();
                return true;
            }
            String previousHash=delegate2.block_chain.get(blockchain.size()-1).hash;
            block1=new Block1(transaction,winner,previousHash);
            delegate2.block_chain.add(block1);
            blockchain=new ArrayList<Block1>(delegate2.block_chain);
            update();
           
        }	
      //if delegate3 node is elected
        if(winner==3)
        {
        	
        	Block1 block1;
            if(delegate3.block_chain.size()==0)
            {
                block1=new Block1(transaction,winner,"0");
                delegate3.block_chain.add(block1);
                blockchain=new ArrayList<Block1>(delegate3.block_chain);
                update();
                return true;
            }
         
            String previousHash=delegate3.block_chain.get(blockchain.size()-1).hash;
            block1=new Block1(transaction,winner,previousHash);
            delegate3.block_chain.add(block1);
        
            blockchain=new ArrayList<Block1>(delegate3.block_chain);
            update();
           
        }	
       
        return true;
    }
    public void update()
    {
    	//After block has been added to the blockchain of the elected delegate node, all other nodes validate this 
    	//blockchain and every nodes blockchain is updated and synchronized to the new blockchain
    	if(isChainValid(blockchain))
    	{
        //if blockchain is valid, then all nodes are synchronized and hence consensus is achieved
    	delegate1.block_chain=new ArrayList<Block1>(blockchain);
    	delegate2.block_chain=new ArrayList<Block1>(blockchain);
    	delegate3.block_chain=new ArrayList<Block1>(blockchain);
    	userA.block_chain=new ArrayList<Block1>(blockchain);
    	userB.block_chain=new ArrayList<Block1>(blockchain);
    	userC.block_chain=new ArrayList<Block1>(blockchain);
    	}
    	}
    public static Boolean isChainValid(ArrayList<Block1> a)
    {
    	//function to check if blockchain is valid
        Block1 currentBlock;
        Block1 previousBlock;
      
        // Iterating through
        // all the blocks
        for (int i = 1;
             i < a.size();
             i++) {
      
            // Storing the current block
            // and the previous block
            currentBlock = a.get(i);
            previousBlock = a.get(i - 1);
      
            // Checking if the current hash
            // is equal to the
            // calculated hash or not
            if (!currentBlock.hash
                     .equals(
                         currentBlock
                             .calculateHash())) {
                System.out.println(
                    "Hashes are not equal");
                return false;
            }
      
            // Checking of the previous hash
            // is equal to the calculated
            // previous hash or not
            if (!previousBlock
                     .hash
                     .equals(
                         currentBlock
                             .previousHash)) {
                System.out.println(
                    "Previous Hashes are not equal");
                return false;
            }
        }
      
        // If all the hashes are equal
        // to the calculated hashes,
        // then the blockchain is valid
        return true;
    }
    public void Election(String[][] transaction)throws Exception
    {
    	//function to choose delegate taking into consideration the individual and total stakes 
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        System.out.println("delegate_1 - 1");
        System.out.println("delegate_2 - 2");
        System.out.println("delegate_3 - 3");
        
        System.out.println("Vote of user A");
        int vA=Integer.parseInt(br.readLine());
        System.out.println("Stake of user A on delegate:"+vA);
        int stakeA=Integer.parseInt(br.readLine());
        
        System.out.println("Vote of user B");
        int vB=Integer.parseInt(br.readLine());
        System.out.println("Stake of user B on delegate:"+vB);
        int stakeB=Integer.parseInt(br.readLine());
        
        System.out.println("Vote of user C");
        int vC=Integer.parseInt(br.readLine());
        System.out.println("Stake of user C on delegate:"+vC);
        int stakeC=Integer.parseInt(br.readLine());
        
        
        int stake1=0;
        int stake2=0;
        int stake3=0;
        if(vA==1)
        {
            stake1+=stakeA;
            
        }
        else if(vA==2)
        {
            stake2+=stakeA;
            
        }
        else
        {
            stake3+=stakeA;
           
        }
        
        if(vB==1)
        {
            stake1+=stakeB;
        }
        else if(vB==2)
        {
            stake2+=stakeB;
        }
        else
        {
            stake3+=stakeB;
        }
        
        if(vC==1)
        {
            stake1+=stakeC;
        }
        else if(vC==2)
        {
            stake2+=stakeC;
        }
        else
        {
            stake3+=stakeC;
        }
        
        int max=Math.max(stake1,Math.max(stake2,stake3));
        //winner is the delegate node selected
        int winner;
     // to calculate the fee acquired by the delegate node for adding a block
        if(max==stake1)
        {
            winner=1;
            
        }
        else if(max==stake2)
        {
            winner=2;
            
        }
        else
        {
        	
            winner=3;
            
        }
        //after election, the selected delegate node creates and adds a block with the below function
        boolean isValid=createBlock(transaction,winner);
        if(isValid)
        {
        	// to calculate the fee acquired by the delegate node for adding a block
            if(max==stake1)
            {
                
                delegate1.Profit+=fee;
            }
            else if(max==stake2)
            {
               
                delegate2.Profit+=fee;
            }
            else
            {
            	
                
                delegate3.Profit+=fee;
            }
        	//to calculate new balances of user nodes
            double tot_stake=0.0;
            if(vA==winner)
            {
                tot_stake+=stakeA;
            }
            if(vB==winner)
            {
                tot_stake+=stakeB;
            }
            if(vC==winner)
            {
                tot_stake+=stakeC;
            }
            
            if(vA==winner)
            {
                userA.Balance+=(stakeA/tot_stake)*profit;
            }
            if(vB==winner)
            {
                userB.Balance+=(stakeB/tot_stake)*profit;
            }
            if(vC==winner)
            {
                userC.Balance+=(stakeC/tot_stake)*profit;
            }

            
            
        }
        
    }
    public boolean verify(String[][] transaction,int winner)
    {
    	//function to verify the transactions
    	int i;
    	for(i=0;i<3;i++)
    	{
    	double bal=Double.parseDouble(transaction[i][3]);
    	double amt=Double.parseDouble(transaction[i][1]);
    	double diff=bal-amt;
    	if(diff<0)
    	{
    		System.out.println("transaction failed");
    		return false;
    	}
    	}
        return true;
    }
    public void show()
    {
        //function to print transactions or logs in the blockchain
        
        for(int i=0;i<userA.block_chain.size();i++)
        {
            System.out.println("Block "+i);

            System.out.println(userA.block_chain.get(i).toString());
        }
        //the new attributes of all the nodes
        System.out.println("Balance of userA="+userA.Balance);
        System.out.println("Balance of userB="+userB.Balance);
        System.out.println("Balance of userC="+userC.Balance);
        
        System.out.println("Profit of Delegate1="+delegate1.Profit);
        System.out.println("Profit of Delegate2="+delegate2.Profit);
        System.out.println("Profit of Delegate3="+delegate3.Profit);
        
        
        
    }
}

