package javalibrary.swing;

import java.awt.GridBagConstraints;

public class LayoutUtil {

	public static GridBagConstraints createConstraints(int gridX, int gridY, double weightX, double weightY) {
		GridBagConstraints constraints = new GridBagConstraints();
	    constraints.fill = GridBagConstraints.BOTH;
	    constraints.gridx = gridX;
	    constraints.gridy = gridY;
	    constraints.weightx = weightX;
	    constraints.weighty = weightY;
	    return constraints;
	}
	
	public static GridBagConstraints createConstraintsIPad(int gridX, int gridY, double weightX, double weightY, int ipadx, int ipady) {
		GridBagConstraints constraints = new GridBagConstraints();
	    constraints.fill = GridBagConstraints.BOTH;
	    constraints.gridx = gridX;
	    constraints.gridy = gridY;
	    constraints.weightx = weightX;
	    constraints.weighty = weightY;
	    constraints.ipadx = ipadx;
	    constraints.ipady = ipady;
	    return constraints;
	}
}
