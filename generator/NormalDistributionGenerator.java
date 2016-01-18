package generator;


/**
 * Generator of random numbers of Gaussian distribution.
 *
 * @author Miroslav Vozabal
 */
public class NormalDistributionGenerator {
    
    /** Max length of the histogram row. It gives us the max count of the stars */
    private static final int GRAPH_MAX_SIZE = 70;
    
    /** Count of values which are checked by the histogram. */
    private static final int GRAPH_VALUES = 31;
    
    
    /**
     * Generates random numbers with the Gauss Distribution.
     * @param meanValue - mean value
     * @param standardDeviation - standard deviation
     * @return random number
     */
    public static double generateGauss(double meanValue, double standardDeviation) {
        double sum = 0.0;
        for (int i = 0; i < 12; i++)
            sum += Math.random();
        return (meanValue + standardDeviation * (sum - 6));
    }
    
    
    /**
     * Prints stars into the console.
     * @param graph an array that represents number of values in the histogram
     * @param min minimal value of the histogram
     * @param interval the length of one interval in the histogram
     */
    private static void printGraph(int[] graph, double min, double interval) {
        int max = 0;
        for (int i = 0; i < graph.length; i++)
            if (graph[i] > max) max = graph[i];
        int factor = max / GRAPH_MAX_SIZE;

        double value = min;
        for (int i = 0; i < graph.length; i++) {
            System.out.format("%6.3f - %6.3f: ", value, value + interval);
            value += interval;
            for (int j = 0; j < graph[i] / factor; j++)
                System.out.print("*");
            System.out.println();
        }
    }
    
    
    /**
     * Starts the test function {@link #generateGauss(double, double)}.
     * @param numberCount - the count of generated numbers 
     * @param meanValue - mean value
     * @param standardDeviation - standard deviation
     */
    public static void verifyGauss(int numberCount, double meanValue, double standardDeviation) {
        double ex = 0.0, powX = 0.0, dispersion = 0.0;
        double random;
        int[] graph = new int[GRAPH_VALUES];
        double min = meanValue - 3 * standardDeviation;  // minimal value
        double max = meanValue + 3 * standardDeviation;  // maximal value
        double interval = (6 * standardDeviation) / graph.length;  // the length of one histogram interval
        
        System.out.print("Starting a test of the generator of random numbers... ");

        for (int i = 0; i < numberCount; i++) {
            random = generateGauss(meanValue, standardDeviation);
            ex = (ex * i + random) / (i + 1);             // continuous computation of E(X)
            powX = (powX * i + random * random) / (i + 1);  // continuous computation of E(X^2)
            dispersion = powX - ex * ex;                             // continuous computation of D(X)
            
            // graph actualization
            if (random > min && random < max) {
                int index = (int) ((random - min) / interval);
                graph[index]++;
            }
        }
        
        System.out.println("complete\n\n*** Test results ***");
        System.out.println("Mean value E(X)");
        System.out.println("    required: " + meanValue);
        System.out.println("    test:       " + ex);
        System.out.println("Standard deviation");
        System.out.println("    required: " + standardDeviation);
        System.out.println("    test:       " + Math.pow(dispersion, 0.5) + "\n");
        printGraph(graph, min, interval);        
    }
}
