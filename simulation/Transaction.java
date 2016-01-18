package simulation;





/**
 * The object stores information bound up with a transaction.
 *
 * @author Miroslav Vozabal
 */
public class Transaction {
    
    /** The time of the transaction creation (arrival into the system). */
    private double startTime;

    
    
    
    /**
     * Creates a new transaction.
     * @param creationTime - the time of the creation (arrival into the system).
     */
    public Transaction(double startTime) {
        this.startTime = startTime;
    }
    
    
    
    
    /**
     * Returns the time of the transaction arrival into the system.
     * @return the time of the transaction arrival
     */
    public double getStartTime() {
        return startTime;
    }
    
}
