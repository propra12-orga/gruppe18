package edu.propra.bomberman.quantizer;

public class Timeout {

	/**
	 * Author: Alex - Time by utilizing the threads with 2 different
	 * modes
	 */
	// This class file is for standard testing
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Add your events here by timeout(time, fraction)
		// some examples:
		timeout(2000, 0.001);
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
