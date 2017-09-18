
public class Node {
	public int id;
	public Node[] children = new Node[26];
	public String[] strs = new String[26];
	boolean isWord;

	public Node(boolean isWord, int id) {
		this.isWord = isWord;
		this.id = id;
	}
	
}
