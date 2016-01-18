package simulation;

import cz.zcu.fav.kiv.jsim.JSimException;
import cz.zcu.fav.kiv.jsim.JSimSimulation;
import nodes.AbstractServer;
import nodes.AbstractTransGenerator;
import nodes.EndNode;
import nodes.ExponentialServer;
import nodes.ExponentialTransactionGenerator;
import nodes.GaussianServer;
import nodes.GaussianTransactionGenerator;
import nodes.Probe;
import nodes.SplitNode;



/**
 * The queue network simulation.
 *
 * @author Miroslav Vozabal
 */
public class QueueSimulation extends JSimSimulation {

    /** The first transactions generator. */
    private AbstractTransGenerator generator1;
    
    /** The second transactions generator. */
    private AbstractTransGenerator generator2;
    
    /** Server 1. */
    private AbstractServer server1;
    
    /** Server 2. */
    private AbstractServer server2;
    
    /** Server 3. */
    private AbstractServer server3;
    
    /** Server 4. */
    private AbstractServer server4;
    
    /** Probe for tracking the stream of transactions. */
    private Probe probe;
    
    /** The last node used for tracking some statistics. */
    private EndNode endNode;
    
    /** The tracker is used for tracking some statistics. */
    private Tracker tracker;
    
    
    /**
     *  Random number distribution X.
     */
    public enum Distribution {
        /** Exponential distribution. */
        EXPONENTIAL,
        /** Normal distribution. */
        GAUSSIAN;
    }
    
    
    
    /**
     * Simulation initialization with particular parameters.
     * @param parameters - simulated queue network parameters
     * @throws JSimException
     */
    public QueueSimulation(SimulationParameters parameters) throws JSimException {
        super("Queueing Network Simulation");
        messageNoNL("Simulation initialization... ");
        initialize(parameters);
        message("complete"); 
    }
    
    
    /**
     * Starts the simulation and returns measured statistics.
     * @param transactionCount the count of transactions which go through the system.
     * @return measured statistics
     */
    public SimulationStats run(int transactionCount) {
        try {
            /* The simulation process */
            messageNoNL("Starting the simulation, please wait... ");
            generator1.activateNow();
            generator2.activateNow();
            tracker.activateNow();
            while ((endNode.getProcessedTransactionsCount() < transactionCount) && (step() == true))
                ;
            message("complete");
            
            /* Tracked statistics */
            SimulationStats stats = new SimulationStats();
            stats.setLq(tracker.getMeanTransactionCount());
            stats.setLqi(server1.getLq(), server2.getLq(), server3.getLq(), server4.getLq());
            stats.setTq(endNode.getMeanResponseTime());
            stats.setTqi(server1.getTq(), server2.getTq(), server3.getTq(), server4.getTq());
            stats.setThroughputRates(server1.getMeanThroughputRate(), server2.getMeanThroughputRate(),
                                     server3.getMeanThroughputRate(), server4.getMeanThroughputRate());
            stats.setLoads(server1.getLoad(), server2.getLoad(), server3.getLoad(), server4.getLoad());
            stats.setProbeStats(probe.getOperationTimeStatistics());
            return stats;
        } catch (JSimException e) {
            e.printStackTrace();
            e.printComment(System.err);
            return null;
        } finally {
            shutdown();
        }
    }
    
    
    
    /**
     * Initializates the network queues.
     * @param par - the network queues parameters
     * @throws JSimException
     */
    private void initialize(SimulationParameters par) throws JSimException {
        /* Creation of needed objects */
        if (par.getDistribution() == Distribution.EXPONENTIAL) {
            generator1 = new ExponentialTransactionGenerator("Generator 1", this, par.getLambda1());
            generator2 = new ExponentialTransactionGenerator("Generator 2", this, par.getLambda2());
            server1 = new ExponentialServer("Server 1", this, 1 / par.getTs1());
            server2 = new ExponentialServer("Server 2", this, 1 / par.getTs2());
            server3 = new ExponentialServer("Server 3", this, 1 / par.getTs3());
            server4 = new ExponentialServer("Server 4", this, 1 / par.getTs4());
        } else {
            generator1 = new GaussianTransactionGenerator("Generator 1", this, 1 / par.getLambda1(), par.getVarianceCoefficient() * (1 / par.getLambda1()));
            generator2 = new GaussianTransactionGenerator("Generator 2", this, 1 / par.getLambda2(), par.getVarianceCoefficient() * (1 / par.getLambda2()));
            server1 = new GaussianServer("Server 1", this, par.getTs1(), par.getVarianceCoefficient() * par.getTs1());
            server2 = new GaussianServer("Server 2", this, par.getTs2(), par.getVarianceCoefficient() * par.getTs2());
            server3 = new GaussianServer("Server 3", this, par.getTs3(), par.getVarianceCoefficient() * par.getTs3());
            server4 = new GaussianServer("Server 4", this, par.getTs4(), par.getVarianceCoefficient() * par.getTs4());
        }
        probe = new Probe(server4, this);
        SplitNode splitter1 = new SplitNode(server3, par.getPrb2(), server2);
        SplitNode splitter2 = new SplitNode(probe, par.getPrb3(), server1);
        endNode = new EndNode(this);
        tracker = new Tracker("Tracker", this, server1, server2, server3, server4);
        
        /* Linking objects in the network */
        generator1.setNextReceiver(server1);
        generator2.setNextReceiver(server2);
        server1.setNextReceiver(server2);
        server2.setNextReceiver(splitter1);
        server3.setNextReceiver(splitter2);
        server4.setNextReceiver(endNode);
    }


}
