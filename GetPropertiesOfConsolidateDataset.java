package Misc;

//Tobe Executed on Ajay Machine
//To know how many tweets has hahtags, of what topic etc  like properties

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;

public class GetPropertiesOfConsolidateDataset
{
	String GDir = "";

	public static final String Sep_file = "-<+>-";// File
	public static final String Sep_group = "\t";// Group
	public static final String Sep_record = ";";// Record

	public static final String RE_Sep_file = "\\-<\\+>\\-";// File
	public static final String RE_Sep_group = Sep_group;// Group
	public static final String RE_Sep_record = ";";// Record

	public static Set<Long> Users = new HashSet<>();
	public static Set<String> SelectedHT = new HashSet<>();
	public static TreeMap<String, Integer> TM_Topics = new TreeMap<>();

	public static String MainDir = "/home/ajayk/Important/";
	public static String IndividualTopicsTweetsDir = "/home/ajayk/Important/cdrive/Data/" + "Topics_SelectedUsers_";
	//public static String AllUserTweetsDir = "/home/ajayk/cdrive/Data/Consolidated_";//contains tweets of selected user category only

	public static long GlobalUsersCount = 26_373_330;
	public static long GlobalTweetsCount = 0;
	public static long GlobalTweetsContaingHT = 0;

	public static long AllTopicTweetsCount = 0;
	public static long AllTopicTweetsContaingHT = 0;

	public static long SelectedTopicTweetsCount = 0;
	public static long SelectedTopicWithWordsCount = 0;

	public static int Index_Tweet_id = 0;
	public static int Index_Tweet_time = 1;
	public static int Index_Tweet_isRetweet = 2;
	public static int Index_Tweet_isRetweeted = 3;
	public static int Index_Tweet_isRetweetedByMe = 4;
	public static int Index_Tweet_RetweetCount = 5;
	public static int Index_Tweet_isFavorited = 6;
	public static int Index_Tweet_FavoriteCount = 7;
	public static int Index_Tweet_isTruncated = 8;
	public static int Index_Tweet_isPossiblySensitive = 9;
	public static int Index_Tweet_User = 10;
	public static int Index_Tweet_InReplyToStatusId = 11;
	public static int Index_Tweet_InReplyToUserId = 12;
	public static int Index_Tweet_GeoLocation = 13;
	public static int Index_Tweet_HashtagEntities = 14;
	public static int Index_Tweet_URLEntities = 15;
	public static int Index_Tweet_text = 16;

	public static int Index_User_id = 0;
	public static int Index_User_isProtected = 1;
	public static int Index_User_FollowersCount = 2;
	public static int Index_User_FriendsCount = 3;
	public static int Index_User_CreatedAt = 4;
	public static int Index_User_StatusesCount = 5;
	public static int Index_User_ListedCount = 6;
	public static int Index_User_FavouritesCount = 7;
	public static int Index_User_UtcOffset = 8;
	public static int Index_User_isGeoEnabled = 9;
	public static int Index_User_isVerified = 10;
	public static int Index_User_TimeZone = 11;
	public static int Index_User_Location = 12;

	public static NumberFormat formatter = new DecimalFormat("#0.00");

	/***************************************************************
	 * MainFile
	 ***************************************************************/
	public static void main(String[] args) throws IOException
	{
		String myClassName = Thread.currentThread().getStackTrace()[1].getClassName();
		long startExecution = (new Long(System.currentTimeMillis())).longValue();

		String fName = "DsProperties.txt";
		BufferedWriter bw = new BufferedWriter(new FileWriter(fName, true));
		bw.write("\n-------------  Pgm Start Time = " + (new Date().toString()) + "  ------------\n");
		bw.close();

		// Read FileNames
		Vector<File> FileVector = new Vector<>();
		for (int i = 0; i < 5; i++)
		{
			File[] dir = new File(MainDir + i).listFiles();
			for (int j = 0; j < dir.length; j++)
			{
				FileVector.add(dir[j]);
			}
		}
		System.out.println("Count of Tweets Files = " + FileVector.size());

		String[] UserTypeArray = { "B", "P", "S" };
		for (int i = 0; i < UserTypeArray.length; i++)
		{
			Users.clear();
			SelectedHT.clear();
			TM_Topics.clear();

			GlobalTweetsCount = 0;
			GlobalTweetsContaingHT = 0;

			AllTopicTweetsCount = 0;
			AllTopicTweetsContaingHT = 0;

			SelectedTopicTweetsCount = 0;
			SelectedTopicWithWordsCount = 0;

			//Read Users who are this type of celebrities
			ReadUsers(UserTypeArray[i]);

			//Read Selected Hashtags
			ReadSelectedTopics(UserTypeArray[i]);

			//Read Tweets
			for (int j = 0; j < FileVector.size(); j++)
			{
				ReadTweets(j, FileVector.get(j), UserTypeArray[i]);
			}
			System.out.println("ConsolidateUsersDataset.main() " + new Date().toString());

			//Clear the output file
			bw = new BufferedWriter(new FileWriter(fName, true));
			bw.write("\n-------------Time = " + (new Date().toString()) + "  -------------------\n");
			bw.write("Dataset Type 				  = " + UserTypeArray[i] + "\n");

			bw.write("GlobalUsersCount			  = " + GlobalUsersCount + "\n");
			bw.write("GlovalTweetsCount 		  = " + GlobalTweetsCount + "\n");
			bw.write("GlobalTweetsContaingHT 	  = " + GlobalTweetsContaingHT + "\n");

			bw.write("DatasetUsersCount			  = " + Users.size() + "\t" + formatter.format(100.0 * Users.size() / GlobalUsersCount) + "\n");
			bw.write("DatasetTweetsCount		  = " + AllTopicTweetsCount + "\t" + formatter.format(100.0 * AllTopicTweetsCount / GlobalTweetsCount) + "\n");
			bw.write("DatasetTweetsContaingHT	  = " + AllTopicTweetsContaingHT + "\t" + formatter.format(100.0 * AllTopicTweetsContaingHT / AllTopicTweetsCount) + "\n");

			bw.write("SelectedHT.size			  = " + SelectedHT.size() + "\n");
			bw.write("SelectedTopicTweetsCount	  = " + SelectedTopicTweetsCount + "\t" + formatter.format(100.0 * SelectedTopicTweetsCount / AllTopicTweetsCount) + "\n");
			bw.write("SelectedTopicWithWordsCount = " + SelectedTopicWithWordsCount + "\t" + formatter.format(100.0 * SelectedTopicWithWordsCount / AllTopicTweetsCount)
					+ "\n");

			bw.close();
		}

		long endExecution = (new Long(System.currentTimeMillis())).longValue();
		long difference = (endExecution - startExecution) / 1000;
		System.out.println(myClassName + " finished at " + new Date().toString() + " The program has taken " + (difference / 60) + " minutes.");
	}

