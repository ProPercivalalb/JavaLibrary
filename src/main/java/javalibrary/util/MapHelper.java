package javalibrary.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class MapHelper {

	public static <T extends Comparable<T>, X extends Comparable<X>> Map<T, X> sortMapByValue(Map<T, X> map, boolean lowestUp) {
		Comparator<T> comparator = new ValueComparer<T, X>(map);
		if(!lowestUp)
			comparator = Collections.reverseOrder(comparator);
		SortedMap<T, X> sortedData = new TreeMap<T, X>(comparator);
		sortedData.putAll(map);
		return sortedData;
	}
	
	public static <T extends Comparable<T>, X extends Comparable<X>> Map<T, X> createMapSortedByValue(boolean lowestUp) {
		Comparator<T> comparator = new ValueComparer<T, X>(new HashMap<T, X>());
		if(!lowestUp)
			comparator = Collections.reverseOrder(comparator);
		SortedMap<T, X> sortedData = new TreeMap<T, X>(comparator);
		return sortedData;
	}
	
	private static class ValueComparer <T extends Comparable<T>, X extends Comparable<X>> implements Comparator<T> {
		
		private Map<T, X> data;
		
	    public ValueComparer(Map<T, X> data) {
	        this.data = data;
	    }
	    
	    @Override
	    public int compare(T o1, T o2) {
	    	X e1 = this.data.get(o1);
	    	X e2 = this.data.get(o2);
	    	int comp = e1.compareTo(e2);
	        return comp == 0 ? -o1.compareTo(o2) : comp;
	    }
	}
}
