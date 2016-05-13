package Crawl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Vector;

import twitter4j.ResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

public class GetUserDetails
{
	//-------------------------------------------------
	public final static String CrawlDir = "G:/MyWork/Data/";
	public final static String CredDir = "G:/MyWork/Credential/";

	public static int CredentialUser = 1;
	public static int CurrentFile = 0;
	public static int StartingUserIndex = 0;
	public static int gIndex = 0;

	//-------------------------------------------------

	public static int waitSeconds = 0;
	public static boolean UsersInfoCrawled = false;
	public static long[] UsersList = new long[100];

	//Delimiters
	public static final String Sep_file = "x\\|x";//File
	public static final String Sep_group = "\t";//Group
	public static final String Sep_record = ",";//Record

	public static Twitter twitter = null;
	//public static BufferedReader br_ids = null;
	public static int gStartingIndex = StartingUserIndex;
	public static int TotalApiCount = 0;
	public static int CurrentApiCount = 0;

	public static final long DS_Tweet_StartID = 414_642_368_885_694_464l;
	public static final long DS_Tweet_EndID = 448_825_794_547_052_544l;

	//300,500,700
	//Started from 1000
	
	/***************************************************************
	 * MainFile
	***************************************************************/

	public static void main(String[] args) throws InterruptedException, IOException, NumberFormatException
	{
		String myClassName = Thread.currentThread().getStackTrace()[1].getClassName();
		long startExecution = (new Long(System.currentTimeMillis())).longValue();
		System.out.println(myClassName + "\n==========================");
		System.out.println(myClassName + " Starting at " + new Date(startExecution).toString());

		Initialize();
		String st = null;
		BufferedReader br_i = new BufferedReader(new InputStreamReader(new FileInputStream(CrawlDir+"Celeb_Ids_2.txt")));
		Vector<Long> vec = new Vector<>();
		while((st = br_i.readLine()) != null)
		{
			if (st.length() < 1)
				continue;
			vec.add(Long.valueOf(st));
		}
		br_i.close();
		
		
//		String st = "2360667266,2367372768,26746454,48901760,58624099,89172076,93343038,127156675,131065414,146825026,234893567,249118251,307760337,309196142,319246523,2166502579,17331252,18383003,33047261,39787488,45347631,58310091,62023151,94579740,105172993,116387564,128640668,205362973,231419239,237004016,243642683,289380346,318034162,445628684,756688452,773729666,825083712,835393704,995136638,1275637886,1878375445,1565045454,2203089006,2348719608,14799078,16654695,17961439,125990707,157311621,160850217,214527521,227667917,299125549,392373734,1956608154,2177376512,2354336924,15317449,50845837,59268218,77422475,104440904,133456786,204799045,255346177,273135707,291004848,306752043,341709707,424432540,573326950,576286964,577145837,624701530,943273266,980658667,1443330241,1450680812,1690176776,1851932641,1930690590,2164649351,2170395669,2200655873,2289314797,2366427763,14242467,23204294,58818835,77460037,78638371,97376439,162401558,174152830,241526259,280050198,381012797,205362973,237004016,243642683,289380346,318034162,542346548,804836382,890931696,241526259,280050198,381012797,514475189,550108703,574432678,585879931,598226451,927981678,413382002,448975691,461514893,531824070,915452041,923817468,1064491280,1699272793,2151240440,2153815837,2374266470,1193590092,1193590092,1162520036,11881852,24108326,48625883,72023944,75480736,78391411,127056518,177656139,189576159,200006266,216390136,305936897,318034809,322006249,370612813,462364793,466127765,467573292,595429407,732904921,837453402,883543944,1014455918,1014825978,23316248,94336318,271705508,398947970,401170807,433807163,540237208,551974341,581776853,596122440,596731157,806140856,819193885,872059387,1592185993,1658258594,2150471226,2181806556,2182593386,2316423877,1226376613,1307937404,1020384775,2260110841,2365713457,54203076,107875992,117176778,136209925,148815716,199266290,223374769,280461953,309790102,342574697,370603794,457445847,555138169,1944596953,46130377,80431205,125849704,207576292,290081093,303498429,319406387,431684378,434904112,443269252,460902635,1048243106,17926558,64884492,104916355,109676671,154096999,202624379,266997795,267118405,283905720,394558572,437675263,479434849,551712769,604732070,2355812190,7733902,17329538,20656259,24137848,61167273,65280939,73045636,84677083,94952691,104446139,111534406,116300716,127424288,143841469,171266293,176797592,190233961,202110494,245079337,283722319,297692294,449601946,583840118,618858167,761789492,881482826,920169361,1015250264,1337335620,2151082555,2198164898,2303307937,2367350107,2371280736,18327213,24280718,29923080,45094675,47060812,78225692,121994782,304762447,314668357,375058568,412749784,512873917,628766583,850626451,948992130,1055714221,1682539970,2210849250,2245872727,2362333476,2362774202,2365460990,1286480521,1680007202,1930619004,1594201,9158962,19351622,21959229,29844084,121045694,124243431,139632292,243656907,396326527,416975157,498186839,557183169,585957002,768427478,1103067246,1119263072,1363923216,1495818782,1930763964,1960134596,2206033819,2270088377,2277797250,2365662470,14310025,16313095,17647762,29125636,30829203,53432142,65272739,80840852,100533307,126872475,133317483,138845000,155219261,164510174,247784303,317017088,340822505,341173297,382730950,527286164,532312933,633654736,1591265588,1655298858,1721143542,1934299364,2195466937,2262429037,2262452594,2214242727,2276147101,2339369293,18359715,21367580,34667257,40245758,42273702,50872293,56389527,62501468,66936924,81995501,83305166,96345425,99619383,106025116,115534914,116951006,135399738,141574104,147415600,215673600,244179808,266488013,292814594,307426513,319976099,495298917,497178443,755130540,769781155,1667936923,2242019323,2367326916,2367397034,2374283100,1905937202,18238260,27941688,30097803,46548706,49148052,72391395,75699774,91753788,102611167,105498404,109699775,125565535,125943621,140975955,151817661,183732915,204764855,240653139,273292178,299035269,370100043,398045704,400108289,402511377,428492590,533322019,624511287,890984287,963490788,1375921326,1425909596,1965063061,1970223210,2209937064,2276093438,2343553850,1373301456,1408420904,1427157684,1664007110,2227756772,2362758433,2370925855,22125118,23061619,23535150,57780882,80380581,105480392,111569532,153007436,240574593,334576301,389679854,480039493,495738865,512092106,517316963,529715946,529732380,557032188,564482664,619267395,802020577,900282534,957862284,1246576580,1456262251,1477118347,1509989940,1680089858,1871976505,2150516708,2253232512,2367678552,2370690025,2370992449,19929890,22627536,25072674,26267634,65085651,186685931,199587536,332467240,345862671,434221993,495746794,564266546,635945002,902594070,924650173,973338595,1122434444,1158098059,1539560863,1818241345,2194624002,2261002856,2277802344,2362959342,2367411492,2368612212,2231156317,2365552806,18493926,30985586,41282669,50399502,50588723,75998366,96825626,105979804,150177856,174141936,225255264,239112388,275721184,279264123,354473638,370594245,443046171,501018943,528648202,601230335,616000350,795428184,956699930,1244741263,1391929873,1421106367,1672790558,1936826564,1947093109,2262283118,1375584342,1417074374,1663865216,15009355,15902145,26680035,29882692,61775378,67813350,94385910,115985414,122743868,242675504,252942171,253567506,488391960,549512748,589563469,632100734,2149823522,2224338870,2247472272,2250561858,2297402348,2362755812,2367666156,1246382317,1679812176,2175717786,1546552560,2203449761,2307843066,2310179366,1277320710,1596937171,2161656819,2367421236,48843127,103232529,107938796,115553337,117863547,129003599,130763223,149852266,194969127,223214336,261596058,265607778,268666861,379818841,397641596,404072096,450296738,459506223,479432348,511976184,1320897439,1622466120,1642699795,1682450425,1714805762,1734874112,1905062054,2177924672,2183456799,2191800494,2276040818,2328467298,2351579046,2364086659,2367656790,19596023,21555509,62308704,74136478,88293088,108856065,139753547,231358554,239373100,268228998,299237449,328773151,383554469,446863797,472412288,492715034,543106139,557025426,607198429,636948790,719931613,812501389,871698522,989428482,1003479180,1092392208,1114349886,1284672925,1317895812,1629673561,1644500192,1679651803,1904271668,1975697202,2279019107,2363816190,15707222,39233819,60191559,61404647,77757297,86479000,106852915,198560057,245771492,332061383,339804366,406797259,467489510,485434264,499105233,529733036,588942466,708354720,755316223,808667636,853194722,860162616,910013089,920953074,1407204308,1417734120,1588809120,1680428034,2197861839,2236356722,1240314464,1715532470,2265810639,48216638,83676899,84812252,117857151,198686229,245157830,292257830,350223763,429674902,453749395,471008465,529700753,540134679,547973288,842205380,1205462400,1588371668,1646923831,1676660863,1707038515,1834846921,1882660892,1903093628,2171956021,2251645183,2269041654,2328775872,2366401014,1494908042,1886466962,50460475,59189497,72264840,97867609,110632722,266576938,342251508,371887501,382811332,471304547,861326131,924688638,1063940659,1439915004,1892719128,2255190895,2281644126,";
//		String[] arr = st.split(",");
//		System.out.println("Users=" + arr.length);

		while(true)
		{
			int readCount = ReadUserIds(vec);
			if (readCount > 0)
			{
				GetUsersInformation();
				Thread.sleep(5*1000);
			}
			else
				break;
		}

		long endExecution = (new Long(System.currentTimeMillis())).longValue();
		long difference = (endExecution - startExecution) / 1000;
		System.out.println(myClassName + " finished at " + new Date(endExecution).toString() + " The program has taken " + (difference / 60) + " minutes.");

	}

