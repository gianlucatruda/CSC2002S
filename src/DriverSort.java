
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
        String SORT = "Mergesort";
        int ARRAYSIZEMIN = 10000;
        int ARRAYSIZEMAX = 100000;
        int ARRAYSIZEINCR = 10000;
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
        
        //If statements to run the appropriate sort based on user parameters from command line
        if(SORT.equals("Quicksort") || SORT.equals("quicksort")){
            
        }
        if(SORT.equals("Mergesort") || SORT.equals("mergesort")){
            
        }
        if(SORT.equals("Altsort") || SORT.equals("altsort")){
            
        }
        
        //This is just code playing around with System.currentTimeMillis() to learn
        long time = System.currentTimeMillis();
        for(int i=0; i<1000000000; i++){
            i =i;
        }
        
        System.out.println(System.currentTimeMillis() - time + " ms.");
        
        //int[] arr = {8,3,5,7,1,4,2,6,9};
        int[] arr = new int[20];
        Random rand = new Random();
        for(int i=0; i<20; i++){
            arr[i] = rand.nextInt(100);
        }
        System.out.println(Arrays.toString(arr));
        
        MergesortParallel merge = new MergesortParallel(arr, 0, arr.length, 4);
        merge.compute();
        System.out.println(Arrays.toString(arr));
        
        /**
         * Call System.gc() to minimize the likelihood that the garbage 
         * collector will run during your execution 
         * (but do not call this within your timing block!).
        */
    }
    
}
