package application;

public class world {
	/*
	 i and j are a variable of formation and test, they are not really important for the project 
	
	*/
	
	
	public static String [][] worldPos(int len) {
		
		String world [][]= new String [len][len];

		for (int i=0; i<world.length-1; i++ ) {
		
			for(int j=0; i<world.length-1; j++) {
				
				if(i%2==0 && j%2==0) {
					
				world[i][j]="pp";
			}
			
				else {
					world[i][j]="wp";
				}
		}
			
		
			
	}

		return world;
	}
	
}
