package simulation;

import java.util.ArrayList;
import java.util.List;
import cz.zcu.fav.kiv.jsim.JSimException;
import cz.zcu.fav.kiv.jsim.JSimInvalidParametersException;
import cz.zcu.fav.kiv.jsim.JSimProcess;
import cz.zcu.fav.kiv.jsim.JSimSimulation;
import cz.zcu.fav.kiv.jsim.JSimSimulationAlreadyTerminatedException;
import cz.zcu.fav.kiv.jsim.JSimSystem;
import cz.zcu.fav.kiv.jsim.JSimTooManyProcessesException;
import nodes.AbstractServer;







/**
 * The tracker object is used for tracking of the system statistics.
 * Tracks the mean transactions count in the system. In no periodic intervals finds out the state of the system and updates the tracked statistics.
 *
 * @author Miroslav Vozabal
 */
public class Tracker extends JSimProcess {
    
    /** The mean frequency of the system tracking. */
    public static final double LAMBDA = 2; 
    
    /** The mean transaction count in the system. */
    private double meanTransactionCount = 0;
    
    /** The counter of performed samplings of the system. */
    private int samplingsCounter = 0;
    
    /** The list of the tracked servers. */
    private List<AbstractServer> servers;


    
    
    /**
     * Creates a new tracking object.
     * @param name - tracker's name
     * @param parent - the parent simulation
     * @param servers - all tracked servers in the simulation.
     * @throws JSimSimulationAlreadyTerminatedException
     * @throws JSimInvalidParametersException
     * @throws JSimTooManyProcessesException
     */
    public Tracker(String name, JSimSimulation parent, AbstractServer... servers) 
            throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException {
        super(name, parent);
        this.servers = new ArrayList<AbstractServer>(servers.length);
        for (AbstractServer server : servers)
            this.servers.add(server);
    }
    
    
    
    /**
     * Returns the mean count of the transactions in the system during all the simulation time.
     * @return the mean count of the transactions in the system
     */
    public double getMeanTransactionCount() {
        return this.meanTransactionCount;
    }
    
    
    
    /**
     * In no periodical intervals processes samplings and updates the tracked statistic (The mean transaction number of the transactions in the system)
     * 
     */
    @Override
    protected void life() {
        long currentLife;
        
        try {
            while (true) {
                hold(Math.abs(JSimSystem.negExp(LAMBDA)));
                currentLife = 0;
                for (AbstractServer server : servers)
                    currentLife += server.getTransactionCount();
                
                meanTransactionCount = (meanTransactionCount * samplingsCounter + currentLife) / (samplingsCounter + 1);
                samplingsCounter++;
            }
        } catch (JSimException e) {
            e.printStackTrace();
        }
    }
}



