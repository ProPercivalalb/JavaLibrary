package javalibrary.swing;

import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JProgressBar;

public class ProgressValue {

    private BigInteger value = BigInteger.ZERO;
	private BigInteger maxValue = BigInteger.ZERO;
	private BigInteger scale;
	private JProgressBar progressBar;
	private int breakCount = 0;
	private int progCount = 1;
	private BigInteger progCountBig = BigInteger.ONE;
	private long startTime = System.currentTimeMillis();
	public int timeLeft;

	public ProgressValue(int precision, JProgressBar progressBar) {
		this.scale = BigInteger.valueOf(precision);
		this.progressBar = progressBar;
		this.progressBar.setMaximum(precision);
	}
	
	public void addMaxValue(long add) {
		this.addMaxValue(BigInteger.valueOf(add));
	}
	
	public void addMaxValue(BigInteger add) {
	    this.setMaxValue(this.maxValue.add(add));
	}
	
	public void setMaxValue(BigInteger maxValue) {
		this.maxValue = maxValue;
		this.progCountBig = this.maxValue.divide(BigInteger.valueOf(8)).min(BigInteger.valueOf(400000).max(BigInteger.ONE));
		this.progCount = this.progCountBig.intValue();
		this.recalculatePercentage();
	}
	
	public void setValue(BigInteger value) {
		this.value = value;
		this.breakCount = value.mod(this.progCountBig).intValue();
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
    	    long timeNow = System.currentTimeMillis();
    	    
    	    if (times > 0) {//timeNow - lastTime > 1000) {
    	        this.value = this.value.add(this.progCountBig.multiply(BigInteger.valueOf(times)));
                this.recalculatePercentage();
                return true;
    	    }
	    }
	    
        return false;
	}
	
	public void start() {
	    this.startTime = System.currentTimeMillis();
	}
	
    public void finish() {
        this.value = this.value.add(BigInteger.valueOf(this.breakCount));
        this.recalculatePercentage();
    }
	
	public boolean increase() {
	    return this.addValue(1);
	}
	
	public void reset() {
	    this.setValue(0);
	    this.maxValue = BigInteger.ZERO;
	    this.recalculatePercentage();
	}
	
	public void recalculatePercentage() {
	    BigInteger percentage = this.getPercentageDoneBig();
		this.progressBar.setValue(percentage.intValue());
		if (percentage.equals(BigInteger.ZERO)) {
		    this.timeLeft = -1;
		} else {
		    BigInteger timeDiff = BigInteger.valueOf(System.currentTimeMillis() - this.startTime);
		    this.timeLeft = timeDiff.multiply(this.scale).divide(percentage).subtract(timeDiff).divide(this.scale).intValue();
		}
	}
	
	private static final NumberFormat numFormatter = NumberFormat.getNumberInstance(Locale.UK);
	public static String formatBigInteger(BigInteger value) {
	    return numFormatter.format(value);
	}
	
	public int getPercentageDone() {
        return this.getPercentageDoneBig().intValue();
    }
	
	// 0-100
	public BigInteger getPercentageDoneBig() {
		return this.maxValue == BigInteger.ZERO ? this.scale : this.value.multiply(this.scale).divide(this.maxValue);
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
