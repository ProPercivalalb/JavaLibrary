package javalibrary.list;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

public class DynamicResultList<T extends Result> {

	private int size;
	private boolean duplicates; //TODO
	private LinkedList<T> results;
	private int worstSolutionIndex;
	private Result worstResult;
	
	public DynamicResultList(int size) {
		this(size, true);
	}
	
	public DynamicResultList(int size, boolean duplicates) {
		this.size = size;
		this.duplicates = duplicates;
		this.results = new LinkedList<T>();
		this.worstResult = null;
	}
	
	public boolean addResult(T result) {
		if(this.worstResult == null) { //First item being added
			this.worstResult = result;
			this.results.add(result);
			this.worstSolutionIndex = this.results.size() - 1;
			return true;
		}
		else if(result.isResultWorseOrEqual(this.worstResult)) { //New result is worse or equal to current worst result
			if(this.results.size() < this.size) { //Is not at is max capacity yet
				if(!this.duplicates && this.containsResult(result))
					return false;
				this.worstResult = result;
				this.results.add(result);
				this.worstSolutionIndex = this.results.size() - 1;
				return true;
			}
		}
		else { //Given result is better than worst
			if(!this.duplicates && this.containsResult(result))
				return false;
			this.results.add(result);
			if(this.results.size() > this.size) { //In adding this result has overflowed its capacity
				this.results.remove(this.worstSolutionIndex);
				
				//Determine the next worst result
				this.worstResult = this.results.get(0);
				this.worstSolutionIndex = 0;
				
				Iterator<T> iterator = this.results.iterator();
				int index = 0;
				
				while(iterator.hasNext()) {
					T possibleResult = iterator.next();
					if(possibleResult.isResultWorse(this.worstResult)) {
						this.worstResult = possibleResult;
						this.worstSolutionIndex = index;
					}
					index++;
				}
			}
			return true;
		}
		
		return false;
	}
	
	public boolean containsResult(T result) {
		return this.results.contains(result);
	}
	
	public T get(int index) {
		return this.results.get(index);
	}
	
	public LinkedList<T> copyList() {
		return (LinkedList<T>)this.results.clone();
	}
	
	public LinkedList<T> getList() {
		return this.results;
	}
	
	public void sort() {
		Collections.sort(this.results);
		this.worstSolutionIndex = this.results.size() - 1;
	}
	
	public int size() {
		return this.results.size();
	}
	
	public void clear() {
		this.results.clear();
		this.worstResult = null;
		this.worstSolutionIndex = 0;
	}
	
	@Override
	public String toString() {
		return String.format("Slots: %d/%d, Worst: %s", this.results.size(), this.size, this.worstResult);
	}

}
