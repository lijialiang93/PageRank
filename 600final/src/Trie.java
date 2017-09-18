import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class Trie {
	private final Node root = new Node(false, -1);
	private int id = 0;
	private HashMap<Integer, String> pm = new HashMap<Integer, String>();
	
	//insert a word into trie
	public void insert(String input, String position){
		Node current = root;
		int i = 0;
		while(i<input.length()&&current.strs[getIndex(input.charAt(i))]!=null){
			
			int j = 0;
			int k = getIndex(input.charAt(i));
			String label = current.strs[k];
			
			//find the prefix
			while (j < label.length() && i < input.length() && label.charAt(j) == input.charAt(i)) {
				i++;
				j++;
			}
			
			if(j==label.length()){
				current = current.children[k];
			}
			else{
				if(i == input.length()){
					//insert a word while the prefix are exist
					Node oldNode = current.children[k];
					String remain = label.substring(j, label.length());
					label = label.substring(0, j);
					
					id++;
					current.strs[k] = label;
					current.children[k] = new Node(true,id);
					pm.put(id, position);
					
					
					current.children[k].children[getIndex(remain.charAt(0))]  = oldNode;
					current.children[k].strs[getIndex(remain.charAt(0))] = remain;
					
				}
				else{
					//insert a word while the partial of prefix belong to other word
					String remainLabel = label.substring(j);
					String remainInput = input.substring(i);
					Node oldNode = current.children[k];
					label = label.substring(0, j);
					current.strs[k] = label;
					current.children[k] = new Node(false,-1);
					current.children[k].children[getIndex(remainLabel.charAt(0))] = oldNode;
					current.children[k].strs[getIndex(remainLabel.charAt(0))] = remainLabel;
					id++;
					current.children[k].children[getIndex(remainInput.charAt(0))] = new Node(true,id);
					current.children[k].strs[getIndex(remainInput.charAt(0))] = remainInput;
					pm.put(id, position);
					
				}
				
				return;
			}
			
		
		}
		
		if (i < input.length()) {
			
			//insert a new word
			id++;
			current.strs[getIndex(input.charAt(i))] = input.substring(i);
			current.children[getIndex(input.charAt(i))] = new Node(true, id);
			pm.put(id, position);
		} else { 
			if(current.isWord){
				//word already existed, add new position
				String pos = pm.get(current.id);
				pos = pos +" "+ position;
				pm.put(current.id, pos);
				return;
			}
			else{
				//word prefix are existed, make it as a word
				id++;
				current.isWord = true;
				current.id = id;
				pm.put(id, position);
			}
			
		}
		
	}
	
	//search word return id, return -1 if word not found
	public int search(String input){
		int i = 0;
		Node current = root;
		while(i<input.length()&&current.children[getIndex(input.charAt(i))]!=null){
			int k = getIndex(input.charAt(i));
			String label = current.strs[k];
			int j = 0;
			while (i < input.length() && j < label.length()) {
				if (input.charAt(i) != label.charAt(j)) {
					return -1; // character mismatch
				}
				i++;
				j++;
			}
			if(i<=input.length()&&j==label.length()){
				current = current.children[k];
			}
			else{
				return -1;
			}
		}
		
		if(i==input.length()){
			if(current.isWord){
				return current.id;
			}
		}
		return -1;
	}
	public int getIndex(char c){
		return c-'a';
	}
	//write to occurrence list
	public void write(){
		File file = new File(Main.occurrenceList);
		try {
			FileWriter fw = new FileWriter(file);
			fw.write("");
			fw.flush();
			fw.close();
			fw = new FileWriter(file,true);
			for (int i = 1; i <= id; i++) {
				String str = pm.get(i);
				if(str==null){
					System.out.println(i);
				}
				fw.write(i+" "+str+'\n');
				fw.flush();
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
