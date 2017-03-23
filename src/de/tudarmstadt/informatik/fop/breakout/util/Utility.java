package de.tudarmstadt.informatik.fop.breakout.util;

import java.math.BigDecimal;

public class Utility {
	    /**
	     * Checks whether a given String can be cast to an integer
	     *
	     * @param str the string to be checked
	     * @return
	     */
	    public static boolean isInteger(String str){
	    	  try
	    	  {
	    	   	@SuppressWarnings("unused")
				int i = Integer.parseInt(str);
	    	  }
	    	  catch(NumberFormatException nfe)
	    	  {
	    	    return false;
	    	  }
	    	  return true;
	    }

	    /**
	     * Rounds a float to a given number of decimal places
	     *
	     * @param f	the number to be rounded
	     * @param decimalPlace
	     * @return
	     */
		public static float round(float f, int decimalPlace) {
	        BigDecimal bd = new BigDecimal(Float.toString(f));
	        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
	        return bd.floatValue();
	    }

		/**
		 *  A basic linear Interpolation function
		 *
		 *  Example: map(0.5,0,1,0,100) = 50
		 *
		 * @param f	the input
		 * @param fromMin minimum of input-scale
		 * @param fromMax maximum of input-scale
		 * @param toMin minimum of output-scale
		 * @param toMax maximum of output-scale
		 * @return
		 */
		public static float map(float f, float fromMin, float fromMax, float toMin, float toMax) {
			return toMin + ((f - fromMin) / (fromMax - fromMin)) * (toMax - toMin);
		}
}
