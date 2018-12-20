import java.util.*;

public class mainFile {
	
	public static void main(String args[]){
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter number of processes: ");
		int n = sc.nextInt();
		int bt[] = new int[n];
		int wt[] = new int[n];
		int rem_bt[] = new int[n];
		int t = 0;
		int quantum = 2;
		
		//Input burst time
		for(int i = 0;i < n;i ++){
			System.out.println("Process" + i + " enter burst time: ");
			bt[i] = sc.nextInt();
		}
		
		// Fill rem_bt with bt
		for(int i = 0;i < n;i ++){
			rem_bt[i] = bt[i];
		}
		
		//Loop until all the processes are not finished
		while(true){
			
			boolean done = true;
			
			for(int i = 0;i < n;i ++){
				
				if(rem_bt[i] > 0){
					done = false;
					
					if(rem_bt[i] > quantum){
						
						t += quantum;
						rem_bt[i] -= quantum;
						
					}
					else{
						
						t += rem_bt[i];
						wt[i] = t - bt[i];
						rem_bt[i] = 0;
					}
				}
				
			}
			if(done == true){
				break;	//All process are finished
			}
		}
		
		System.out.println("Pid    Burst Time   Waiting time");
		for(int i = 0;i < n; i++){
			System.out.println(i + " \t " + bt[i] + " \t\t " + wt[i]);
		}
		
	}

}
