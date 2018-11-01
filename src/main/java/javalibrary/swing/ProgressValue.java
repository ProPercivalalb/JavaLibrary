package javalibrary.swing;

import java.math.BigInteger;

import javax.swing.JProgressBar;

public class ProgressValue {

	public BigInteger value;
	public BigInteger maxValue;
	public BigInteger scale;
	public JProgressBar progressBar;
	
	public ProgressValue(int scale, JProgressBar progressBar) {
		this.value = BigInteger.ZERO;
		this.maxValue = BigInteger.ZERO;
		this.scale = BigInteger.valueOf(scale);
		this.progressBar = progressBar;
		this.progressBar.setMaximum(scale);
	}
	
	public void addMaxValue(int add) {
		this.maxValue = this.maxValue.add(BigInteger.valueOf(add));
	}
	
	public void addMaxValue(BigInteger add) {
		this.maxValue = this.maxValue.add(add);
	}
	
	public void setValue(int value) {
		this.value = BigInteger.valueOf(value);
		this.updateProgress();
	}
	
	public void addValue(int add) {
		this.value = this.value.add(BigInteger.valueOf(add));
		this.updateProgress();
	}
	
	public void increase() {
		this.value = this.value.add(BigInteger.ONE);
		this.updateProgress();
	}
	
	public void updateProgress() {
		this.progressBar.setValue(this.getPercentageDone());
	}
	
	public int getPercentageDone() {
		return this.value.multiply(this.scale).divide(this.maxValue).intValue();
	}
	
	public void setIndeterminate(boolean newValue) {
		this.progressBar.setIndeterminate(newValue);
		if(newValue)
			this.progressBar.setString("Searching...");
		else
			this.progressBar.setString("0.0%");
	}
}
