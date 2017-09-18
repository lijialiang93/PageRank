import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

public class Intersection {
    private Integer[] array;
    private Integer[] temp;
    
	public  void intersectSite(HashMap<String, ArrayList<Integer>> map) {
		ArrayList<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>();
		for (Entry<String, ArrayList<Integer>> entry : map.entrySet()) {
			list.add(entry.getValue());
		}

		int first = 0;
		int second = 1;
		while (second < list.size()) {
			ArrayList<Integer> twoIntersect = intersectList(list.get(first), list.get(second));
			list.set(first, twoIntersect);
			second++;
		}

		ArrayList<Integer> resultList = list.get(0);
		if(resultList.size()>0){
			System.out.println("Intersected in " + resultList.size() + " pages:");
			for (int i = 0; i < resultList.size(); i++) {
				System.out.println(Main.pages.get(resultList.get(i)));
			}
		}
		else{
			System.out.println("no intersection between these words");
		}
		
	}
	
	public  ArrayList<Integer> intersectList(ArrayList<Integer> listA, ArrayList<Integer> listB) {
		Set<Integer> result = new HashSet<Integer>();
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.addAll(listA);
		list.addAll(listB);
		Integer[] array = list.toArray(new Integer[list.size()]);
		
		//sort
		//Arrays.sort(array);
		array = mergeSort(array);
		Integer previous = null;
		for (Integer current : array) {
			if (previous == current) {
				result.add(previous);
			}
			previous = current;
		}
		list.clear();
		Iterator<Integer> iter = result.iterator();

		while (iter.hasNext()) {
			list.add(iter.next());
		}
		return list;
	}

    public Integer[] mergeSort(Integer[] array) {
    	this.array = array;
    	this.temp = new Integer[array.length];
        divide(0, array.length - 1);
        return array;
    }
    
	  private void divide(int left, int right) {
	       
	        if (left < right) {
	            int mid = left + (right - left) / 2;
	            divide(left, mid);
	            divide(mid + 1, right);
	            merge(left, mid, right);
	        }
	    }
	  
	    private void merge(int left, int mid, int right) {
	        for (int i = left; i <= right; i++) {
	            temp[i] = array[i];
	        }
	        int i = left;
	        int j = mid + 1;
	        int k = left;
	        while (i <= mid && j <= right) {
	            if (temp[i] >= temp[j]) {
	                array[k] = temp[i];
	                i++;
	            } else {
	                array[k] = temp[j];
	                j++;
	            }
	            k++;
	        }
	        while (i <= mid) {
	            array[k] = temp[i];
	            k++;
	            i++;
	        }
	 
	    }

}
