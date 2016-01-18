package simulation;





/**
 * Represents an object that preservers the simulation statistics.
 *
 * @author Miroslav Vozabal
 */
public class SimulationStats {
    
	/** The Lq array of values for the particular servers. */
    private double[] Lqi;
    
    /** The system Lq. */
    private double Lq;
    
    /** The Tq array of values for the particular servers. */
    private double[] Tqi;
    
    /** The system Tq. */
    private double Tq;
    
    /** The array of transaction stream values of particular nodes. */
    private double[] throughputRates;
    
    /** The array of particular nodes loads values*/
    private double[] loads;
    
    /** The statistics of the stream through the node P, the node of the individual measurement */
    private Stats probeStats;

    
    /**
     * @return the lqi
     */
    public double getLqi(int nodeIndex) {
        return Lqi[nodeIndex];
    }

    
    /**
     * @param lqi the lqi to set
     */
    public void setLqi(double... lqi) {
        Lqi = lqi;
    }

    
    /**
     * @return the lq
     */
    public double getLq() {
        return Lq;
    }

    
    /**
     * @param lq the lq to set
     */
    public void setLq(double lq) {
        Lq = lq;
    }

    
    /**
     * @return the tqi
     */
    public double getTqi(int nodeIndex) {
        return Tqi[nodeIndex];
    }

    
    /**
     * @param tqi the tqi to set
     */
    public void setTqi(double... tqi) {
        Tqi = tqi;
    }

    
    /**
     * @return the tq
     */
    public double getTq() {
        return Tq;
    }

    
    /**
     * @param tq the tq to set
     */
    public void setTq(double tq) {
        Tq = tq;
    }


    
    
    /**
     * @return the throughputRates
     */
    public double getThroughputRate(int nodeIndex) {
        return throughputRates[nodeIndex];
    }


    
    
    /**
     * @param throughputRates the throughputRates to set
     */
    public void setThroughputRates(double... throughputRates) {
        this.throughputRates = throughputRates;
    }


    
    
    /**
     * @return the loads
     */
    public double getLoad(int nodeIndex) {
        return loads[nodeIndex];
    }

    

    
    /**
     * @param loads the loads to set
     */
    public void setLoads(double... loads) {
        this.loads = loads;
    }


    
    /**
     * @return the probeStats
     */
    public Stats getProbeStats() {
        return probeStats;
    }


    
    /**
     * @param probeStats the probeStats to set
     */
    public void setProbeStats(Stats probeStats) {
        this.probeStats = probeStats;
    }

}
