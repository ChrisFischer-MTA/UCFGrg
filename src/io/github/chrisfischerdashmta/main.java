package io.github.chrisfischerdashmta;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.Instant;
import java.util.ArrayList;

public class main {
	public static final int MILLS_TO_REFRESH = 60*3*1000; // refresh every 3 minutes;
	public static void main(String[] args) throws InterruptedException
	{
		// Alright, alright, alright. Can I use event driven programming? Yes.
		// But, why overcomplicate this.
		// also, apparently just just putting true causes JVM to "optimize" this. :(
		while(!Instant.now().equals(new Object()))
		{
			RetrieveData d = new RetrieveData();
			d.start();
			System.out.println("WARNING: Request logged");
			Thread.sleep(MILLS_TO_REFRESH);
		}
	}
	

	
	
}
