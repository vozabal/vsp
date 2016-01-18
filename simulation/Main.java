package simulation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import cz.zcu.fav.kiv.jsim.JSimException;
import generator.NormalDistributionGenerator;
import simulation.QueueSimulation.Distribution;



/**
 * The main class with the main method. It is used like the entry point of the application.
 *
 * @author Miroslav Vozabal
 */
public class Main {
    
    /** The default count of the transactions which go through the system. */
    public static final int TRANSACTION_COUNT = 100000;
    
    /** The coefficient of the variance for the first normal distribution simulation. */
    public static final double VARIANCE_COEFFICIENT_1 = 0.02;
    
    /** The coefficient of the variance for the second normal distribution simulation. */
    public static final double VARIANCE_COEFFICIENT_2 = 0.3;
    
    /** The coefficient of the variance for the third normal distribution simulation. */
    public static final double VARIANCE_COEFFICIENT_3 = 0.7;
    
    /** The name of the configuration file with the parameters of the queue network. */
    public static final String CONFIGURATION_FILE_NAME = "res\\simulation.properties";
    
    /** The horizontal row which separates records. */
    public static final String ROW = "\n---------------------------------------------------------------------\n";
    
    /** The highlight of the heading. */
    public static final String HIGHLIGHT_ROW = "=======";
    
    
    /*
     * JSim switching off.
     */
    static {
        Logger.getLogger("").setLevel(Level.OFF);
    }
    
    
    /**
     * The main entrance of the application. It parses the parameters of the command line and runs the simulation. With regarded parameters
     * 
     * 
     * @param args the arguments of the command line
     */
    public static void main(String[] args) {
        if (args.length == 2) {
            try {
                int transactionNumber = Integer.parseInt(args[0]);
                if (args[1].equalsIgnoreCase("EXP"))
                    startSimulation(Distribution.EXPONENTIAL, transactionNumber);
                else if (args[1].equalsIgnoreCase("GAUSS"))
                    startSimulation(Distribution.GAUSSIAN, transactionNumber);
                else
                    printIntroduction();
            } catch (NumberFormatException e) {
                printIntroduction();
                return;
            }
        } else if (args.length == 0) {
            startSimulation(Distribution.EXPONENTIAL, TRANSACTION_COUNT);
            System.out.println(ROW);
            startSimulation(Distribution.GAUSSIAN, TRANSACTION_COUNT);
        } else if (args.length == 4) {
            if (args[0].equals("--test-generator")) {
                try {
                    int number = Integer.parseInt(args[1]);
                    double meanValue = Double.parseDouble(args[2]);
                    double standardDeviation = Double.parseDouble(args[3]);
                    NormalDistributionGenerator.verifyGauss(number, meanValue, standardDeviation);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    printIntroduction();
                }
            } else {
                printIntroduction();
            }
        } else {
            printIntroduction();
        }
    }
    
