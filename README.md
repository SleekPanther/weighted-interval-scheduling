# Weighted Interval Scheduling
The classic introduction to Dynamic Programming

## Problem Statement
Jobs have a start time, a finish time & a value (or weight) associated with it
**Compatible** jobs don't overlap (a job cannot start before another job finishes)  
**Find the maximum weight subset of mutually compatible jobs.**

## Input Jobs
![](images/input-jobs.png)  
job _ is compatible with _

## Pseudocode
![](images/optimal-pseudocode.png)  
![](images/find-solution-pseudocode.png)


## Runtime
**O(n log(n) )** from sorting jobs by finish time and binary search in `latestCompatible()`  
Binary Search is O(log n) and there are **n** jobs, so **O(n log(n) )**

## Solution (Jobs Selected in Optimal Subset)
![](images/optimal-solution.png)

## Usage
- A 2D array of `inputJobs[][]` created in `main()`
- Each `inputJobs[i]` is an array of 4 numbers  
**[ID, startTime, finishTime, value]**
- `inputJobs[0]` must be a placeholder array of four 0's: `{0,0,0,0}`

## Code Details
- 2D `jobs[]` array is sorted by finish time so the ID's of jobs are no longer the array indexes  
`findSolution()` uses `jobs[jobIndex][0]` to access the `0th` number in the actual job to get the original ID
- This allows the rest of the code to just use array indexes in `jobs` when finding the optimal value

## References
- [Design and Analysis of Algorithms I - Larry Ruzzo, University of Washington](https://courses.cs.washington.edu/courses/cse521/13wi/slides/06dp-sched.pdf)
- [GeeksForGeeks Weighted Job Scheduling](http://www.geeksforgeeks.org/weighted-job-scheduling/)
- [GeeksForGeeks Weighted Job Scheduling in O(n Log n)](http://www.geeksforgeeks.org/weighted-job-scheduling-log-n-time/)
