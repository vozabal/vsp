package simulation;


/**
 * Objekt nesouci namerene statistiky.
 *
 * @author Miroslav Vozabal
 */
public class SimulationStatistics {
    
    /** Pole hodnot Lq pro jednotlive servery. */
    private double[] Lqi;
    
    /** Celkove Lq systemu. */
    private double Lq;
    
    /** Pole hodnot Tq pro jednotlive servery. */
    private double[] Tqi;
    
    /** Celkove Tq systemu. */
    private double Tq;
    
    /** Pole hodnot toku pozadavku jednotlivymi uzly. */
    private double[] throughputRates;
    
    /** Pole hodnot zatizeni jednotlivych uzlu. */
    private double[] loads;
    
    /** Statistiky doby cinnosti na serveru 2. */
    private Statistics operationStatsServer2;

    
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
     * @return the operationStatsServer2
     */
    public Statistics getOperationStatsServer2() {
        return operationStatsServer2;
    }


    
    /**
     * @param operationStatsServer2 the operationStatsServer2 to set
     */
    public void setOperationStatsServer2(Statistics operationStatsServer2) {
        this.operationStatsServer2 = operationStatsServer2;
    }

}
