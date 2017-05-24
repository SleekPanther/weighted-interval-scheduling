import java.util.*;

public class WeightedIntervalScheduling {
	private int[][] jobs;
	private int[] memo;
	private ArrayList<Integer> includedJobs = new ArrayList<Integer>();
	
	public void calcSchedule(int[][] inputJobs){
		jobs= inputJobs;
		memo = new int[jobs.length];	//create memoization array
		
		Arrays.sort(jobs, (a, b) -> Integer.compare(a[1], b[1]));	//Sort jobs by finish time
		
		memo[0]=0;		//base case with no jobs selected
		
		for(int i = 1; i<jobs.length; i++){
			int compatibleIndex = latestCompatible(i);	//find latest finishing job that's compatible with job i
			if(compatibleIndex==-1){	//If not compatible job exists, reset value to 0
				compatibleIndex=0;
				System.out.println("found compatible==-1, i="+i);
			}
			memo[i] = Math.max( jobs[i][2]+memo[compatibleIndex],  memo[i-1] );		//add max value if job is included or if it's not
		}
		
		System.out.println("Memoization array: " + Arrays.toString(memo));
		System.out.println("Maximum profit from the optimal set of jobs = " + memo[memo.length-1]);
		
		findSolution(memo.length-1);
		System.out.println("All included jobs as a list:\n" +includedJobs);
		for(int jobIndex : includedJobs){
			System.out.println(getJobInfo(jobIndex));
		}

		System.out.println();
		for(int i=0; i<jobs.length; i++){
			System.out.println(getJobInfo(i));
		}
	}
	
	private int latestCompatible(int i){
		for (int j=i-1; j>=0; j--){
			System.out.println("jobs[j][1] <= jobs[i][0] " + jobs[j][1] +" <= "+ jobs[i][0] +" is "+ (jobs[j][1] <= jobs[i][0]) ) ;
			if (jobs[j][1] <= jobs[i][0]){  //If j's finish time is less than or equal to i's start time
				return j;
			}
		}
		return 0;	//or return -1??
	}
	
	private void findSolution(int j){
		System.out.println(j);
		if(j==0){
			System.out.println("base case");
			return;
		}
		else{
			int compatibleIndex = latestCompatible(j);  //find latest finishing job that's compatible with job i
			if(compatibleIndex==-1){    //If not compatible job exists, reset value to 0
				compatibleIndex=0;
				System.out.println("FIND SOL found compatible==-1, j="+j);
			}
			//System.out.println(memo[j-1]);    //fails
			if(jobs[j][2]+ memo[compatibleIndex] > memo[j-1]){
				System.out.println("Included Job " + j);
				includedJobs.add(j);
				findSolution(compatibleIndex);
			}
			else{
				findSolution(j-1);
			}
		}
	}
	
	private String getJobInfo(int jobIndex){
		return "Job " + jobIndex + ": (" + jobs[jobIndex][0] +"-" + jobs[jobIndex][1] +") value=" + jobs[jobIndex][2];
	}


	public static void main(String args[]) {
		WeightedIntervalScheduling tester = new WeightedIntervalScheduling();
		//int[][] inputJobs = {{0,0,0}, {3, 10, 20}, {6, 19, 100}, {1, 2, 50}, {2, 100, 200}};
		//int[][] inputJobs = {{0,0,0}, {1, 2, 10}, {2, 3, 20}};
		//int[][] inputJobs = {{0,0,0}, {3, 10, 20}};
		int[][] inputJobs = {{0,0,0},	//dummy 0th item to make array indexes line up
							{0, 6, 3},
							{1, 4, 5},
							{3, 5, 5},
							{3, 8, 8},
							{4, 7, 3},
							{5, 9, 7},
							{6, 10, 3},
							{8, 11, 4}
							};
		tester.calcSchedule(inputJobs);
	}
}