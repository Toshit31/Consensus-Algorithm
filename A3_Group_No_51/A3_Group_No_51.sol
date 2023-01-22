pragma solidity ^0.8.4;

contract DEX_REX {
    
    address public DEXTER; // shows contract creator address
    mapping (address => uint) public ret_balances; // shows the RET Balance
    mapping (address => uint) public ETHER; // shows the ETHER Balance
    uint cost = 5; // Cost of RET 

    
    event Sent(address from, address to, uint RET);

    // Constructor code is only run when the contract
    // is created
    constructor() {
        DEXTER = msg.sender;
    }
    
    
     // Errors allow you to provide information about
    // why an operation failed. They are returned
    // to the caller of the function.
    error InsufficientBalance(uint requested, uint available); 
    

    // Function to initialise ETHER Balance for a customer
    function New_customer(address receiver, uint Ether) public {
        ETHER[receiver] += Ether;
    }

    // Sends an amount of newly created RET to an address
    // Can only be called by the contract creator (DEXTER)
    function BUY_RET(address receiver, uint RET) public {
        require(msg.sender == DEXTER);
        if(ETHER[receiver]<(RET*cost)){
          revert InsufficientBalance({
                requested: RET,
                available: ETHER[receiver]/cost  
        });
        }
          ret_balances[receiver] += RET;
          ETHER[receiver]-=(RET*cost);
        
    }
    

    // Sends an amount of existing RET in exchange for ETHER (Trade)
    // from any caller to an address
    function SEND(address receiver, uint amount) public {
        if (amount > ret_balances[msg.sender] || ETHER[receiver]< (amount*cost))
            revert InsufficientBalance({
                requested: amount,
                available: ret_balances[msg.sender]
            });

        ret_balances[msg.sender] -= amount;
        ret_balances[receiver] += amount;
        ETHER[msg.sender] += (amount*cost);
        ETHER[receiver] -= (amount*cost);
        emit Sent(msg.sender, receiver, amount);
    }
    
    // funtion to use RET to enter Dexter's shop
    // one RET per customer
    function USE_RET(address sender, uint Num_of_people) public {
        if (ret_balances[sender]<Num_of_people)
            revert InsufficientBalance({
                requested: Num_of_people,
                available: ret_balances[sender]
            });

        ret_balances[sender] -= Num_of_people;
        
    }
}