import java.util.Arrays;
import java.util.Random;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

/**
 * Parallel Programming Assignment for CSC2002S
 * @author gianlucatruda
 * Created 26 July 2016
 */
public class DriverSort {
    
    @SuppressWarnings("empty-statement")
    public static void main(String[] args) throws IOException{
        //Initialising important variables and assigning them default values
        String SORT = "Mergesort";
        int ARRAYSIZEMIN = 1000000;
        int ARRAYSIZEMAX = 31000000;
        int ARRAYSIZEINCR = 5000000;
        String OUTFILENAME = "data.txt";
        double THRESHVAL = 0.5;
        
        //Getting the commandline arguments from the user
        try{
            SORT = args[0];
            ARRAYSIZEMIN = Integer.parseInt(args[1]);
            ARRAYSIZEMAX = Integer.parseInt(args[2]);
            ARRAYSIZEINCR = Integer.parseInt(args[3]);
            OUTFILENAME = args[4];
            THRESHVAL = Double.parseDouble(args[5]);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            if(args.length > 0) {System.out.println("Too few parameters! \n <sort> <arraySizeMin> <arraySizeMax> <arraySizeIncr> <outFileName> <thresholdCoefficient>");}
        }
        
        System.out.println("\nInstance : " + SORT +"; " + ARRAYSIZEMIN +"; " + ARRAYSIZEMAX +"; " + ARRAYSIZEINCR +"; " + OUTFILENAME +"; " +THRESHVAL+"\n"+"Time(ms)");
        writeToFile(OUTFILENAME, "\n"+SORT +"; " + ARRAYSIZEMIN +"; " + ARRAYSIZEMAX +"; " + ARRAYSIZEINCR +"; " + OUTFILENAME +"; "+ THRESHVAL); 

        //Variables to implement across all testing
        int[] arr;
        long tick = 0;
        Random rand = new Random();
        int elementSize = 100000;
        int repeats = 5;
        
        //Code to perform generalised timing, with an embedded if-net to only perform the user-specified sort on each run
        for(int arraySize=ARRAYSIZEMIN; arraySize<=ARRAYSIZEMAX; arraySize=arraySize+ARRAYSIZEINCR){
            String outText ="";
            int best = 100000;
            arr = new int[arraySize];
            for(int i=0; i<repeats; i++){
                for(int j=0; j<arraySize; j++){
                    arr[j]=rand.nextInt(elementSize);
                }
                //Garbage collection called to avoid it slowing down the testing
                System.gc();
                
                //If statements to run the appropriate sort based on user parameters from command line
                if(SORT.equals("Quicksort") || SORT.equals("quicksort")){
                    QuicksortParallel qsp = new QuicksortParallel(arr,(int)(THRESHVAL*arraySize));
                    tick = getTime();
                    qsp.compute();
                }
                if(SORT.equals("Mergesort") || SORT.equals("mergesort")){
                    MergesortParallel msp = new MergesortParallel(arr, 0, arraySize, (int)(THRESHVAL*arraySize));
                    tick = getTime();
                    msp.compute();
                }
                if(SORT.equals("System") || SORT.equals("system")){
                    tick = getTime();
                    Arrays.sort(arr);
                }
                //Code to call a timeout on anything that runs longer than 60 seconds on one sort.
                int duration = (int)(getTime() - tick);
                if(duration > 60000){
                    best = 60000;
                    System.exit(duration);
                }
                //Code to keep track of the best time recorded for the set at that specific N.
                if(duration < best){
                    best = duration;
                }
            }
            
            outText+= best;
            System.out.println(outText);
            writeToFile(OUTFILENAME, outText);
        }

    }
    
    /**
     * Returns the current time in milliseconds for purposes of timing.
     * @return timeInMillis
    */
    public static long getTime(){
        return System.currentTimeMillis();
    }
    
    
    /**
     * Simple function that can write text to a .txt file for review and data collection.
     * @param filename
     * @param text
     * @throws IOException 
     */
    public static void writeToFile(String filename, String text) throws IOException{
        FileWriter fw = new FileWriter(filename, true);
        try (PrintWriter pw = new PrintWriter(fw)) {
            pw.println(text);
        }
    }
}
