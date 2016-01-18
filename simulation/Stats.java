package simulation;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;





/**
 * Trackes statistics of the random value X. Provides the meanValue E(X), 
 * dispersion D(X), standard deviation &sigma;(X) and the histogram.
 *
 * @author Miroslav Vozabal
 */
public class Stats {
    
    /** The resolution rate of the values in the graph (higher value, higher resolution). */
    public static final int RESOLUTION_RATE = 1000;
    
    /** The count of the graph lines written into the console. */
    public static final int GRAPH_LINES = 75;
    
    /** The maximum of the stars in the graph that are written into the console. */
    public static final int GRAPH_LENGHT = 75;     
    
    
    /** The counter of the realization of the random value. */
    private int counter = 0;
    
    /** The sum of every random value occurrences. */
    private double sum = 0.0;
    
    /** The mean value. */
    private double meanValue = 0;
    
    /** A variable that contains the value E(X^2). It's used for the computation of D(X). */
    private double ex2;
    
    /** The map of the X count occurrences. It's used for the graph */
    private Map<Integer, Integer> graph = new HashMap<>();
    
    
    
    
    /**
     * Adds the occurrence of the random value X.
     * @param value - the occurrence of the random value
     */
   public void addValue(double value) {
        sum += value;
        meanValue = (meanValue * counter + value) / (counter + 1);    // continuous computation of E(X)
        ex2 = (ex2 * counter + value * value) / (counter + 1);        // continuous computation of E(X^2)
        counter++;
        
        /* Updates of the graph */
       int key = (int) (value * RESOLUTION_RATE);
        if (graph.containsKey(key)) graph.put(key, graph.get(key) + 1);
        else graph.put(key, 1);
    }
  
    
   
    /**
     * * Creates a string which contains the actual graph that has the maximum rows count
     * count {@link #GRAPH_LINES} and the maximum number of chars appeared nearby to the particular value {@link #GRAPH_LENGHT}.
     * @return string contains a text representation of the graph
     */
    public String getGraph() {
        StringBuilder builder = new StringBuilder();
        Integer counter;
        
        int step = graph.size() / GRAPH_LINES;
        if (step == 0) step = 1;
        int tempMeanValue = (int) (meanValue * RESOLUTION_RATE);
        int firstNumber = tempMeanValue - (GRAPH_LINES / 2) * step;
        int lastNumber = tempMeanValue + (GRAPH_LINES / 2) * step;
        int rate = Collections.max(graph.values()) / GRAPH_LENGHT + 1;

        for (int i = firstNumber; i <= lastNumber; i += step) {
            if (i < 0) continue;
            builder.append(String.format("%.3f: ", ((double) i / RESOLUTION_RATE)));
            counter = graph.get(i);
            if (counter != null) {
                for (int j = 0; j < counter / rate; j++)
                    builder.append("*");
                builder.append("  (" + counter + ")");
            }
            builder.append('\n');
        }
        
        return builder.toString();
    }
    
    
    
    /**
     * Return the meanValue E(X) of the tracked value X.
     * 
     * @return meanValue E(X)
     */
    public double getMeanValue() {
        return meanValue;
    }
    
    
    
    /**
     * Returns the dispersion D(X) of the tracked value X.
     * 
     * @return dispersion D(X)
     */
    public double getVariance() {
        return ex2 - meanValue * meanValue;
    }
    
    
    
    /**
     * Returns the standard deviation &sigma;(X) of the tracekd value X.
     *  
     * @return the standard deviation &sigma;(X)
     */
    public double getStandardDeviation() {
        return Math.pow(getVariance(), 0.5);
    }
    
    
    
    /**
     * Returns the sum of the all values of the tracked random value X.
     * 
     * @return The all values sum.
     */
    public double getSum() {    	
        return sum;
    }

}