	/***************************************************************
	 * Initialize 
	***************************************************************/
	private static void Initialize() throws IOException
	{
		String lFileName = CredDir + CredentialUser + ".txt";
		System.out.println("CredentialFile=" + lFileName);
		BufferedReader br_i = new BufferedReader(new InputStreamReader(new FileInputStream(lFileName)));
		br_i.readLine();// Skip First Line
		String key = br_i.readLine();
		String secret = br_i.readLine();
		String token = br_i.readLine();
		String tokenSecret = br_i.readLine();
		br_i.close();

		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true);

		twitter = new TwitterFactory(cb.build()).getInstance();
		twitter.setOAuthConsumer(key, secret);
		twitter.setOAuthAccessToken(new AccessToken(token, tokenSecret));

	}

	/***************************************************************
	 * Verify if users have been Crawled 
	***************************************************************/
	private static int ReadUserIds(Vector<Long> vec)
	{
		//Initialize
		for (int i = 0; i < UsersList.length; i++)
		{
			UsersList[i] = 0l;
		}

		//Read Users

		if (gIndex >= vec.size())
			return 0;

		int readCount = 0;
		int uIndex = 0;
		for (int i = gIndex; i < vec.size(); i++)
		{
			long id = vec.get(i).longValue();
			UsersList[uIndex++] = id;
			readCount++;
			if (readCount >= 100)
			{
				break;
			}
		}
		gIndex += readCount;
		System.out.println("ReadCount=" + uIndex + " gIndex=" + gIndex + ",  Userid[" + gStartingIndex + "] =" + UsersList[0]);
		return uIndex;
	}

	/***************************************************************
	 * Verify if users have been Crawled
	 * @throws InterruptedException 
	***************************************************************/
	private static void GetUsersInformation() throws NumberFormatException, IOException, InterruptedException
	{
		myPrint();
		ResponseList<User> usersRes = null;

		while(true)
		{
			try
			{
				Thread.sleep(100);
				usersRes = twitter.lookupUsers(UsersList);
				CurrentApiCount++;
				TotalApiCount++;
				break;
			}
			catch (TwitterException e)
			{
				e.printStackTrace();
				int waitSec = 0;
				if (e.getRateLimitStatus() != null)
				{
					if (e.getStatusCode() == 404)
					{
						break;
					}
					if (e.getRateLimitStatus().getRemaining() > 0)
					{
						waitSec = 30;
					}
					else
					{
						waitSec = e.getRateLimitStatus().getSecondsUntilReset();
					}
				}
				if (waitSec < 0)
					waitSec = 60;
				System.out.println("waitSec=" + waitSec + "  at " + (new Date(System.currentTimeMillis())).toString() + " when CurrentApiCount=" + CurrentApiCount
						+ " TotalApiCount=" + TotalApiCount);
				Thread.sleep(waitSec * 1000);// Sleep 60 sec when Twitter service or network is unavailable	
				CurrentApiCount = 0;
			}
		}

		if (usersRes != null)
		{
			BufferedWriter bw = new BufferedWriter(new FileWriter(CrawlDir + "InfoAboutIds_" + CurrentFile + ".txt", true));
			for (User u : usersRes)
			{
				bw.write(GetUser(u) + "\n");
			}
			bw.close();
		}
		else
		{
			BufferedWriter bw = new BufferedWriter(new FileWriter(CrawlDir + "ErrorAboutIds_" + CurrentFile + ".txt", true));
			bw.write(myPrint() + "\n");
			bw.close();
		}
	}

	/***************************************************************
	 * WriteToStatusFile
	 * @param mApicount 
	***************************************************************/
	private static String myPrint()
	{
		StringBuffer stBuf = new StringBuffer();
		for (int i = 0; i < UsersList.length; i++)
		{
			stBuf.append(UsersList[i] + ",");
		}
		System.out.println(CurrentFile + "\t" + CredentialUser + "\t" + gStartingIndex + "\t" + stBuf.toString());
		return stBuf.toString();
	}

	/***************************************************************
	 * PrintInfo 
	***************************************************************/
	private static String GetUser(User mUser)
	{
		if (mUser == null)
		{
			System.out.println("User is null");
			return "";
		}

		StringBuffer userBuf = new StringBuffer();
		userBuf.append(mUser.getId() + "" + Sep_record); //0
		userBuf.append(StringProcessing(mUser.getLocation()) + "" + Sep_record); //1
		userBuf.append(BooleanProcessing(mUser.isProtected()) + "" + Sep_record); //2

		userBuf.append(mUser.getFollowersCount() + "" + Sep_record); //3
		userBuf.append(mUser.getFriendsCount() + "" + Sep_record); //4

		userBuf.append(mUser.getCreatedAt().getTime() + "" + Sep_record); //5
		userBuf.append(mUser.getFavouritesCount() + "" + Sep_record); //6
		userBuf.append(mUser.getUtcOffset() + "" + Sep_record); //7
		userBuf.append(StringProcessing(mUser.getTimeZone()) + "" + Sep_record); //8
		userBuf.append(StringProcessing(mUser.getLang()) + "" + Sep_record); //9
		userBuf.append(mUser.getStatusesCount() + "" + Sep_record); //10
		userBuf.append(BooleanProcessing(mUser.isGeoEnabled()) + "" + Sep_record); //11

		userBuf.append(BooleanProcessing(mUser.isVerified()) + "" + Sep_record); //12

		userBuf.append(mUser.getListedCount() + "" + Sep_record); //13
		if (mUser.getStatus() != null)
		{
			userBuf.append(mUser.getStatus().getId() + "" + Sep_record); //14
			userBuf.append(mUser.getStatus().getCreatedAt().getTime() + "" + Sep_record); //15
		}
		else
		{
			userBuf.append("#" + "" + Sep_record); //14
			userBuf.append("#" + "" + Sep_record); //15
		}

		String Flag = "";
		if (mUser.isProtected())
		{
			Flag += "P"; // Protected
		}
		else
		{
			Flag += "#";
		}

		if (mUser.getStatusesCount() == 0)
		{
			Flag += "T"; // Tweets = 0
		}
		else
		{
			Flag += "#";
		}
		if (mUser.getStatus() == null)
		{
			Flag += "N"; // No Current Status
		}
		else
		{
			Flag += "#";
		}

		if ((mUser.getStatus() != null) && (mUser.getStatus().getId() < DS_Tweet_StartID))
		{
			Flag += "B"; // Before Date
		}
		else
		{
			Flag += "#";
		}

		if ((mUser.getStatus() != null) && (mUser.getStatus().getId() > DS_Tweet_EndID))
		{
			Flag += "A"; // Before Date
		}
		else
		{
			Flag += "#";
		}
		userBuf.append(Flag + "" + Sep_record); //16
		userBuf.append(StringProcessing(mUser.getScreenName()) + "" + Sep_record); //17
		userBuf.append(StringProcessing(mUser.getName())); //18

		if (userBuf.toString().split("" + Sep_record, 0).length != 19)
		{
			String[] m = userBuf.toString().split("" + Sep_record, 0);
			System.out.println("Bad Userid=" + mUser.getId() + " tokens=" + m.length);
		}
		return userBuf.toString();
	}

	/***************************************************************
	 * StringProcessing 
	***************************************************************/
	public static String StringProcessing(String txt)
	{
		String response = null;
		if (txt == null)
			response = "";
		else
		{
			response = txt.replaceAll(Sep_file + "", " ").replaceAll(Sep_group + "", " ").replaceAll(Sep_record + "", " ").replaceAll("\n", " ").replaceAll("\r", " ")
					+ "";
			response = response.replaceAll("[^\\p{Print}]", " ").replaceAll("\\s+", " ");
		}
		return response;
	}

	/***************************************************************
	 * PrintInfo 
	***************************************************************/
	public static String BooleanProcessing(boolean b)
	{
		if (b == true)
			return "1";

		return "0";
	}

}
