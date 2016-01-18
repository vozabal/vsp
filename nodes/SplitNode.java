package nodes;

import cz.zcu.fav.kiv.jsim.JSimLink;




/**
 * A place of the division - splits the transaction stream to two directions depending on the probability of the each way. 
 *
 * @author Miroslav Vozabal
 */
public class SplitNode implements IReceiver {
    
    /** The node of the 1. direction. */
    private IReceiver firstReceiver;
    
    /** The probability of the 1. direction */
    private double probability;
    
    /** The node of the 2. direction.*/
    private IReceiver secondReceiver;
    
    
    /**
     * Creates a new place of the division.
     * @param firstReceiver - The node of the 1. direction
     * @param probability - The probability of the 1. direction
     * @param secondReceiver - The node of the 2. direction
     */
    public SplitNode(IReceiver firstReceiver, double probability, IReceiver secondReceiver) {
        this.firstReceiver = firstReceiver;
        this.probability = probability;
        this.secondReceiver = secondReceiver;
    }

    
    /**
     * Receive the transaction with a probability {@link #probability} hands it over to the {@link #firstReceiver},
     * with probability (1 - {@link #probability}) to the node {@link #secondReceiver}.
     */
    @Override
    public void receive(JSimLink link) {
        if (Math.random() < probability)
            firstReceiver.receive(link);
        else
            secondReceiver.receive(link);
    }

}
