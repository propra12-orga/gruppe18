package edu.propra.bomberman.gameengine.actions;



import edu.propra.bomberman.quantizer.Quantizer;

public class TimeoutAction {

	/**
	 * Author: Alex - Time by utilizing the threads with 2 different
	 * modes
	 */
	// This class file is for standard testing
	public static void main(String[] args) {

		timeout(2, 1);


	}

	private static void timeout(int i, double d) {
		// TODO Auto-generated method stub
		//Initialize the values
		Quantizer.setDelay(i);
		Quantizer.setFraction(d);
		String[] args = null;
		Quantizer.main(args);
		Quantizer.reset();
	}

}