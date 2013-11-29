package javalibrary.swing.chart;

import java.util.ArrayList;

/**
 * @author Alex Barter
 * @since 1 Nov 2013
 */
public class ChartList extends ArrayList<ChartData> {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 * @return
	 */
	public ArrayList<Double> getAllValues() {
		ArrayList<Double> values = new ArrayList<Double>();
		//Runs through all the ChartData instances in this instance and adds then to the list
		for(ChartData data : this) {
			values.add(data.getValue());
		}
		return values;
	}
	
	public double getTotalValues() {
		double amount = 0.0D;
		//Runs through all the ChartData instances in this instance and adds then to the double
		for(ChartData data : this) {
			amount += data.getValue();
		}
		return amount;
	}
}
