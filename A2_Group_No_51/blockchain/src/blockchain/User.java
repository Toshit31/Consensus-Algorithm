package blockchain;
import java.util.*;
class User
{
	//User class, blueprint of user nodes which will elect delegates
	//different instances of this class form different user nodes
    double Balance;
    int key;
    String username;
    ArrayList<Block1> block_chain;
    public User(double Balance,int key,String username)
    {
    	//Every user has balance, key, username and its own blockchain(arraylist)
        this.Balance=Balance;
        this.key=key;
        this.username=username;
        block_chain=new ArrayList<Block1>();
    }
}