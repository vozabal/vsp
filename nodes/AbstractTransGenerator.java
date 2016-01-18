package nodes;

import cz.zcu.fav.kiv.jsim.JSimException;
import cz.zcu.fav.kiv.jsim.JSimInvalidParametersException;
import cz.zcu.fav.kiv.jsim.JSimLink;
import cz.zcu.fav.kiv.jsim.JSimProcess;
import cz.zcu.fav.kiv.jsim.JSimSimulation;
import cz.zcu.fav.kiv.jsim.JSimSimulationAlreadyTerminatedException;
import cz.zcu.fav.kiv.jsim.JSimTooManyProcessesException;
import simulation.Transaction;






/**
 * The abstract transactions generator. Generates new transactions arrivals in random intervals.
 * Descendants have to implement the method called {@link #interval()}, which returns interval random times
 * until the next transaction arrival. (depending on the distribution).
 *
 * @author Miroslav Vozabal
 */
public abstract class AbstractTransGenerator extends JSimProcess {
    
    /** The next simulation node. */
    private IReceiver nextReceiver;

    
    /**
     * Creates a generator with a particular name.
     * @param name - the node name
     * @param parent - the parent simulation
     * @throws JSimSimulationAlreadyTerminatedException
     * @throws JSimInvalidParametersException
     * @throws JSimTooManyProcessesException
     */
    public AbstractTransGenerator(String name, JSimSimulation parent)
            throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException {
        this(name, parent, null);
    }
    
    
    
    /**
     * Creates a generator with a particular name.
     * @param name - the node name
     * @param parent - the parent simulation
     * @param nextReceiver - the next simulation node
     * @throws JSimSimulationAlreadyTerminatedException
     * @throws JSimInvalidParametersException
     * @throws JSimTooManyProcessesException
     */
    public AbstractTransGenerator(String name, JSimSimulation parent, IReceiver nextReceiver)
            throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException {
        super(name, parent);
        this.nextReceiver = nextReceiver;
    }
    
    
    
    /**
     * Generates a random interval between 2 transactions arrivals into the system (depending on the distribution).
     * 
     * @return the random interval
     */
    protected abstract double interval();
    
    
    
    /**
     * Generates new transactions in random intervals which arrive into the system.
     */
    
    @Override
    protected void life() {
        JSimLink link;
        
        try {
            while (true) {
                link = new JSimLink(new Transaction(myParent.getCurrentTime()));
                nextReceiver.receive(link);
                hold(interval());
            }
        } catch (JSimException e) {
            e.printStackTrace();
            e.printComment(System.err);
        }
    }

    
    
    /**
     * Returns the next node.
     * @return the next node
     */
    public IReceiver getNextReceiver() {
        return nextReceiver;
    }

    
    
    /**
     * Sets up the next node.
     * @param nextReceiver - the next node
     */
    public void setNextReceiver(IReceiver nextReceiver) {
        this.nextReceiver = nextReceiver;
    }
}
