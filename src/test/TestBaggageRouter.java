package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

import baggageRouter.BaggageRouter;

class TestBaggageRouter {

	@Test
	void test() throws FileNotFoundException {
		   System.out.println("calling to get input");
		   System.out.println("Current dir is "+System.getProperty("user.dir"));
	       ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	        
	       System.setOut(new PrintStream(outputStream));
	       System.out.println("calling to get input");
	        BaggageRouter.main(new String [] {"input1.txt"});
	        System.out.println("before main");
	       String output=outputStream.toString();
	      System.out.println(output);
	        assertEquals("0001 Concourse_A_Ticketing A5 A1 : 11\n" +
	                "0002 A5 A1 A2 A3 A4 : 9\n" +
	                "0003 A2 A1 : 1\n" +
	                "0004 A8 A9 A10 A5 : 6\n" +
	                "0005 A7 A8 A9 A10 A5 BaggageClaim : 12\n",output);
	 
	}
}
