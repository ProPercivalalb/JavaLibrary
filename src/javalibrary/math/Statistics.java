package javalibrary.math;

import java.util.ArrayList;
import java.util.List;

public class Statistics {

	private final List<Double> data;
	private final int valueCount;
	
	private StatCache mean;
	private StatCache variance;
	private StatCache standardDeviation;
	
	
	public <T> Statistics(Iterable<T> data) {
		this.data = new ArrayList<Double>();
		for(Object n : data) {
			
			if(n instanceof Double)
				this.data.add(((Double)n).doubleValue());
			else if(n instanceof Long)
				this.data.add(((Long)n).doubleValue());
			else if(n instanceof Byte)
				this.data.add(((Byte)n).doubleValue());
			else if(n instanceof Integer)
				this.data.add(((Integer)n).doubleValue());
			else if(n instanceof String)
				this.data.add(Double.valueOf((String)n).doubleValue());
			
		}
		
		this.valueCount = this.data.size();
	}
	
	public double getMean() {
		if(this.mean == null)
			this.mean = new StatCache(MathHelper.sumDouble(this.data) / this.valueCount);
		return this.mean.value;
	}
	
	public double getVariance() {
		if(this.variance == null) {
			double mean = this.getMean();
			double variance = 0.0D;
			
			for(double d : this.data) variance += Math.pow(mean - d, 2);
			
			this.variance = new StatCache(variance / this.valueCount);
		}
		return this.variance.value;
	}

	public double getStandardDeviation() {
		if(this.standardDeviation == null)
			this.standardDeviation = new StatCache(Math.sqrt(this.getVariance()));
		return this.standardDeviation.value;
	}
	
	public double getMax() {
		return MathHelper.findLargestDouble(this.data);
	}
	
	public double getMin() {
		return MathHelper.findSmallestDouble(this.data);
	}

	@Override 
	public String toString() {
		return String.format("Mean: %f, SD: %f ", this.getMean(), this.getStandardDeviation());
	}
	
	private class StatCache {
		public double value;
		public StatCache(double value) {
			this.value = value;
		}
	}
}
