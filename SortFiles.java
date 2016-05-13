public class SortFiles {
	String file = "./B/BfollowerList";
	public static int[] uniqueBC = new int[27000000];
	
	public static void main(String args[]) {
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
}