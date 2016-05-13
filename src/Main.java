import java.io.IOException;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;


public class Main {
	// public static String B_celebrities = "../B/Bollywood.txt";
	// public static String P_celebrities = "../P/Politics.txt";
	// public static String S_celebrities = "../S/Sports.txt";
	
	public static String BDIR_Individual = "../B/Individual";
	public static String PDIR_Individual = "../P/Individual";
	public static String SDIR_Individual = "../S/Individual";

	// public static String BidToNameFile = "../B/idMap.properties";
	// public static String PidToNameFile = "../P/idMap.properties";
	// public static String SidToNameFile = "../S/idMap.properties";
	
	public static String mediaDomains = "../mediaDomains";
	
	public static String BfollowerMap = "../B/Bfollowers.properties";
	public static String Bfollowers = "../B/BfollowerList";
	public static String PfollowerMap = "../P/Pfollowers.properties";
	public static String Pfollowers = "../P/PfollowerList";
	public static String SfollowerMap = "../S/Sfollowers.properties";
	public static String Sfollowers = "../S/SfollowerList";
	
	// public static OutputStream output = null;

	public static void main(String[] args) throws IOException {

		// Properties BIDtoName = CreateDataStructures.getCelebrityMap(B_celebrities);
		// Properties PIDtoName = CreateDataStructures.getCelebrityMap(P_celebrities);
		// Properties SIDtoName = CreateDataStructures.getCelebrityMap(S_celebrities);

		// output = new FileOutputStream(BidToNameFile);
		// BIDtoName.store(output,null);
		// output = new FileOutputStream(PidToNameFile);
		// PIDtoName.store(output,null);
		// output = new FileOutputStream(SidToNameFile);
		// SIDtoName.store(output,null);
		// output.close();
		
		
		
		// followerMap<CelebrityName, sorted[] followers> is a global map that is passed
		// HashMap<String, long[]> followerMap = new HashMap<String, long[]>();
		// File file = new File(followerMap);
		/*
		FileWriter fw = new FileWriter(BfollowerMap);
		BufferedWriter fMap = new BufferedWriter(fw);
		FileWriter fw1 = new FileWriter(Bfollowers);
		BufferedWriter BfollowerList = new BufferedWriter(fw1);
		long[] bSortedFollowers = CreateDataStructures.getCelebrityFollowers(BDIR_Individual, fMap, BfollowerList);
		BfollowerList.close();
		fMap.close();

		FileWriter fw2 = new FileWriter(PfollowerMap);
		BufferedWriter fMap2 = new BufferedWriter(fw2);
		FileWriter fw3 = new FileWriter(Pfollowers);
		BufferedWriter PfollowerList = new BufferedWriter(fw3);
		/*long[] pSortedFollowers = CreateDataStructures.getCelebrityFollowers(PDIR_Individual, fMap2, PfollowerList);
		PfollowerList.close();
		fMap2.close();

		FileWriter fw4 = new FileWriter(SfollowerMap);
		BufferedWriter fMap3 = new BufferedWriter(fw4);
		FileWriter fw5 = new FileWriter(Sfollowers);
		BufferedWriter SfollowerList = new BufferedWriter(fw5);
		/*long[] sSortedFollowers = CreateDataStructures.getCelebrityFollowers(SDIR_Individual, fMap3, SfollowerList);
		SfollowerList.close();
		fMap3.close();
		*/
		// fMap.close();
		
		TweetsSeparator.getTweetDetail('B', "../out.txt");
		System.out.println("----------------------------------------------------------------------");
		TweetsSeparator.getTweetDetail('P', "../out.txt");
		System.out.println("----------------------------------------------------------------------");
		TweetsSeparator.getTweetDetail('S', "../out.txt");
	}
}
