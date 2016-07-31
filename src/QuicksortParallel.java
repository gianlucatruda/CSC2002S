import java.util.Arrays;
import java.util.concurrent.*;
import java.util.Random;

/**
 * A quicksort algorithm that uses Java's lightweight threads from the Fork/Join Framework
 * to implement parallelism for the purpose of speedup.
 * Both the recursive call step AND the partitioning step are parallelized!
 * @author gianlucatruda
 * Created 26 July 2016
 */
public class QuicksortParallel extends RecursiveAction{
    //Declaration of private variables for a Quicksort object
    private int[] arr;
    private int[] clone;
    private final int begin, end, thresh;
    private int pivot;
    
    //Constructor method
    public QuicksortParallel(int[] a, int begin, int end, int thresh){
        this.arr = a;
        this.begin = begin;
        this.end = end;
        this.thresh = thresh;
        this.pivot = (end-begin)/2;
}
    //Override for the compute() method implemented from RecursiveAction
    @Override
    protected void compute() {
        //If the array provided is smaller than the threshold, then a sequential sort is performed.
        if((end-begin)<= thresh){
            Arrays.sort(arr, begin, end);
        }
        else{
            this.pivot = getMedian();
            //Perform a parallel pack
            
        }
    }
    
    
    /**
     * A pack algorithm using the bit-bitsum-output method to enable parallelization.
     */
    private void pack(int start, int end, int val){
        int[] bits = new int[(end-start)+1];
        int[] bitsum = new int[bits.length];
        for (int i = start; i < end; i++) {
            if(arr[i]<val){
                bits[i-start] = 0;
            }
            else if(arr[i]>= val){
                bits[i-start] = 1;
            }
        }
        //Prefix-Sum Algorithm
        
    }
    
    
    /**
     * Estimates the median of the array in O(c) time and returns the index of the median.
     * @return pivot
     */
    private int getMedian(){
        //Sneaky trick to select a pretty good privot in O(1) time.
        Random rand = new Random();
        int[] medianator = new int[3];
        for (int i = 0; i <3; i++) {
            medianator[i] = rand.nextInt(end);
        }
        int hi = medianator[0];
        int lo = medianator[0];
        int piv = medianator[0];
        for (int i = 0; i < 3; i++) {
            if(arr[medianator[i]]<= arr[hi] && arr[medianator[i]]>=arr[lo]){
                piv = medianator[i];
            }
            if(arr[medianator[i]]>=arr[hi]){
                hi = medianator[i];
            }
            if(arr[medianator[i]]<=arr[lo]){
                lo = medianator[i];
            }
        }
        //System.out.println(arr[medianator[0]]+";"+arr[medianator[1]]+";"+arr[medianator[2]]+" :\t"+pivot+"\t"+arr[pivot]);
        return piv;
    }
    
}
