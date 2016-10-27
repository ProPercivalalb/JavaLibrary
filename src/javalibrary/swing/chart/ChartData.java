package javalibrary.swing.chart;

/**
 * @author Alex Barter
 * @since 09 Oct 2013
 */
public class ChartData {

	private String name;
	private double value;
	private String toolTip;
	
	public ChartData(String name, double value, String toolTip) {
		this.name = name;
		this.value = value;
		this.toolTip = toolTip;
	}
	
	public ChartData(String name, double value) {
		this(name, value, name);
	}
	
	public ChartData setNoToolTip() {
		this.toolTip = "";
		return this;
	}
	
	public double getValue() {
		return this.value;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getToolTip() {
		return this.toolTip;
	}
}
