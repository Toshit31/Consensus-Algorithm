package block;
import java.util.*;
import java.io.*;
public class Blockchain
{
    ArrayList<Block> blockchain=new ArrayList<Block>();
   
    
    public static void main(String args[])throws Exception
    {
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        Blockchain ob=new Blockchain();
        ArrayList<String> user=new ArrayList<String>();
        ArrayList<Integer> balance=new ArrayList<Integer>();
        while(true)
        {
        	//menu driven log recording
            System.out.println("Press 1 to add a user details");
            System.out.println("Press 2 to add a transaction");
            System.out.println("Press 3 to display existing logs");
            System.out.println("Press 0 to exit");
            int choice=Integer.parseInt(br.readLine());
            if(choice==0)
            {
                break;
            }
            else if(choice==1)
            {
            	//to add users to the pool
                System.out.println("Enter username");
                String username=br.readLine();
                user.add(username);
                System.out.println("Enter balance");
                int balance1=Integer.parseInt(br.readLine());
                balance.add(balance1);
                
            }
            else if(choice==2)
            {
            	//to collect transaction details and verify whether transaction is possible or not
            	int i;
            	if(user.size()<2)
            	{
            		System.out.println("Need atleat 2 users for transaction");
            		continue;
            	}
            	System.out.println("Available users");
            	for(i=0;i<user.size();i++)
            		System.out.println("Press "+(i+1)+" for "+user.get(i));
            	System.out.println("Enter number corresponding to sender");
            	int choice1=Integer.parseInt(br.readLine());
            	System.out.println("Enter number corresponding to receiver");
            	int choice2=Integer.parseInt(br.readLine());
            	System.out.println("Enter Amount");
                String amt1=br.readLine();
                int balancesender=balance.get((choice1-1));
                int balancereceiver=balance.get((choice2-1));
                String sender1=user.get((choice1-1));
                String receiver1=user.get((choice2-1));
                int amt2=Integer.parseInt(amt1);
                if(amt2>balancesender)
                {
                	System.out.println("Invalid transcation, Verification failed");
                	continue;
                }
                if(choice1==choice2)
                {
                	System.out.println("Invalid transcation, Verification failed");
                	continue;
                }
                else
                {
                	//if valid transaction, balances are evaluated and transaction is added to the blockchain
                	balance.set((choice1-1),(balancesender-amt2));
                	balance.set((choice2-1),(balancereceiver+amt2));
                	//System.out.println(""+balance.get((choice1-1)));
                	//System.out.println(""+balance.get((choice2-1)));
                	ob.createBlock(sender1,receiver1,amt1);
                	System.out.println("Valid transcation, Verification successful");
                }
                	
            }
            else
            {
                ob.show();
            }
        }
    }
    public void createBlock(String sender,String receiver,String amt)throws Exception
    {
    	//function to add transaction to blockchain
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw=new PrintWriter(System.out);
       
        
        Block block;
        if(blockchain.size()==0)
        {
            block=new Block(amt,"0",sender,receiver);
            blockchain.add(block);
            return;
        }
        String previousHash=blockchain.get(blockchain.size()-1).hash;
        block=new Block(amt,previousHash,sender,receiver);
        blockchain.add(block);
       
    }
    public void show()
    {
    	//function to print transactions or logs in the blockchain
        for(int i=0;i<blockchain.size();i++)
        {

            System.out.println(blockchain.get(i).toString());
        }
    }
}

