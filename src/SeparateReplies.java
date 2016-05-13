import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SeparateReplies {

	public static void main(String args[]) throws IOException {
		File file = new File("./out.txt");

		//FileWriter fw = new FileWriter("./out2.txt");
		//BufferedWriter bw = new BufferedWriter(fw);

		ArrayList<String> domains = CreateDataStructures
				.getMediaDomains("./mediaDomains");
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			String line;// = "hindustantimes+hindustantimes+445114571896782848	1394958558000	0	0	0	0	0	0	0	0	514520185;0;142;279;13308792ibnlive+ibnlive+426380448285986816	1390491995000	1	0	0	2	0	0	0	0	516737394;0;3;15;1331057112000;1080;0;0;-1;0;0;;Lucknow;	-1	-1	NullGeo	ICCRevamp	http://ibnlive.in.com/videos/447387/bcci-gives-nod-to-icc-proposal.html	RT @ibnlive: RT @IBNLiveSports: BCCI gives nod to ICC proposal http://t.co/N5rQEjWr7Z #ICCRevamp	-<+>- 426380225627189249	1390491942000	0	0	0	2	0	1	0	0	6509832;0;716263;503;1180729861000;201484;4180;9;19800;1;1;New Delhi;India;	-1	-1	NullGeo	ICCRevamp	http://ibnlive.in.com/videos/447387/bcci-gives-nod-to-icc-proposal.html	RT @IBNLiveSports: BCCI gives nod to ICC proposal http://t.co/N5rQEjWr7Z #ICCRevamp";
			while ((line = br.readLine()) != null) {
				String newLine;
				
				 String[] tokenized = line.split("\t");
				 if(tokenized.length > 13 && tokenized[12].equals("1")) {
					 System.out.println(line);
					 System.exit(0);
				}
				 
				
//				String subs = line.substring(line.indexOf('\t'));
//				//System.out.println("1:");
//				//System.out.println(subs);
//				newLine = line.substring(0, line.indexOf('\t'));
//				//System.out.println("2:");
//				//System.out.println(newLine);
//				boolean fh = false;
//				for (int i = 0; i < domains.size(); i++) {
//					String reg = domains.get(i) + "+";
//					if (subs.contains(reg)) {
//						fh = true;
//						int ind = subs.indexOf(reg);
//						newLine += subs.substring(0, ind);
//						newLine = newLine + '\n';
//						newLine += subs.substring(ind);
//						break;
//					}
//				}
//				if (fh) {
//					System.out.println("dobara");
//				}
//				if (!fh) {
//					newLine = line;
//				}
//				//System.out.println("3:");
//				//System.out.println(newLine);
//				bw.write(newLine + '\n');

			}
			//bw.close();
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("File Not found!");
			e.printStackTrace();
		}

	}
}
