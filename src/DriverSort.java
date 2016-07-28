
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.*;
import java.util.Random;

/**
 *
 * @author gianlucatruda
 * Created 26 July 2016
 */
public class DriverSort {
    
    @SuppressWarnings("empty-statement")
    public static void main(String[] args){
        //Initialising important variables and assigning them default values
        String SORT = "System";
        int ARRAYSIZEMIN = 10000;
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
        }
        
        //Variables to implement across all testing
        int[] arr;
        Random rand = new Random();
        int elementSize = 1000000;
        int repeats = 5;
        
        for(int arraySize=ARRAYSIZEMIN; arraySize<ARRAYSIZEMAX; arraySize=arraySize+ARRAYSIZEINCR){
                arr = new int[arraySize];
                for(int i=0; i<arraySize; i++){
                    arr[i]=rand.nextInt(elementSize);
                }
                System.out.print("Size: "+arraySize);
                for(int i=0; i<repeats; i++){
                    //Garbage collection called to avoid it slowing down the testing
                    System.gc();
                    long tick = getTime();
                    //If statements to run the appropriate sort based on user parameters from command line
                    if(SORT.equals("Quicksort") || SORT.equals("quicksort")){

                    }
                    if(SORT.equals("Mergesort") || SORT.equals("mergesort")){

                    }
                    if(SORT.equals("Altsort") || SORT.equals("altsort")){

                    }
                    if(SORT.equals("System") || SORT.equals("system")){
                        Arrays.sort(arr);
                    }
                    int duration = (int)(getTime() - tick);
                    System.out.print("\t"+duration+" ms");
                }
                System.out.print("\n");
            }
        
        //Testing for Mergesort implementation
        
        /**
         * Call System.gc() to minimize the likelihood that the garbage 
         * collector will run during your execution 
         * (but do not call this within your timing block!).
        */
    }
    
    /**
     * Returns the current time in milliseconds for purposes of timing.
    */
    public static long getTime(){
        return System.currentTimeMillis();
    }
}
