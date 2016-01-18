package nodes;

import cz.zcu.fav.kiv.jsim.JSimLink;


/**
 * An interface for a node of the simulation to be able receive transactions.
 *
 * @author Miroslav Vozabal
 */
public interface IReceiver {
    
    /**
     * Receive a transaction.
     * 
     * @param link - the transaction
     */
    void receive(JSimLink link);
}
