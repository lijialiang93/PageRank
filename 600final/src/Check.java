
public class Check {
	
	public String stop = "an a the in on at for of i me you we us you he him they them she her it that this";
	
	//only allow lower case English letter
	public  String[] checkString(String text) {
		text = text.toLowerCase();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			
			if((c<='z'&&c>='a')||c==' '){
				sb.append(c);
			}
		}
		text = sb.toString();
		String[] strs = text.split(" ");

		return strs;
	}
	
	//screening stop word
	public  String[] stopWords(String[] strs){

		
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < strs.length; i++) {
			if(!stop.contains(strs[i])){
				sb.append(strs[i]+' ');
			}
		}
		strs = sb.toString().split(" ");
		return strs;
	}

}
