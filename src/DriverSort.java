/**
 *
 * @author gianlucatruda
 * Created 26 July 2016
 */
public class DriverSort {
    
    public static void main(String[] args){
        //Initialising important variables and assigning them default values
        String SORT = "Quicksort";
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
    }
    
}
