import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.NumberFormatException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.Properties;

public class CreateDataStructures {
	public static String whitespaceReg = "\\s+";

	public static Properties getCelebrityMap(String fileName) throws IOException {
		File file = new File(fileName);
		BufferedReader br;
	//	HashMap<String, String> idToName = null;
		Properties idToName = null;
		try {
			br = new BufferedReader(new FileReader(file));
		//	idToName = new HashMap<String, String>();
			idToName = new Properties();
			// Skip first line which is the heading.
			String line = br.readLine();
			while ((line = br.readLine()) != null) {
				String[] tokenized = line.split(whitespaceReg);
			//	idToName.put(tokenized[1], tokenized[0]);
				idToName.setProperty(tokenized[1], tokenized[0]);
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println(fileName + " Not found!");
			e.printStackTrace();
		}
		return idToName;
	}

	// Returns the sorted[] of all followers in a section B/P/S
	// followerMap<CelebrityName, sorted[] followers> is a global map that is
	// passed
	public static void getCelebrityFollowers(String folderPath, BufferedWriter fMap, BufferedWriter followerList)
			throws IOException {
		// long[] allSortedFollowers = null;
		// TreeSet<Long> allFollowers = new TreeSet<Long>();
		
		File dir = new File(folderPath);
		File[] directoryListing = dir.listFiles();
		if (directoryListing != null) {
			for (File file : directoryListing) {
				String fName = file.getName();
				System.out.println(fName);
				// AllFiles are of type FollowersOf_NAME.txt
				String celebrityName = fName.substring(fName.indexOf('_') + 1, fName.indexOf('.') - 1);	
				fMap.write("\n"+celebrityName+"=");

				// TreeSet<Long> someFollowers = new TreeSet<Long>();
				// BufferedReader br;
				// String line;
				// try {
				// 	br = new BufferedReader(new FileReader(file));
				// 	line = br.readLine();

				// 	StringTokenizer tokenizer = new StringTokenizer(line,",");
				// 	while (tokenizer.hasMoreTokens()) {
				// 		long followerId = Long.parseLong(tokenizer.nextToken().toString()); 
				// 		// System.out.print(followerId+" ");
				// 		allFollowers.add(followerId);
				// 		someFollowers.add(followerId);
				// 	}
				// 	br.close();
				// } catch (FileNotFoundException e) {
				// 	System.out.println("File Not Found In Directory!");
				// 	e.printStackTrace();
				// }
				Scanner scan = new Scanner(new FileReader(file)).useDelimiter(",");
				Long followerId;
				while(scan.hasNext()) {
					try {
						followerId = Long.parseLong(scan.next().toString());
						fMap.write(followerId.toString()+",");
						followerList.write(followerId.toString()+",");
						// allFollowers.add(followerId);
						// someFollowers.add(followerId);	
					} catch(NumberFormatException e) {
						System.out.println();
						continue;
					}
					
				}
				scan.close();

				// long[] someSortedFollowers = new long[someFollowers.size()];
				// Iterator<Long> it = someFollowers.iterator();
				// int i = 0;
				// while (it.hasNext()) {
				// 	someSortedFollowers[i++] = it.next();
				// }
				// String fName = file.getName();
				// // AllFiles are of type FollowersOf_NAME.txt
				// String celebrityName = fName.substring(fName.indexOf('_') + 1, fName.indexOf('.') - 1);
				// followerMap.put(celebrityName, someSortedFollowers);
			}
			// allSortedFollowers = new long[allFollowers.size()];
			// Iterator<Long> it = allFollowers.iterator();
			// int i = 0;
			// while (it.hasNext()) {
			// 	allSortedFollowers[i++] = it.next();
			// }
		} else {
			System.out.println("Given path is not a directory!");
		}
		// return allSortedFollowers;
	}

	public static ArrayList<String> getMediaDomains(String mediaDomains) throws IOException {
		File file = new File(mediaDomains);
		BufferedReader br;
		ArrayList<String> domains = new ArrayList<String>();
		try {
			br = new BufferedReader(new FileReader(file));

			String line;
			while ((line = br.readLine()) != null) {
				domains.add(line);
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println(mediaDomains + " Not found!");
			e.printStackTrace();
		}
		return domains;
	}
}
