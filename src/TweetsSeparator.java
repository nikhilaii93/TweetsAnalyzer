import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* 
 * Tweet Structure:
 * 
 * Tweet Fields:
 * -------------
 *  Tweet_id concatenated with media name using '+' = 0;
 *  Tweet_time = 1;
 *  Tweet_isRetweet = 2;
 *  Tweet_isRetweeted = 3;
 *  Tweet_isRetweetedByMe = 4;
 *  Tweet_RetweetCount = 5;
 *  Tweet_isFavorited = 6;
 *  Tweet_FavoriteCount = 7;
 *  Tweet_isTruncated = 8;
 *  Tweet_isPossiblySensitive = 9;
 *  Tweet_User = 10;
 *  Tweet_InReplyToStatusId = 11;
 *  Tweet_InReplyToUserId = 12;
 *  Tweet_GeoLocation = 13;
 *  Tweet_HashtagEntities = 14;
 *  Tweet_URLEntities = 15;
 *  Tweet_text = 16;
 *  
 *  User Details in Tweet:
 *  ----------------------
 *  User_id = 0;
 *  User_isProtected = 1;
 *  User_FollowersCount = 2;
 *  User_FriendsCount = 3;
 *  User_CreatedAt = 4;
 *  User_StatusesCount = 5;
 *  User_ListedCount = 6;
 *  User_FavouritesCount = 7;
 *  User_UtcOffset = 8;
 *  User_isGeoEnabled = 9;
 *  User_isVerified = 10;
 *  User_TimeZone = 11;
 *  User_Location = 12; 
 */

public class TweetsSeparator {
	public static String whitespaceReg = "\\s+";
	public static int indexIsRetweet = 2;
	public static int indexRetweetCount = 5;
	public static int indexIsFavorited = 6;
	public static int indexIsReplyToUserId = 12;
	public static int indexUser = 10;

	public static int indexUserId = 0;
	public static int indexIsVerified = 10;

	public final static int TotalUsersB = 23068931;
	public final static int TotalUsersP = 6968543;
	public final static int TotalUsersS = 9130991;

	public static long[] sortedUsers;
	public static Properties prop;
	public static Properties propMap;
	public static ArrayList<String> domains;

