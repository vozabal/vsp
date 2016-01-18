package nodes;

import cz.zcu.fav.kiv.jsim.JSimLink;
import cz.zcu.fav.kiv.jsim.JSimSimulation;
import simulation.Transaction;





/**
 * The output simulation node. Collects these statistics: the system response time mean value, the count of processed transactions and 
 * the frequency mean value of the transactions stream.
 * 
 * 
 * @author Miroslav Vozabal
 */
public class EndNode implements IReceiver {
    
	/** The parent simulation. */    
    private JSimSimulation myParent;
    
    /** The system response time mean value*/
    private double meanResponseTime = 0.0;
    
    /** The count of processed transactions, */
    private int transactionCount = 0;
    
    
    /**
     * Creates a new endNode
     * @param parent the simulation parent
     */
    public EndNode(JSimSimulation parent) {
        this.myParent = parent;
    }

    
    /**
     * Accepts the transaction and updates the tracked statistics.
     */
    @Override
    public void receive(JSimLink link) {
        Transaction transaction = (Transaction) link.getData();
        double responseTime = myParent.getCurrentTime() - transaction.getStartTime();
        meanResponseTime = (meanResponseTime * transactionCount + responseTime) / (transactionCount + 1);
        transactionCount++;        
    }
    
    
    /**
     * Returns the count of the processed transactions.
     * @return the count of the processed transactions
     */
    public int getProcessedTransactionsCount() {
        return transactionCount;
    }

    
    /**
     * Returns the system response time mean value.
     * @return the system response time mean value
     */
    public double getMeanResponseTime() {
        return meanResponseTime;
    }
    
    
    /**
     * Returns the mean value of the output stream.
     * @return the mean value of the output stream
     */
    public double getMeanOutputRate() {
        return transactionCount / myParent.getCurrentTime();
    }

}
