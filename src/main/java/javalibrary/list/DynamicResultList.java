package javalibrary.list;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class DynamicResultList<T extends Result> implements List<T> {

	private final int capacity;
	private final boolean duplicates;
	private final LinkedList<T> results;
	
	private int worstSolutionIndex;
	private Result worstResult;
	
	public DynamicResultList(int size) {
		this(size, true);
	}
	
	public DynamicResultList(int size, boolean duplicates) {
		this.capacity = size;
		this.duplicates = duplicates;
		this.results = new LinkedList<T>();
		this.worstResult = null;
	}
	
	@Override
	public synchronized boolean add(T result) {
		if(this.worstResult == null) { //First item being added
			this.worstResult = result;
			this.results.add(result);
			this.worstSolutionIndex = this.results.size() - 1;
			return true;
		}
		else if(result.isWorseOrEqual(this.worstResult)) { //New result is worse or equal to current worst result
			if(this.results.size() < this.capacity) { //Is not at is max capacity yet
				if(!this.duplicates && this.contains(result))
					return false;
				this.worstResult = result;
				this.results.add(result);
				this.worstSolutionIndex = this.results.size() - 1;
				return true;
			}
		}
		else { //Given result is better than worst
			if(!this.duplicates && this.contains(result))
				return false;
			this.results.add(result);
			if(this.results.size() > this.capacity) { //In adding this result has overflowed its capacity
				this.results.remove(this.worstSolutionIndex);
				
				//Determine the next worst result
				this.worstResult = this.results.get(0);
				this.worstSolutionIndex = 0;
				
				Iterator<T> iterator = this.results.iterator();
				int index = 0;
				
				while(iterator.hasNext()) {
					T possibleResult = iterator.next();
					if(possibleResult.isWorseThan(this.worstResult)) {
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
	
	@Override
	public boolean contains(Object result) {
		return this.results.contains(result);
	}
	
	@Override
	public T get(int index) {
	    throw new UnsupportedOperationException();
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
	
	@Override
	public int size() {
		return this.results.size();
	}
	
	@Override
	public void clear() {
		this.results.clear();
		this.worstResult = null;
		this.worstSolutionIndex = 0;
	}
	
	@Override
	public String toString() {
		return String.format("Slots: %d/%d, Worst: %s", this.results.size(), this.capacity, this.worstResult);
	}

    @Override
    public boolean isEmpty() {
        return this.results.isEmpty();
    }

    @Override
    public Iterator<T> iterator() {
        return this.results.iterator();
    }

    @Override
    public Object[] toArray() {
        return this.results.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return this.results.toArray(a);
    }

    @Override
    public boolean remove(Object o) {
        return this.results.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return this.results.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T set(int index, T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(Object o) {
        return this.results.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return this.results.lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        return this.results.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return this.results.listIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return this.subList(fromIndex, toIndex);
    }

}
