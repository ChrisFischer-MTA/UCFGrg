package io.github.chrisfischerdashmta;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.time.Instant;
import java.util.Scanner;

/*
 * Independant Thread to autonomously grab 
 * data and store it in a string.
 */


public class RetrieveData extends Thread {
	public final String access = "http://secure.parking.ucf.edu/GarageCount/iframe.aspx";
	public String output = "";
	public boolean ready = false;
	public int[] percentages = new int[7];
	public void run()
	{
		URLConnection retrieve;
		BufferedReader in;
		String input;
		int i = 0;
		try {
			retrieve = (new URL(access).openConnection());
			in = new BufferedReader(new InputStreamReader(retrieve.getInputStream()));
			while((input=in.readLine())!=null)
			{
				output+=input+"\n";
				if(input.contains("percent: "))
				{
					// TODO: Implement error checking to ensure the packet is recieved
					// and decoded in the correct order, however Java should handle this
					// for us!
					input = input.replace(',', ' ').replaceAll("percent: ", "");
					// I could use trim, but I just want to kill all spaces dead.
					percentages[i] = Integer.parseInt(input.replaceAll(" ", ""));
					i++;
				}
			}
			in.close();
			ready = true;
			writeValues(percentages);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	public static void writeValues(int[] values) throws IOException
	{
		
		BufferedWriter buff = new BufferedWriter(new FileWriter("output.bin", true));
		String out = "";
		out += Instant.now();
		for(int i : values)
		{
			out+=(i+",");
		}
		buff.write(out+"\r\n");
		buff.close();
		System.out.println("WARNING: Request Written");
	}
}
