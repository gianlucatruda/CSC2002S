#!/bin/sh
#SBATCH -A icts
#SBATCH --partition=ucthimem
#SBATCH --time=180:00
#SBATCH -N 1
#SBATCH -n 8
#SBATCH	--job-name="CSC2_PP_TRDGIA"
##SBATCH --mail-user="trdgia001@myuct.ac.za"
##SBATCH --mail-type=ALL

DATE="`date '+%Y-%m-%d-%H-%M-%S'`"
FILE=$DATE"_Test.txt"
CORES="`python -c 'import multiprocessing as mp; print(mp.cpu_count())'`"

make all
cd bin

echo $DATE >> $FILE
echo "Logical cores: "$CORES >> $FILE
pwd >> $FILE
echo "" >> $FILE

java DriverSort System 1000000 31000000 5000000 $FILE 1.0

java DriverSort Quicksort 1000000 31000000 5000000 $FILE 1.0
java DriverSort Quicksort 1000000 31000000 5000000 $FILE 0.5
java DriverSort Quicksort 1000000 31000000 5000000 $FILE 0.25
java DriverSort Quicksort 1000000 31000000 5000000 $FILE 0.1667
java DriverSort Quicksort 1000000 31000000 5000000 $FILE 0.125
java DriverSort Quicksort 1000000 31000000 5000000 $FILE 0.0833
java DriverSort Quicksort 1000000 31000000 5000000 $FILE 0.0625
java DriverSort Quicksort 1000000 31000000 5000000 $FILE 0.04167

java DriverSort Mergesort 1000000 31000000 5000000 $FILE 1.0
java DriverSort Mergesort 1000000 31000000 5000000 $FILE 0.5
java DriverSort Mergesort 1000000 31000000 5000000 $FILE 0.25
java DriverSort Mergesort 1000000 31000000 5000000 $FILE 0.1667
java DriverSort Mergesort 1000000 31000000 5000000 $FILE 0.125
java DriverSort Mergesort 1000000 31000000 5000000 $FILE 0.0833
java DriverSort Mergesort 1000000 31000000 5000000 $FILE 0.0625
java DriverSort Mergesort 1000000 31000000 5000000 $FILE 0.04167


