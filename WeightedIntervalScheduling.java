import java.util.*;

public class WeightedIntervalScheduling {
	private int[][] jobs;	//array of jobs. Each job is [id, startTime, finishTime, value]
	private int[] memo;		//memoization array
	private ArrayList<Integer> includedJobs = new ArrayList<Integer>();		//holds jobs in optimal solution. The id's are id's of the sorted jobs, so must be converted to the original ID's by getJobInfo
	
	public void calcSchedule(int[][] inputJobs){
		jobs= inputJobs;
		memo = new int[jobs.length];	//create memoization array
		
		Arrays.sort(jobs, (a, b) -> Integer.compare(a[2], b[2]));	//Sort jobs by finish time
		
		memo[0]=0;		//base case with no jobs selected
		
		for(int i = 1; i<jobs.length; i++){
			int compatibleIndex = latestCompatible(i);	//find latest finishing job that's compatible with job i
			if(compatibleIndex==-1){	//If not compatible job exists, reset value to 0
				compatibleIndex=0;
				System.out.println("found compatible==-1, i="+i);
			}
			memo[i] = Math.max( jobs[i][3]+memo[compatibleIndex], memo[i-1] );		//add max value if job is included or if it's not
		}
		
		System.out.println("Memoization array: " + Arrays.toString(memo));
		System.out.println("Maximum profit from the optimal set of jobs = " + memo[memo.length-1]);
		
		findSolution(memo.length-1);
		System.out.println("\nJobs Included in optimal solution:");
		for(int i=includedJobs.size()-1; i>=0; i--){		//Loop backwards to display jobs in increasing order of their ID's
			System.out.println(getJobInfo(includedJobs.get(i)));
		}
	}
	
//BINARY SEARCH improvement waiting
	//Find the index of the job finishing before job i starts (uses jobs[][] array sorted by finish time)
	private int latestCompatible(int i){
		for (int j=i-1; j>=0; j--){
			// System.out.println("jobs[j][2] <= jobs[i][1] " + jobs[j][2] +" <= "+ jobs[i][1] +" is "+ (jobs[j][2] <= jobs[i][1]) ) ;
			if (jobs[j][2] <= jobs[i][1]){	//If j's finish time is less than or equal to i's start time
				return j;
			}
		}
		return 0;	//or return -1??
	}
	
	//Recursive method to retrace the memoization array & find optimal solution
	private void findSolution(int j){
		if(j==0){
			return;
		}
		//Simplify to just 3 cases? no nested if
		else{
			int compatibleIndex = latestCompatible(j);  //find latest finishing job that's compatible with job i
			if(compatibleIndex==-1){    //If not compatible job exists, reset value to 0
				compatibleIndex=0;
				System.out.println("FIND SOL found compatible==-1, j="+j);
			}
			
			if(jobs[j][3]+ memo[compatibleIndex] > memo[j-1]){
				includedJobs.add(j);
				findSolution(compatibleIndex);
			}
			else{
				findSolution(j-1);
			}
		}
	}
	
	private String getJobInfo(int jobIndex){
		return "Job " + jobs[jobIndex][0] + ":  Time (" + jobs[jobIndex][1] +"-" + jobs[jobIndex][2] +") Value=" + jobs[jobIndex][3];
	}


	public static void main(String args[]) {
		WeightedIntervalScheduling tester = new WeightedIntervalScheduling();
		//int[][] inputJobs = {{0,0,0}, {3, 10, 20}, {6, 19, 100}, {1, 2, 50}, {2, 100, 200}};
		//int[][] inputJobs = {{0,0,0}, {1, 2, 10}, {2, 3, 20}};
		//int[][] inputJobs = {{0,0,0}, {3, 10, 20}};
		int[][] inputJobs = {{0,0,0,0},	//dummy 0th item to make array indexes line up
							{1, 0, 6, 3},
							{2, 1, 4, 5},
							{3, 3, 5, 5},
							{4, 3, 8, 8},
							{5, 4, 7, 3},
							{6, 5, 9, 7},
							{7, 6, 10, 3},
							{8, 8, 11, 4}
							};
		tester.calcSchedule(inputJobs);
	}
}