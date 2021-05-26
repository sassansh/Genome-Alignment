// CSID of group member 1: s8w9a
//
// The first three lines of this file will be processed by a script.
// If there are fewer than three group members, you can delete some
// of the CSIDs.
//
// Just in case, you can put the group member's names here too.
// These lines won't be processed by a script. For example,
// Name of Group Member 1: Sassan Shokoohi
import java.io.*; 
public class GA { 

	String ReadFromFile(String name) throws Exception {
		File file = new File(name); 
		BufferedReader br = new BufferedReader(new FileReader(file)); 
		return br.readLine();
	}

	public void Main(String[] args) throws Exception {
		if (args.length < 2) {
			System.out.println("Error: Need two filenames");
			return;
		}

		// Read the sequences from files
		String A = ReadFromFile(args[0]);
		String B = ReadFromFile(args[1]);
		
		// Set the mismatch penalties
		int gapPenalty = 3;
		int atcgPenalty = 1;
		int acagPenalty = 2;
		int tctgPenalty = 2;

		// Set up the 2D array
		int lengthA = A.length() + 1;
		int lengthB = B.length() + 1;
		int[][] geneTable = new int[lengthA][2];

		// Initialize the 0th column
		for (int i = 0; i < lengthA; i++) {	
			geneTable[i][0] = i * gapPenalty;
		}

		// Complete the rest of the geneTable
		for (int j = 1; j < lengthB; j++) {
			geneTable[0][1] = j * gapPenalty;
			for (int i = 1; i < lengthA; i++) {
				// Calcualte penalty for the 3 cases
				char c1 = A.charAt(i - 1);
				char c2 = B.charAt(j - 1);

				int charPen;
				if (c1 == c2) {
					charPen = 0;
				}
				else if ((c1 == 'a' && c2 == 't') || (c1 == 't' && c2 == 'a') || (c1 == 'c' && c2 == 'g') || (c1 == 'g' && c2 == 'c')) {
					charPen = atcgPenalty;
				}
				else if ((c1 == 'a' && c2 == 'c') || (c1 == 'c' && c2 == 'a') || (c1 == 'a' && c2 == 'g') || (c1 == 'g' && c2 == 'a')) {
					charPen = acagPenalty;
				} else {
					charPen = tctgPenalty;
				}
				int charCharPenalty = charPen + geneTable[i - 1][0];
				int gapA = gapPenalty + geneTable[i - 1][1];
				int gapB = gapPenalty + geneTable[i][0];
				// Figure out the minimum of the 3 cases
				if (charCharPenalty <= gapA && charCharPenalty <= gapB) {
					geneTable[i][1] = charCharPenalty;
				}
				else if (gapA <= charCharPenalty && gapA <= gapB) {
					geneTable[i][1] = gapA;
				}
				else {
					geneTable[i][1] = gapB;
				}
			}
			for (int i = 0; i < lengthA; i++) {
				geneTable[i][0] = geneTable[i][1];
			}
		}

		// Print the minimum Penalty Value
	 	System.out.println(geneTable[A.length()][1]);

	}

	public static void main(String[] args) throws Exception { 
		GA obj = new GA();
		obj.Main(args);
	}
} 
