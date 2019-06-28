package javalibrary.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Statistics {

	private final List<Double> data;
	private final int valueCount;
	
	private Optional<Double> mean;
	private Optional<Double> variance;
	private Optional<Double> standardDeviation;
	
	
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
		if(!this.mean.isPresent())
			this.mean = Optional.of(MathUtil.sumDouble(this.data) / this.valueCount);
		return this.mean.get();
	}
	
	public double getVariance() {
		if(!this.variance.isPresent()) {
			double mean = this.getMean();
			double variance = 0.0D;
			
			for(double d : this.data) variance += Math.pow(mean - d, 2);
			
			this.variance = Optional.of(variance / this.valueCount);
		}
		return this.variance.get();
	}

	public double getStandardDeviation() {
		if(!this.standardDeviation.isPresent())
			this.standardDeviation = Optional.of(Math.sqrt(this.getVariance()));
		return this.standardDeviation.get();
	}
	
	public double getMax() {
		return MathUtil.findLargestDouble(this.data);
	}
	
	public double getMin() {
		return MathUtil.findSmallestDouble(this.data);
	}

	@Override 
	public String toString() {
		return String.format("Mean: %f, SD: %f ", this.getMean(), this.getStandardDeviation());
	}
}
