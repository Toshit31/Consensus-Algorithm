package blockchain;
import java.util.*;
class Delegate
{
	//Delegate class, blueprint of delegate nodes which will add blocks
    //different instances of this class form different delegate nodes
    double Profit;
    int key;
    String name;
    ArrayList<Block1> block_chain=new ArrayList<Block1>();
    public Delegate(double Profit,int key,String name)
    {
    	//Every delegate has profit, key, name and its own blockchain(arraylist)
        this.Profit=Profit;
        this.key=key;
        this.name=name;
        block_chain=new ArrayList<Block1>();
    }
}