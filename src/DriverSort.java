import java.util.Arrays;
import java.util.Random;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

/**
 *
 * @author gianlucatruda
 * Created 26 July 2016
 */
public class DriverSort {
    
    @SuppressWarnings("empty-statement")
    public static void main(String[] args) throws IOException{
        //Initialising important variables and assigning them default values
        String SORT = "Quicksort";
        int ARRAYSIZEMIN = 1000000;
        int ARRAYSIZEMAX = 10000000;
        int ARRAYSIZEINCR = 1000000;
        String OUTFILENAME = "data.txt";
        
        //Getting the commandline arguments from the user
        try{
            SORT = args[0];
            ARRAYSIZEMIN = Integer.parseInt(args[1]);
            ARRAYSIZEMAX = Integer.parseInt(args[2]);
            ARRAYSIZEINCR = Integer.parseInt(args[3]);
            OUTFILENAME = args[4];
        }
        catch (ArrayIndexOutOfBoundsException e) {
            if(args.length > 0) {System.out.println("Too few parameters! \n <sort> <arraySizeMin> <arraySizeMax> <arraySizeIncr> <outFileName>");}
            System.out.println("Instance : " + SORT +"; " + ARRAYSIZEMIN +"; " + ARRAYSIZEMAX +"; " + ARRAYSIZEINCR +"; " + OUTFILENAME);
            writeToFile(OUTFILENAME, SORT +"; " + ARRAYSIZEMIN +"; " + ARRAYSIZEMAX +"; " + ARRAYSIZEINCR +"; " + OUTFILENAME); 
        }
        
        //Variables to implement across all testing
        int[] arr;
        long tick = 0;
        Random rand = new Random();
        int elementSize = 100000;
        int repeats = 2;
        
        //Code to perform generalised timing, with an imbedded if-net to only perform the user-specified sort on each run
        for(int arraySize=ARRAYSIZEMIN; arraySize<=ARRAYSIZEMAX; arraySize=arraySize+ARRAYSIZEINCR){
            String outText ="";
            arr = new int[arraySize];
            for(int i=0; i<arraySize; i++){
                arr[i]=rand.nextInt(elementSize);
            }
            outText+="Size: "+arraySize+"\tTime(ms):";
            for(int i=0; i<repeats; i++){
                //Garbage collection called to avoid it slowing down the testing
                System.gc();
                //If statements to run the appropriate sort based on user parameters from command line
                if(SORT.equals("Quicksort") || SORT.equals("quicksort")){
                    QuicksortParallel qsp = new QuicksortParallel(arr, 0, arraySize, (int)(2500000));
                    tick = getTime();
                    qsp.compute();
                }
                if(SORT.equals("Mergesort") || SORT.equals("mergesort")){
                    MergesortParallel msp = new MergesortParallel(arr, 0, arraySize, (int)(2500000));
                    tick = getTime();
                    msp.compute();
                }
                if(SORT.equals("Altsort") || SORT.equals("altsort")){

                }
                if(SORT.equals("System") || SORT.equals("system")){
                    tick = getTime();
                    Arrays.sort(arr);
                }
                int duration = (int)(getTime() - tick);
                outText+="\t"+duration;
            }
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
    
    public static void writeToFile(String filename, String text) throws IOException{
        FileWriter fw = new FileWriter(filename, true);
        try (PrintWriter pw = new PrintWriter(fw)) {
            pw.println(text);
        }
    }
}
