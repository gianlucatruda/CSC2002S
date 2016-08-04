#!/bin/bash
# Gianluca Truda
# 4 August 2016

DATE="`date '+%Y-%m-%d-%H-%M-%S'`"
FILE=$DATE"_Test.txt"
CORES="`python -c 'import multiprocessing as mp; print(mp.cpu_count())'`"

make all
cd bin

echo $DATE >> $FILE
echo "Logical cores: "$CORES >> $FILE
echo "" >> $FILE

COUNTER=0
while [  $COUNTER -lt 5 ]; do
	java DriverSort System 10 20000010 1000000 $FILE 1.0
	let COUNTER=COUNTER+1 
done

COUNTER=0
while [  $COUNTER -lt 5 ]; do
	java DriverSort Mergesort 10 20000010 1000000 $FILE 1.0
	let COUNTER=COUNTER+1 
done

COUNTER=0
while [  $COUNTER -lt 5 ]; do
	java DriverSort Mergesort 10 20000010 1000000 $FILE 0.5
	let COUNTER=COUNTER+1 
done

COUNTER=0
while [  $COUNTER -lt 5 ]; do
	java DriverSort Mergesort 10 20000010 1000000 $FILE 0.25
	let COUNTER=COUNTER+1 
done

COUNTER=0
while [  $COUNTER -lt 5 ]; do
	java DriverSort Mergesort 10 20000010 1000000 $FILE 0.125
	let COUNTER=COUNTER+1 
done

COUNTER=0
while [  $COUNTER -lt 5 ]; do
	java DriverSort Quicksort 10 20000010 1000000 $FILE 1.0
	let COUNTER=COUNTER+1 
done

COUNTER=0
while [  $COUNTER -lt 5 ]; do
	java DriverSort Quicksort 10 20000010 1000000 $FILE 0.5
	let COUNTER=COUNTER+1 
done

COUNTER=0
while [  $COUNTER -lt 5 ]; do
	java DriverSort Quicksort 10 20000010 1000000 $FILE 0.25
	let COUNTER=COUNTER+1 
done

COUNTER=0
while [  $COUNTER -lt 5 ]; do
	java DriverSort Quicksort 10 20000010 1000000 $FILE 0.125
	let COUNTER=COUNTER+1 
done