import java.util.Arrays;
import java.util.concurrent.*;
import java.util.Random;

/**
 * A quicksort algorithm that uses Java's lightweight threads from the Fork/Join Framework
 * to implement parallelism for the purpose of speedup.
 * Initially, both the recursive call step AND the partitioning step are parallelized!
 * Due to stack overflow and the sufficient performance upgrade of just parallelizing the recursive step,
 * the partitioning step has now been made sequential.
 * @author gianlucatruda
 * Created 26 July 2016
 */
public class QuicksortParallel extends RecursiveAction{
    //Declaration of private variables for a Quicksort object
    private int[] arr;
    private final int begin, end, thresh;
    private int pivot;
    
    //Constructor method
    public QuicksortParallel(int[] a, int thresh){
        this.arr = a;
        this.begin = 0;
        this.end = arr.length;
        this.thresh = thresh;
}
    //Override for the compute() method implemented from RecursiveAction
    @Override
    protected void compute() {
        //If the array provided is smaller than the threshold, then a sequential sort is performed.
        if((arr.length)<= thresh){
            Arrays.sort(arr, begin, end);
        }
        else{
            this.pivot = getMedian();
            
            this.serialSort(arr[pivot]);
        }
    }
    
    private void serialSort(int pivVal){
        int[] bits = new int[end+1];
        int[] bitsum = new int[end+1];
        for (int i = 0; i <end; i++) {
            if(arr[i]>= pivVal){
                bits[i] = 1;
            }
            else if(arr[i]<pivVal){
                bits[i] = 0;
            }
        }
        
        serialPrefixSum(bits, bitsum);
        
        int[] right = new int[bitsum[bitsum.length-1]];
        int[] left = new int[bitsum.length-right.length-1];
        
        int count = 0;
        for (int i = 0; i <arr.length; i++){          
            if(bits[i]==1){
                right[bitsum[i]-1] = arr[i];
            }
            else{
                left[count++] = arr[i];
            }
        }
    
        QuicksortParallel leftKid = new QuicksortParallel(left, this.thresh);
        QuicksortParallel rightKid = new QuicksortParallel(right, this.thresh);
        
        //Parallelisation by dividing the array roughly in half and recursively sorting each half in individual threads.
        invokeAll(leftKid, rightKid);
        //Copying the sorted sub-arrays into the main array to conclude with a sorted array.
        System.arraycopy(left, 0, this.arr, 0, left.length);
        for (int i = 0; i < right.length; i++) {
            this.arr[i+left.length] = right[i];
        }
    }
    
    //Takes in the bit vector and a blank array, then writes the bit-sum values to the blank array.
    private void serialPrefixSum(int[] in, int[] out){
        int sum = 0;
        for (int i = this.begin; i <=this.end; i++) {
            sum+=in[i];
            out[i]=sum;
        }
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
        return piv;
    }
    
    /**
     * A pack algorithm using the bit-bitsum-output method to enable parallelization.
     * Although this works, it "over-parallelises" and thus is not suitable for large N values.
     */
    private void parallelSort(int val){
        int[] bits = new int[end+1];
        int[] bitsum = new int[end+1];
        for (int i = 0; i <end; i++) {
            if(arr[i]>= val){
                bits[i] = 1;
            }
            else if(arr[i]<val){
                bits[i] = 0;
            }
        }
        //Parallelised Prefix-Sum Algorithm
        int psThresh;
        if(thresh >= 8){psThresh = thresh/2;}
        else{psThresh = 4;}       
        PrefixSumParallel pfp = new PrefixSumParallel(bits, bitsum, 0, arr.length, null, psThresh, true);
        invokeAll(pfp);
        pfp.apply();      
        //Create and populate the array of items greater than pivot value
        int[] right = new int[bitsum[bitsum.length-1]];
        int[] left = new int[(end)-right.length];
        int count = 0;
        for (int i = 0; i <arr.length; i++){          
            if(bits[i]==1){
                right[bitsum[i]-1] = arr[i];
            }
            else{
                left[count++] = arr[i];
            }
        }
        if(left.length >= 2 && right.length >= 2){
            invokeAll(  new QuicksortParallel(left, thresh),
                        new QuicksortParallel(right, thresh));
        }
        else if(left.length >= 2){
            invokeAll(new QuicksortParallel(left, thresh));
        }
        else if(right.length >= 2){
            invokeAll(new QuicksortParallel(right, thresh));
        }
        int[] out = new int[left.length+right.length];
        System.arraycopy(left, 0, out, 0, left.length);
        System.arraycopy(right, 0, out, left.length, right.length);
        System.out.println("out: "+ Arrays.toString(out));
        this.arr = out.clone();
    }
}
