import java.util.Arrays;
import java.util.concurrent.*;

/**
 * A Mergesort algorithm that uses Java's lightweight threads from the Fork/Join Framework
 * to implement parallelism for the purpose of speedup.
 * @author gianlucatruda
 * Created 26 July 2016
 */
public class MergesortParallel extends RecursiveAction{
    //Declaration of the private variables for a Mergesort object
    private int[] arr;
    private int begin, end;
    private int thresh;
    
    //Constructor method for MergesortParallel
    public MergesortParallel(int[] a, int begin, int end, int threshold){
        this.arr = a;
        this.begin = begin;
        this.end = end;
        this.thresh = threshold;
    }
    
    
    //Override for the compute() method implemented from RecursiveAction (main body of code)
    @Override
    protected void compute() {
        //If statement to catch the instance where the threshold has been reached and to perform a sequential sort.
        if(end - begin <= thresh){
            //A system-implemented sequential sort over the range provided.
            Arrays.sort(arr, begin, end);
        }
        else{
            //Parallel code to sort the two halves in parallel
            
            
        }
        
        
        
        //Sequential merge of the two halves
        
        
    }
    
}
