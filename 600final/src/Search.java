import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Search {
	
	//search trie
	public  int searchTrie(String word) {
		int num = Main.t.search(word);
		return num;
	}
	
	//search occurrence list
	public  String searchList(int id) {
		File file = new File(Main.occurrenceList);
		FileReader fr;
		String str = null;
		try {
			fr = new FileReader(file);
			LineNumberReader reader = new LineNumberReader(fr);

			while (true) {
				str = reader.readLine();
				if (reader.getLineNumber() == id) {
					reader.close();
					fr.close();
					return str;

				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return str;
	}
	
	//return key-pair(website index, position string)
	public  HashMap<Integer, String> getMap(String line, String str) {
		String[] words = line.split(" ");
		String word;
		int position;
		int prevIndex = -1;
		int nextIndex = 0;
		// key-website index value- position
		HashMap<Integer, String> outputMap = new HashMap<Integer, String>();

		for (int i = 1; i < words.length; i++) {
			word = words[i];
			if (word.equals("null")) {
				continue;
			}
			position = Integer.parseInt(word.substring(6)) + 1;
			nextIndex = Integer.parseInt(word.substring(4, 5));

			if (nextIndex != prevIndex) {
				outputMap.put(nextIndex, "<" + position + ">");
				prevIndex = nextIndex;

			} else {
				String positions = outputMap.get(nextIndex);
				positions = positions + "<" + position + ">";
				outputMap.put(nextIndex, positions);
			}

		}
		return outputMap;
	}
	
	//return sorted list contains key-pair(website index, frequency)
	public  List<Map.Entry<Integer, Integer>> frequency(HashMap<Integer, String> map) {
		HashMap<Integer, Integer> freqMap = new HashMap<Integer, Integer>();
		Integer website;
		String positions;
		for (Entry<Integer, String> entry : map.entrySet()) {
			int frequency = 0;
			website = entry.getKey();
			positions = entry.getValue();
			for (int i = 0; i < positions.length(); i++) {
				if (positions.charAt(i) == '<') {
					frequency++;
				}
			}

			freqMap.put(website, frequency);
		}

		List<Map.Entry<Integer, Integer>> list = new ArrayList<Map.Entry<Integer, Integer>>(freqMap.entrySet());

		Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
			public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});

		return list;
	}

}