	public static void LoadDataStructures(char field) throws IOException {
		File file = new File("../" + field + "/TotalUsers" + field + ".txt");

		if (field == 'B') {
			sortedUsers = new long[TotalUsersB];
		} else if (field == 'P') {
			sortedUsers = new long[TotalUsersP];
		} else if (field == 'S') {
			sortedUsers = new long[TotalUsersS];
		} else {
			System.out.println("Chutiya h kya!");
		}

		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			String line;
			int i = 0;
			while ((line = br.readLine()) != null) {
				try {
					sortedUsers[i++] = Long.parseLong(line);
				} catch (NumberFormatException e) {
					continue;
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("File Not found!");
			e.printStackTrace();
		}

		System.out.println("kuch toh hua h load");

		prop = new Properties();
		prop.load(new FileInputStream("../" + field + "/idMap.properties"));
		
		System.out.println("map toh ho gaya");

		domains = CreateDataStructures.getMediaDomains("../mediaDomains");

		System.out.println("domains bhi ho gaya");

		propMap = new Properties();
		propMap.load(new FileInputStream("../" + field + "/" + field
				+ "followers.properties"));

		System.out.println("sab ho gaya");
	}

	public static boolean isCelebrity(String userId) {
		if (prop.containsKey(userId)) {
			//System.out.println(prop.getProperty(userId));
			return true;
		}
		return false;
	}

	public static boolean isFollower(String follower, String celeb) {
		String celebName = prop.getProperty(celeb);
		// Bug in names resolved
		String celebFollowers = propMap.getProperty(celebName.substring(0,
				celebName.length() - 1));

		if (celebFollowers.contains(',' + follower + ',')) {
			return true;
		}
		if (celebFollowers.indexOf(follower + ',') == 0) {
			return true;
		}
		return false;
	}

	public static HashMap<String, int[]> getTweetDetail(char field,
			String pathToFile) throws IOException {
		LoadDataStructures(field);
		System.out.println("We are loaded");

		HashMap<String, int[]> statistics = new HashMap<String, int[]>();

		File file = new File(pathToFile);
		BufferedReader br;
		String[] tokenized = null;
		String line = "";
		int rcrt = 0;
		int rct = 0;
		try {
			br = new BufferedReader(new FileReader(file));
			int lnum = 0;
			int ll = 0;
			int nr = 0;
			while ((line = br.readLine()) != null) {
				// Find if it's a retweet or not
				//System.out.println(lnum++);
				
				//Pattern pattern = Pattern.compile("\\-<\\+>\\-");
				//Matcher matcher = pattern.matcher(line);
				if (/*matcher.find()*/line.contains("-<+>-")) {
					String retweet = line.substring(0, line.indexOf("-<+>-")).trim();
					String originalTweet = line
							.substring(line.indexOf("-<+>-") + 5).trim();
					
					//System.out.println(retweet);
					//System.out.println(originalTweet);
					
					String[] retweetTokenized = retweet.split("\t");
					String[] originalTweetTokenized = originalTweet.split("\t");
					
					String retweetUser;
					String originalUser;
					try {
						retweetUser = retweetTokenized[10].split(";")[0].trim();
						originalUser = originalTweetTokenized[10].split(";")[0].trim();
						
						if (Long.parseLong(retweetTokenized[12]) > 0) {
							rcrt++;
						}
						
					} catch (ArrayIndexOutOfBoundsException e) {
						System.out.println(ll++);
						continue;
					}
					String mediaDomain = retweetTokenized[0].split("\\+")[0];

					int[] values = new int[4];
					values[0] = isCelebrity(retweetUser) ? 1 : 0;
					values[1] = (!isCelebrity(retweetUser)
							&& isCelebrity(originalUser) && isFollower(
							retweetUser, originalUser)) ? 1 : 0;
					values[2] = ((!isCelebrity(retweetUser) && !isCelebrity(originalUser)) || (!isCelebrity(retweetUser)
							&& isCelebrity(originalUser) && !isFollower(
							retweetUser, originalUser))) ? 1 : 0;
					values[3] = 0;

					if (statistics.containsKey(mediaDomain)) {
						int originalArray[] = statistics.get(mediaDomain);
						for (int i = 0; i < 4; i++) {
							values[i] += originalArray[i];
						}
					}
					statistics.put(mediaDomain, values);
				} else {
					String[] tweetTokenized = line.split("\t");
					String tweetUser;
					boolean isRetweet = false;
					try {
						tweetUser = tweetTokenized[10].split(";")[0];
						try {
							if (Long.parseLong(tweetTokenized[12]) > 0) {
								isRetweet = true;
								rct++;
							}
						} catch (NumberFormatException e) {
							
						}
						
					} catch (ArrayIndexOutOfBoundsException e) {
						continue;
					}
					
					String mediaDomain = tweetTokenized[0].split("\\+")[0];
					int[] values = new int[4];
					if (isRetweet) {
						String originalUser = tweetTokenized[12];
						values[0] = isCelebrity(tweetUser) ? 1 : 0;
						values[1] = (!isCelebrity(tweetUser)
								&& isCelebrity(originalUser) && isFollower(
								tweetUser, originalUser)) ? 1 : 0;
						values[2] = ((!isCelebrity(tweetUser) && !isCelebrity(originalUser)) || (!isCelebrity(tweetUser)
								&& isCelebrity(originalUser) && !isFollower(
								tweetUser, originalUser))) ? 1 : 0;
						values[3] = 0;
					} else {
						values[0] = isCelebrity(tweetUser) ? 1 : 0;
						values[1] = 0;
						values[2] = 0;
						values[3] = !isCelebrity(tweetUser) ? 1 : 0;
					}

					if (statistics.containsKey(mediaDomain)) {
						int originalArray[] = statistics.get(mediaDomain);
						for (int i = 0; i < 4; i++) {
							values[i] += originalArray[i];
						}
					}
					statistics.put(mediaDomain, values);
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("Tweets File " + file.getName() + " Not found!");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(line);
			e.printStackTrace();
		}
		System.out.println("Stats " + rcrt + " : " + rct);
		for (String name: statistics.keySet()){
            String key =name.toString();
            int[] value = statistics.get(name);  
            System.out.println(key + "," + value[0] + "," + value[1] + "," + value[2] + "," + value[3]);  
		} 
		return statistics;
	}
}
