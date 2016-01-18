package simulation;

import simulation.QueueSimulation.Distribution;



/**
 * Represents and object that preservers the simulated queue network parameters.
 *
 * @author Miroslav Vozabal
 */
public class SimulationParameters {
    
    /** The random values distribution. Arrivals of the transactions (times of the services). */
    private Distribution distribution;
    
    /** The mean frequency of the entry stream 1. */
    private double lambda1;
    
    /** The mean frequency of the entry stream 2. */
    private double lambda2;
    
    /** The mean frequency of the service of the server 1. */    
    private double ts1;
    
    /** The mean frequency of the service of the server 2. */
    private double ts2;
    
    /** The mean frequency of the service of the server 3. */
    private double ts3;

    /** The mean frequency of the service of the server 4. */
    private double ts4;
    
    /** The probability of hanging a transaction over from the server 2 to the server 3. */
    private double prb2;
    
    /** The probability of hanging a transaction over from the server 3 to the server 4. */
    private double prb3;
    
    /** The coefficient of the variance (only for Gaussian distribution). */
    private double varianceCoefficient;

    
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
     * @return the prb2
     */
    public double getPrb2() {
        return prb2;
    }

    
    /**
     * @param prb2 the prb2 to set
     */
    public void setPrb2(double prb2) {
        this.prb2 = prb2;
    }

    
    /**
     * @return the prb3
     */
    public double getPrb3() {
        return prb3;
    }

    
    /**
     * @param prb3 the prb3 to set
     */
    public void setPrb3(double prb3) {
        this.prb3 = prb3;
    }

    
    /**
     * @return the varianceCoefficient
     */
    public double getVarianceCoefficient() {
        return varianceCoefficient;
    }

    
    /**
     * @param varianceCoefficient the varianceCoefficient to set
     */
    public void setVarianceCoefficient(double varianceCoefficient) {
        this.varianceCoefficient = varianceCoefficient;
    }

}
