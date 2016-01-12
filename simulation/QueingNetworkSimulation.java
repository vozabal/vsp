package simulation;

import cz.zcu.fav.kiv.jsim.JSimException;
import cz.zcu.fav.kiv.jsim.JSimSimulation;
import simulation.nodes.AbstractServer;
import simulation.nodes.AbstractTransactionGenerator;
import simulation.nodes.ExponentialServer;
import simulation.nodes.ExponentialTransactionGenerator;
import simulation.nodes.GaussianServer;
import simulation.nodes.GaussianTransactionGenerator;
import simulation.nodes.OutputNode;
import simulation.nodes.Splitter;


/**
 * Simulace site front.
 *
 * @author Miroslav Vozabal
 */
public class QueingNetworkSimulation extends JSimSimulation {

    /** Generator simulujici vstupni tok pozadavku 1. */
    private AbstractTransactionGenerator generator1;
    
    /** Generator simulujici vstupni tok pozadavku 2. */
    private AbstractTransactionGenerator generator2;
    
    /** Server 1. */
    private AbstractServer server1;
    
    /** Server 2. */
    private AbstractServer server2;
    
    /** Server 3. */
    private AbstractServer server3;
    
    /** Server 4. */
    private AbstractServer server4;
    
    /** Vystupni uzel sluzici k zachycovani nekterych statistik. */
    private OutputNode outputNode;
    
    /** Objekt spion sluzici k zachycovani nekterych statistik. */
    private Spy spy;
    
    
    /**
     * Rozdeleni nahodne veliciny X.
     */
    public enum Distribution {
        /** Exponencialni rozdeleni. */
        EXPONENTIAL,
        /** Normalni (Gausovske) rozdeleni. */
        GAUSSIAN;
    }
    
    
    
    /**
     * Inicializuje simulaci s danymi parametry.
     * @param parameters - parametry simulovane site front
     * @throws JSimException
     */
    public QueingNetworkSimulation(SimulationParameters parameters) throws JSimException {
        super("Queueing Network Simulation");
        messageNoNL("Inicializuji simulaci... ");
        initialize(parameters);
        message("hotovo"); 
    }
    
    
    /**
     * Spusti simulaci a vrati namerene statistiky.
     * @param transactionCount - pocet pozadavku, ktere maji projit simulaci
     * @return namerene statistiky
     */
    public SimulationStatistics run(int transactionCount) {
        try {
            /* prubeh simulace */
            messageNoNL("Spoustim simulaci, prosim cekejte... ");
            generator1.activateNow();
            generator2.activateNow();
            spy.activateNow();
            while ((outputNode.getProcessedTransactionsCount() < transactionCount) && (step() == true))
                ;
            message("hotovo");
            
            /* sledovane statistiky */
            SimulationStatistics stats = new SimulationStatistics();
            stats.setLq(spy.getMeanTransactionCount());
            stats.setLqi(server1.getLq(), server2.getLq(), server3.getLq(), server4.getLq());
            stats.setTq(outputNode.getMeanResponseTime());
            stats.setTqi(server1.getTq(), server2.getTq(), server3.getTq(), server4.getTq());
            stats.setThroughputRates(server1.getMeanThroughputRate(), server2.getMeanThroughputRate(),
                                     server3.getMeanThroughputRate(), server4.getMeanThroughputRate());
            stats.setLoads(server1.getLoad(), server2.getLoad(), server3.getLoad(), server4.getLoad());
            stats.setOperationStatsServer2(server2.getOperationTimeStatistics());
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
     * Provede inicializaci site front.
     * @param par - parametry site front
     * @throws JSimException
     */
    private void initialize(SimulationParameters par) throws JSimException {
        /* vytvoreni potrebnych objektu */
        if (par.getDistribution() == Distribution.EXPONENTIAL) {
            generator1 = new ExponentialTransactionGenerator("Generator 1", this, par.getLambda1());
            generator2 = new ExponentialTransactionGenerator("Generator 2", this, par.getLambda2());
            server1 = new ExponentialServer("Server 1", this, 1 / par.getTs1());
            server2 = new ExponentialServer("Server 2", this, 1 / par.getTs2());
            server3 = new ExponentialServer("Server 3", this, 1 / par.getTs3());
            server4 = new ExponentialServer("Server 4", this, 1 / par.getTs4());
        } else {
            generator1 = new GaussianTransactionGenerator("Generator 1", this, 1 / par.getLambda1(), par.getC() * (1 / par.getLambda1()));
            generator2 = new GaussianTransactionGenerator("Generator 2", this, 1 / par.getLambda2(), par.getC() * (1 / par.getLambda2()));
            server1 = new GaussianServer("server 1", this, par.getTs1(), par.getC() * par.getTs1());
            server2 = new GaussianServer("server 2", this, par.getTs2(), par.getC() * par.getTs2());
            server3 = new GaussianServer("server 3", this, par.getTs3(), par.getC() * par.getTs3());
            server4 = new GaussianServer("server 4", this, par.getTs4(), par.getC() * par.getTs4());
        }
        Splitter splitter1 = new Splitter(server3, par.getP2(), server2);
        Splitter splitter2 = new Splitter(server4, par.getP3(), server1);
        outputNode = new OutputNode(this);
        spy = new Spy("Spy", this, server1, server2, server3, server4);
        
        /* propojeni objektu do site */
        generator1.setNextReceiver(server1);
        generator2.setNextReceiver(server2);
        server1.setNextReceiver(server2);
        server2.setNextReceiver(splitter1);
        server3.setNextReceiver(splitter2);
        server4.setNextReceiver(outputNode);
    }


}