    //TODO: zkontrolovat link
    /**
     * Loads the parameters of the queue network from the configuration file and runs the simulation
     * for the particular count of transactions. If the distribution parametr is equal to 
     * {@link cz.zcu.QueueSimulation.vsp.simulation.QueingNetworkSimulation.Distribution#GAUSSIAN Distribution.GAUSSIAN},
     * it starts the simulation with different variance coefficients for tree times.
     *  
     * @param distribution random numbers distribution
     * @param transactionCount the count of the simulation transactions which are going go through the system
     */
    private static void startSimulation(Distribution distribution, int transactionCount) {
        try {
            SimulationParameters parameters = loadParameters();
            parameters.setDistribution(distribution);
            if (distribution == Distribution.EXPONENTIAL) {
                runSimulation(parameters, transactionCount);
            } else {
                parameters.setVarianceCoefficient(VARIANCE_COEFFICIENT_1);
                runSimulation(parameters, transactionCount);
                System.out.println(ROW);
                parameters.setVarianceCoefficient(VARIANCE_COEFFICIENT_2);
                runSimulation(parameters, transactionCount);
                System.out.println(ROW);
                parameters.setVarianceCoefficient(VARIANCE_COEFFICIENT_3);
                runSimulation(parameters, transactionCount);
            }
        } catch (JSimException e) {
            e.printStackTrace();
            e.printComment(System.err);
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error in loading of the parameters form the configuration file");
            e.printStackTrace();
        }
    }
    
    
    /**
     * Starts the simulation with particular parameters.
     * 
     * @param parameters simulation parameters
     * @param transactionCount transaction count which are going to go through the system
     * @throws JSimException
     */
    private static void runSimulation(SimulationParameters parameters, int transactionCount) throws JSimException {
        System.out.println("The distribution of random numbers: " + 
                (parameters.getDistribution() == Distribution.EXPONENTIAL ? "EXPONENTIAL" : "NORMAL"));
        if (parameters.getDistribution() == Distribution.GAUSSIAN)
            System.out.println("Variance coefficient: " + parameters.getVarianceCoefficient());
        
        QueueSimulation simulation = new QueueSimulation(parameters);
        SimulationStats stats = simulation.run(transactionCount);
        printStatistics(stats);
    }
    
    
    /**
     * Prints introduction information.
     */
    private static void printIntroduction() {
        System.out.println("Usage: qn-simulation [ <transactionCount> { EXP | GAUSS } ]");
        System.out.println("         qn-simulation --test-generator <numberCount> <meanValue> <standardDeviation>\n");
        System.out.println("If the simulation is started without parameters it will starts with numberCount = 100000. Firstly it will start with the expenencional distribution\n"
                + "afterwards with the normal distribution of random numbers.");
        System.out.println("Switcher --test-generator is used for the start of the Gaussian random values generator.");
    }
    
    
    /**
     * Prints statistics measured during the simulation.
     * @param stats measured statistics
     */
    private static void printStatistics(SimulationStats stats) {
        System.out.println("Measured statistics:");
        
        System.out.println("\n" + HIGHLIGHT_ROW + " Server 1 " + HIGHLIGHT_ROW);
        System.out.println("Stream frequency = " + stats.getThroughputRate(0));
        System.out.println("Load = " + stats.getLoad(0));
        System.out.println("Tq = " + stats.getTqi(0));
        System.out.println("Lq = " + stats.getLqi(0));
        
        System.out.println("\n" + HIGHLIGHT_ROW + " Server 2 " + HIGHLIGHT_ROW);
        System.out.println("Stream frequency = " + stats.getThroughputRate(1));
        System.out.println("Load = " + stats.getLoad(1));
        System.out.println("Tq = " + stats.getTqi(1));
        System.out.println("Lq = " + stats.getLqi(1));
        
        System.out.println("\n" + HIGHLIGHT_ROW + " Server 3 " + HIGHLIGHT_ROW);
        System.out.println("Stream frequency = " + stats.getThroughputRate(2));
        System.out.println("Load = " + stats.getLoad(2));
        System.out.println("Tq = " + stats.getTqi(2));
        System.out.println("Lq = " + stats.getLqi(2));
        
        System.out.println("\n" + HIGHLIGHT_ROW + " Server 4 " + HIGHLIGHT_ROW);
        System.out.println("Stream frequency = " + stats.getThroughputRate(3));
        System.out.println("Load = " + stats.getLoad(3));
        System.out.println("Tq = " + stats.getTqi(3));
        System.out.println("Lq = " + stats.getLqi(3));
        
        System.out.println("\n" + HIGHLIGHT_ROW + " The whole system " + HIGHLIGHT_ROW);
        System.out.println("Tq = " + stats.getTq());
        System.out.println("Lq = " + stats.getLq());
        
        System.out.println("\n" + HIGHLIGHT_ROW + " Stream tracking " + HIGHLIGHT_ROW);
        System.out.println("E(X) = " + stats.getProbeStats().getMeanValue());
        System.out.println("D(X) = " + stats.getProbeStats().getVariance());
        System.out.println("\n" + stats.getProbeStats().getGraph());
    }
    
    
    /**
     * Loads the queue network parameters from the configuration file {@link #CONFIGURATION_FILE_NAME}.
     * 
     * @return queue network parameters
     * @throws FileNotFoundException
     * @throws IOException
     * @throws NumberFormatException
     */
    private static SimulationParameters loadParameters() throws FileNotFoundException, IOException, NumberFormatException {
        Properties prop = new Properties();
        prop.load(Main.class.getClassLoader().getResourceAsStream(CONFIGURATION_FILE_NAME));

        SimulationParameters params = new SimulationParameters();
        params.setLambda1(Double.parseDouble(prop.getProperty("lambda1")));
        params.setLambda2(Double.parseDouble(prop.getProperty("lambda2")));
        params.setTs1(Double.parseDouble(prop.getProperty("Ts1")));
        params.setTs2(Double.parseDouble(prop.getProperty("Ts2")));
        params.setTs3(Double.parseDouble(prop.getProperty("Ts3")));
        params.setTs4(Double.parseDouble(prop.getProperty("Ts4")));
        params.setPrb2(Double.parseDouble(prop.getProperty("p2")));
        params.setPrb3(Double.parseDouble(prop.getProperty("p3")));
        
        return params;
    }
   

}
