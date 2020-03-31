package com.molcon.escalex.microbiology.parser;

import java.text.DecimalFormat;
import java.text.ParseException;

public class Test {

	public static void main(String[] args) {
		
		String text = "8.10";
		
		double d = Double.parseDouble(text);
		float f = Float.parseFloat(text);
		System.out.println(d);
		System.out.println(f);
		DecimalFormat df = new DecimalFormat("0.00");
		
        
		String numberAsString = "8.10";
		DecimalFormat decimalFormat = new DecimalFormat("#");
		try {
			Double totalnoofdays1final= Double.parseDouble(df.parse(text).toString()); 
	        System.out.println(totalnoofdays1final);
			
		   Double number = decimalFormat.parse(numberAsString).doubleValue();
		  // System.out.println("The number is: " + number);
		} catch (ParseException e) {
		   System.out.println(numberAsString + " is not a valid number.");
		}
	}
	
}