	/***************************************************************
	 * ReadUsers
	***************************************************************/
	private static void ReadSelectedTopics(String mUserType) throws IOException
	{
		String fName = MainDir + "CelebrityHashTags/S_SelectedHashTags_" + mUserType + ".txt";
		String readStr = "";
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fName)));
		SelectedHT.clear();
		while((readStr = br.readLine()) != null)
		{
			SelectedHT.add(readStr.split("\\t")[0]);
		}
		br.close();
		System.out.println("ConsolidateUsersDataset.ReadSelectedTopics() TopicsCount=" + SelectedHT.size());
	}

	/***************************************************************
	 * ReadUsers
	***************************************************************/
	@SuppressWarnings("boxing")
	private static void ReadUsers(String mUserType) throws IOException
	{
		String fName = MainDir + "/Data/TotalUsers" + mUserType + ".txt";
		String readStr = "";
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fName)));
		while((readStr = br.readLine()) != null)
		{
			Users.add(Long.parseLong(readStr));
		}
		br.close();
		System.out.println("ConsolidateUsersDataset.ReadUsers() UsersCount=" + Users.size());
	}

	/***************************************************************
	 * ReadTweets
	***************************************************************/
	@SuppressWarnings("boxing")
	private static void ReadTweets(int fId, File mFile, String mUserType) throws IOException
	{
		//	443807874746044416	1394647017000	0	0	0	0	0	0	0	0	455;0;786;376;1152670287000;527;18;6;-25200;1;0;Pacific Time (US & Canada);San Francisco, CA;	-1	-1	NullGeo	SoundCloud	https://soundcloud.com/letsbefriendsuk/lets-be-friends-only-time?utm_source=soundcloud&utm_campaign=share&utm_medium=twitter	Have you heard Lets Be Friends | Only Time [GlobalGathering Anthem 2014] by LetsBeFriends on #SoundCloud? https://t.co/keXZ7xJPKT	
		//	441266949780029440	1394041213000	0	0	0	0	0	0	0	0	455;0;786;376;1152670287000;527;18;6;-25200;1;0;Pacific Time (US & Canada);San Francisco, CA;	-1	-1	NullGeo	NullH	http://tech.fortune.cnn.com/2014/03/03/bonobos-allen-company/?source=yahoo_quote	http://t.co/Hy5F8Drczc	

		System.out.println("ConsolidateUsersDataset.ReadTweets() : i=" + fId + " " + mFile + "  " + mUserType + " " + new Date().toString());
		String readStr = "";
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(mFile)));

		while((readStr = br.readLine()) != null)
		{
			if (readStr.length() == 0)
				continue;

			String[] k = readStr.split(RE_Sep_file);
			String[] Args_Out = k[0].split(RE_Sep_group);
			Long Uid = Long.parseLong(Args_Out[Index_Tweet_User].split(RE_Sep_record)[0]);

			//Write Selected Topics
			Set<String> AllTags = new HashSet<>();
			Set<String> WordsTags = new HashSet<>();
			Set<String> SelectedTags = new HashSet<>();
			String[] HashTags = Args_Out[Index_Tweet_HashtagEntities].split(RE_Sep_record);
			String[] Words = Args_Out[Index_Tweet_text].toLowerCase().split("\\s+");

			for (int i = 0; i < Words.length; i++)
			{
				if (Words[i].startsWith("#"))
				{
					if (SelectedHT.contains(Words[i].substring(1)))
					{
						WordsTags.add(Words[i].substring(1));
					}
				}
				else
				{
					if (SelectedHT.contains(Words[i]))
					{
						WordsTags.add(Words[i]);
					}
				}
			}
			WordsTags.remove("nullh");
			
			for (int i = 0; i < HashTags.length; i++)
			{
				String Tag = HashTags[i].toLowerCase();
				AllTags.add(Tag);
				if (SelectedHT.contains(Tag))
				{
					SelectedTags.add(Tag);
				}
			}
			AllTags.remove("nullh");
			SelectedTags.remove("nullh");

			// Get Properties
			GlobalTweetsCount += 1;
			if (AllTags.size() > 0)
			{
				GlobalTweetsContaingHT += 1;
			}
			if (Users.contains(Uid))
			{
				AllTopicTweetsCount += 1;
				if (AllTags.size() > 0)
				{
					AllTopicTweetsContaingHT += 1;
				}
				if (SelectedTags.size() > 0)
				{
					SelectedTopicTweetsCount += 1;
				}
				if (WordsTags.size() > 0)
				{
					SelectedTopicWithWordsCount += 1;
				}
			}
		}
		br.close();
	}

}
