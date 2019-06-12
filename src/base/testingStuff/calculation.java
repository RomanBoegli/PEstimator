package base.testingStuff;

import base.app.Cheater;

public class calculation {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		
//		System.out.println(cheat.getPi().toString());
		for ( int z = 1 ; z<2000 ; z++) {
			Cheater cheat = Cheater.getInstance();
			cheat.approximatePi();
			System.out.println(cheat.getPi().toString());
		}
		
//		System.out.println(cheat.getPi().toString());
		
	}

	
	
	
	
}
