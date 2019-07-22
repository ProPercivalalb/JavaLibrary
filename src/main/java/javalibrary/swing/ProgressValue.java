package javalibrary.swing;

import java.math.BigInteger;

import javax.swing.JProgressBar;

public class ProgressValue {

    private BigInteger value = BigInteger.ZERO;
	private BigInteger maxValue = BigInteger.ZERO;
	private BigInteger scale;
	private JProgressBar progressBar;
	private int breakCount = 0;
	private int progCount = 1;
	private BigInteger progCountBig = BigInteger.ONE;

	public ProgressValue(int precision, JProgressBar progressBar) {
		this.scale = BigInteger.valueOf(precision);
		this.progressBar = progressBar;
		this.progressBar.setMaximum(precision);
	}
	
	public void addMaxValue(long add) {
		this.addMaxValue(BigInteger.valueOf(add));
	}
	
	public void addMaxValue(BigInteger add) {
		this.maxValue = this.maxValue.add(add);
		this.progCountBig = this.maxValue.divide(BigInteger.valueOf(8)).min(BigInteger.valueOf(100000).max(BigInteger.ONE));
		this.progCount = this.progCountBig.intValue();
		this.recalculatePercentage();
	}
	
	public void setValue(long value) {
		this.value = BigInteger.valueOf(value);
		this.breakCount = (int) (value % this.progCount);
		this.recalculatePercentage();
	}
	
	public boolean addValue(int add) {
	    if (this.progressBar.isIndeterminate()) {
	        return false;
	    }
	    
	    synchronized (this) {
    	    this.breakCount += add;
    	    
    	    int times = 0;
    	    
    	    while (this.breakCount >= this.progCount) {
    	        this.breakCount -= this.progCount;
    	        times++;
    	    }
    	    
    	    if (times > 0) {
    	        this.value = this.value.add(this.progCountBig.multiply(BigInteger.valueOf(times)));
                this.recalculatePercentage();
                return true;
    	    }
	    }
	    
        return false;
	}
	
    public void finish() {
        this.value = this.value.add(BigInteger.valueOf(this.breakCount));
        this.recalculatePercentage();
    }
	
	public boolean increase() {
	    return this.addValue(1);
	}
	
	public void recalculatePercentage() {
		this.progressBar.setValue(this.getPercentageDone());
	}
	
	public int getPercentageDone() {
		return this.maxValue == BigInteger.ZERO ? this.scale.intValue() : this.value.multiply(this.scale).divide(this.maxValue).intValue();
	}
	
	public void setIndeterminate(boolean newValue) {
		this.progressBar.setIndeterminate(newValue);
		if (newValue) {
			this.progressBar.setString("Searching...");
		} else {
			this.progressBar.setString("0.0%");
		}
	}
	
	public BigInteger getValue() {
	    return this.value;
	}
}
