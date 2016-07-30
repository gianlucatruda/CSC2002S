import java.util.Arrays;
import java.util.concurrent.*;

/**
 * A quicksort algorithm that uses Java's lightweight threads from the Fork/Join Framework
 * to implement parallelism for the purpose of speedup.
 * @author gianlucatruda
 * Created 26 July 2016
 */
public class QuicksortParallel extends RecursiveAction{
    //Declaration of private variables for a Quicksort object
    private int[] arr;
    private int[] clone;
    private final int begin, end, thresh;
    
    //Constructor method
    public QuicksortParallel(int[] a, int begin, int end, int thresh){
        this.arr = a;
        this.begin = begin;
        this.end = end;
        this.thresh = thresh;
}

    //Override for the compute() method implemented from RecursiveAction
    @Override
    protected void compute() {
        if((end-begin)<= thresh){
            Arrays.sort(arr, begin, end);
        }
    }
    
}
