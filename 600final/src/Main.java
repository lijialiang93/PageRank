
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {
	public static HashMap<Integer, String> pages = new HashMap<Integer, String>();
	public static HashMap<Integer, String> texts = new HashMap<Integer, String>();
	public static final int totalPages = 10;
	public static final String occurrenceList = "src/occurrenceList.txt";
	public static final String startPage = "https://www.stevens.edu/news";
	public static Trie t = new Trie();
	public static Check check = new Check();
	public static Loader loader = new Loader();
	public static Intersection inter = new Intersection();
	public static Search search = new Search();
	
	public static void main(String[] args) {
		Main main = new Main();
		main.init();
		main.doSearch();
	}
	
	public void init(){
	System.out.print("initializing...");
		
		Document doc = loader.loadPage(startPage);
		Elements p = doc.select("p");
		String text = p.text();
		
		//put page and text into hashmap
		pages.put(0, startPage);
		texts.put(0, text);
		Elements links = doc.select("a[href]");

		for (int i = 0; i < totalPages - 1; i++) {
			Element link = links.get(i);
			pages.put(i + 1, link.attr("abs:href"));
		}

		for (int i = 0; i < totalPages - 1; i++) {
			doc = loader.loadPage(pages.get(i + 1));
			p = doc.select("p");
			text = p.text();
			texts.put(i + 1, text);
		}
		//create trie
		createTrie(t);

		System.out.println("done!");
		
		

	}
	
	public void doSearch(){

		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		while (true) {
			boolean found = true;
			System.out.print('\n' + "search: ");
			String input = sc.nextLine();
			String[] words = check.checkString(input);
			if(words.length==1&&words[0].equals("")){
				System.out.println("wrong input, try again.");
				continue;
			}
			HashMap<String, ArrayList<Integer>> intersect = new HashMap<String, ArrayList<Integer>>();

			for (int i = 0; i < words.length; i++) {
				ArrayList<Integer> webList = new ArrayList<Integer>();
				int id = search.searchTrie(words[i]);

				if (id != 0 && id != -1) {
					String line = search.searchList(id);
					HashMap<Integer, String> outputMap = search.getMap(line, words[i]);
					//get frequency
					List<Map.Entry<Integer, Integer>> list = search.frequency(outputMap);
					System.out.println("<"+words[i] +">"+ " found in "+list.size()+" pages");
					System.out.println("<frequency><url><position>");
					for (int j = 0; j < list.size(); j++) {
						// frequency
						String frequency = list.get(j).getValue().toString();
						// web site index
						int index = list.get(j).getKey();
						webList.add(index);
						String website = pages.get(index);
						String positions = outputMap.get(index);
						System.out.println(frequency + " " + website + " " + positions);
					}

					System.out.println();
					intersect.put(words[i], webList);
				} else {
					//not found
					System.out.println(words[i]);
					System.out.println("word not found!");
					System.out.println();
					found = false;
				}
			}
			if (found == true&&intersect.size()>1) {
				inter.intersectSite(intersect);
			}
		}
	}
	//create trie
	public  void createTrie(Trie t) {

		for (int i = 0; i < totalPages; i++) {
			String text = texts.get(i);
			String[] strs = check.checkString(text);
			strs = check.stopWords(strs);
			insert(strs, t, i);
		}

		t.write();
	}
	
	//insert word into trie
	public void insert(String[] strs, Trie t, int pageNum) {
		for (int i = 0; i < strs.length; i++) {
				t.insert(strs[i], "page" + pageNum + "-" + i);
		}
	}

	

	
	
}
