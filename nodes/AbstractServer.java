package nodes;

import cz.zcu.fav.kiv.jsim.JSimException;
import cz.zcu.fav.kiv.jsim.JSimHead;
import cz.zcu.fav.kiv.jsim.JSimInvalidParametersException;
import cz.zcu.fav.kiv.jsim.JSimLink;
import cz.zcu.fav.kiv.jsim.JSimProcess;
import cz.zcu.fav.kiv.jsim.JSimSecurityException;
import cz.zcu.fav.kiv.jsim.JSimSimulation;
import cz.zcu.fav.kiv.jsim.JSimSimulationAlreadyTerminatedException;
import cz.zcu.fav.kiv.jsim.JSimTooManyHeadsException;
import cz.zcu.fav.kiv.jsim.JSimTooManyProcessesException;
import simulation.Stats;






/**
 * The abstract implementation of the server. Server is consisted from a queue and a node of the service.
 * Descendants have to implement the method called {@link #workDuration()}, which returns a random  time of
 * the service transaction (depending on the distribution).
 *
 * @author Miroslav Vozabal
 * 
 */
public abstract class AbstractServer extends JSimProcess implements IReceiver {
    
	/** The node which the server hands the transaction over to. */
    protected IReceiver nextReceiver;
    
    /** The transaction queue */
    protected JSimHead queue;
    
    /** The count of the processed transactions. */
    protected int transactionCount = 0;
    
    /** The statistic of the server working time. */
    protected Stats workingTime = new Stats();
    

    
    /**
     * Creates a new server in the simulation with the particular name
     * 
     * @param name - the node name
     * @param parent - the parent simulation
     * @throws JSimSimulationAlreadyTerminatedException
     * @throws JSimInvalidParametersException
     * @throws JSimTooManyProcessesException
     * @throws JSimTooManyHeadsException
     */
    public AbstractServer(String name, JSimSimulation parent)
            throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException, JSimTooManyHeadsException {
        this(name, parent, null);
    }
    
    
    
    /**
     * Creates a new server in the simulation with the particular name
     * 
     * @param name - the node name
     * @param parent - the parent simulation
     * @param nextReceiver - the next node
     * @throws JSimSimulationAlreadyTerminatedException
     * @throws JSimInvalidParametersException
     * @throws JSimTooManyProcessesException
     * @throws JSimTooManyHeadsException
     */
    public AbstractServer(String name, JSimSimulation parent, IReceiver nextReceiver)
            throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException, JSimTooManyHeadsException {
        super(name, parent);
        this.nextReceiver = nextReceiver;
        this.queue = new JSimHead(name + " queue", parent);
    }
    
    
    
    /**
     * Returns the transactions mean value in the server (of the whole simulation time).
     * @return the transactions mean value in the server (Lq)
     */
    public double getLq() {
        /* the transaction is pulled in the life() method from the queue after the processing
         * Lw + Ls = Lq */
        return queue.getLw();
    }
    
    
    
    /**
     * Returns the transaction processed mean value (of the whole simulation time).
     * @return the transaction processed mean value in the server (Tq)
     */
    public double getTq() {
    	/* the transaction is pulled in the life() method from the queue after the processing
         *  Tw + Ts = Tq */
        return queue.getTw();
    }
    
    
    
    /**
     * Returns the actual transaction count in the server. It means in the node of the service and in the queue.
     * @return the actual transaction count in the server
     */
    public long getTransactionCount() {
    	/* the transaction is pulled in the life() method from the queue after the processing
         * the queue contains all server transaction. */
        return queue.cardinal();
    }
    
    
    
    /**
     * Finds out if the server is working or waiting on the transaction arrival.
     * @return true if the server is working (the process of processing is active), else false
     */
    public boolean isWorking() {
        return !isIdle();
    }
    
    
    
    /**
     * Returns the server load (of the whole simulation time). The load is computed as the rate between the time of working and the whole simulation time
     * 
     * @return the server load (of the whole simulation time)
     */
    public double getLoad() {
        return workingTime.getSum() / myParent.getCurrentTime();
    }
    
    
    
    /**
     *   
     * Generates a random time of the transaction processing (depending on the distribution).
      @return the random time of the transaction processing
     */
    protected abstract double workDuration();
    
    
    
    /**
     * Pulls transactions from the queue and performs their processing. The time of the processing is determined by
     * the method {@link #workDuration()}. If the queue is empty it falls asleep.
     */
    @Override
    protected void life() {
        JSimLink link;
        double currentTime;
        
        try {
            while (true) {
            	
                if (queue.empty())
                    passivate();
                
                else {
                    link = queue.first();
                    
                    /* the processing of the transaction */
                    currentTime = myParent.getCurrentTime();
                    hold(workDuration());
                    workingTime.addValue(myParent.getCurrentTime() - currentTime);
                    
                    /* the transaction is pulled from the queue after the processing. 
                     * It means that the queue contains all server transactions */
                    link.out();
                    transactionCount++;
                    
                    if (nextReceiver != null)
                        nextReceiver.receive(link);
                    else
                        link = null;
                }
            }
        } catch (JSimException e) {
            e.printStackTrace();
            e.printComment(System.err);
        }
    }
    
    

    @Override
    public void receive(JSimLink link) {
        try {
            link.into(queue);
            if (isIdle()) activateNow();
        } catch (JSimSecurityException e) {
            // The transaction is already in the queue
            e.printStackTrace();
            e.printComment(System.err);
        }
    }

    
    /**
     * Return the next simulation node.
     * @return the next simulation node
     */
    public IReceiver getNextReceiver() {
        return nextReceiver;
    }


    /**
     * Sets up the next simulation node
     * @param nextReceiver - the next simulation node
     */
    public void setNextReceiver(IReceiver nextReceiver) {
        this.nextReceiver = nextReceiver;
    }
    
    
    
    /**
     * Returns the count of already processed transactions.
     * @return the count of already processed transactions
     */
    public int getProcessedTransactionsCount() {
        return transactionCount;
    }
    
    
    
    /**
     * Returns the frequency mean value of the node stream.
     * @return the frequency mean value of the node stream
     */
    public double getMeanThroughputRate() {	
        return transactionCount / myParent.getCurrentTime();
    }
    
    
    
    /**
     * Returns the statistics of the server working time.
     * @return the statistics of the server working time.
     */
    public Stats getWorkingTimeStatistics() {
        return workingTime;
    }

}
