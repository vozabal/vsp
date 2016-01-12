package simulation;

import simulation.QueingNetworkSimulation.Distribution;


/**
 * Objekt nesouci parametry simulovane site front.
 *
 * @author Miroslav Vozabal
 */
public class SimulationParameters {
    
    /** Rozdeleni nahodnych velicin (prichody pozadavku, doby obsluhy). */
    private Distribution distribution;
    
    /** Stredni frekvence vstupniho toku 1. */
    private double lambda1;
    
    /** Stredni frekvence vstupniho toku 2. */
    private double lambda2;
    
    /** Stredni doba obsluhy na serveru 1. */
    private double ts1;
    
    /** Stredni doba obsluhy na serveru 2. */
    private double ts2;
    
    /** Stredni doba obsluhy na serveru 3. */
    private double ts3;

    /** Stredni doba obsluhy na serveru 4. */
    private double ts4;
    
    /** Pravdepodobnost predani pozadavku ze serveru 2 do serveru 3. */
    private double p2;
    
    /** Pravdepodobnost predani pozadavku ze serveru 3 do serveru 4. */
    private double p3;
    
    /** Koeficient variace (pouze pro normalni rozdeleni nahodnych velicin). */
    private double c;

    
    /**
     * @return the distribution
     */
    public Distribution getDistribution() {
        return distribution;
    }

    
    /**
     * @param distribution the distribution to set
     */
    public void setDistribution(Distribution distribution) {
        this.distribution = distribution;
    }

    
    /**
     * @return the lambda1
     */
    public double getLambda1() {
        return lambda1;
    }

    
    /**
     * @param lambda1 the lambda1 to set
     */
    public void setLambda1(double lambda1) {
        this.lambda1 = lambda1;
    }

    
    /**
     * @return the lambda2
     */
    public double getLambda2() {
        return lambda2;
    }

    
    /**
     * @param lambda2 the lambda2 to set
     */
    public void setLambda2(double lambda2) {
        this.lambda2 = lambda2;
    }

    
    /**
     * @return the ts1
     */
    public double getTs1() {
        return ts1;
    }

    
    /**
     * @param ts1 the ts1 to set
     */
    public void setTs1(double ts1) {
        this.ts1 = ts1;
    }

    
    /**
     * @return the ts2
     */
    public double getTs2() {
        return ts2;
    }

    
    /**
     * @param ts2 the ts2 to set
     */
    public void setTs2(double ts2) {
        this.ts2 = ts2;
    }

    
    /**
     * @return the ts3
     */
    public double getTs3() {
        return ts3;
    }

    
    /**
     * @param ts3 the ts3 to set
     */
    public void setTs3(double ts3) {
        this.ts3 = ts3;
    }

    
    /**
     * @return the ts4
     */
    public double getTs4() {
        return ts4;
    }

    
    /**
     * @param ts4 the ts4 to set
     */
    public void setTs4(double ts4) {
        this.ts4 = ts4;
    }

    
    /**
     * @return the p2
     */
    public double getP2() {
        return p2;
    }

    
    /**
     * @param p2 the p2 to set
     */
    public void setP2(double p2) {
        this.p2 = p2;
    }

    
    /**
     * @return the p3
     */
    public double getP3() {
        return p3;
    }

    
    /**
     * @param p3 the p3 to set
     */
    public void setP3(double p3) {
        this.p3 = p3;
    }

    
    /**
     * @return the c
     */
    public double getC() {
        return c;
    }

    
    /**
     * @param c the c to set
     */
    public void setC(double c) {
        this.c = c;
    }

}
