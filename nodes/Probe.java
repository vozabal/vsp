package nodes;

import cz.zcu.fav.kiv.jsim.JSimLink;
import cz.zcu.fav.kiv.jsim.JSimSimulation;
import simulation.Stats;
import simulation.Transaction;




/**
 * A passive tracker object is used for tracking stream statistics at the node position.
 * Counts the interval between transactions. 
 * 
 * @author Miroslav Vozabal
 *
 */
public class Probe implements IReceiver {
	
	/** The statistics of the stream at the node position. */
	protected Stats streamStats = new Stats();

	/** The node of the 1. direction. */
    private IReceiver firstReceiver;      
      
    /** The parent simulation. */
    private JSimSimulation myParent;    
    
    /** The time of the last transaction arrival. */
    private double lastLinkTime;      
 
    /** True - the first transaction handed over. */
    private Boolean first = true;
    
    public Probe(IReceiver receiver, JSimSimulation parent) {
	    this.firstReceiver = receiver;
	    this.myParent = parent;
	}
		
	
	 /**
     * Receive the transaction with the probability {@link #probability} hands it over to the {@link #firstReceiver},
     * 
     */
	@Override
	public void receive(JSimLink link) {		
		if (first) {	// the first transaction
			Transaction transaction = (Transaction) link.getData();
	        double startTime = transaction.getStartTime();	        
			streamStats.addValue(myParent.getCurrentTime() - startTime); // Gets the time of the first transaction from the start to the arrival.
			lastLinkTime = myParent.getCurrentTime(); 
			firstReceiver.receive(link);
			first = false;
		}
		else {	// the other transactions				
			streamStats.addValue(myParent.getCurrentTime() - lastLinkTime); // Gets the transaction time between the last one and the actual one(the gap).
			firstReceiver.receive(link);			
			lastLinkTime = myParent.getCurrentTime();
		}
	}
	
	
	
	 /**
     * Returns the statistics of the stream at the node position
     * @return the statistics of the stream
     */
    public Stats getOperationTimeStatistics() {
        return streamStats;
    }

}
