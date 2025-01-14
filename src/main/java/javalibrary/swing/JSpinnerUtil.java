package javalibrary.swing;

import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class JSpinnerUtil {

	/**
	 * Returns an array of the two spinners that are interlinked
	 */
	public static JSpinner[] createRangeSpinners(int minStart, int maxStart, int min, int max, int step) {
		SpinnerNumberModel modelMin = new SpinnerNumberModel(minStart, min, max, step);
		JSpinner spinnerMin = new JSpinner(modelMin);
		SpinnerNumberModel modelMax = new SpinnerNumberModel(maxStart, min, max, step) {
			private static final long serialVersionUID = 1L;

			@Override
			public Comparable<Integer> getMinimum() {
				Integer minVariable = (Integer)modelMin.getNumber().intValue();
				Integer minConstant = (Integer)super.getMinimum();
				return minVariable.compareTo(minConstant) > 0 ? minVariable : minConstant;
			}
		};

		JSpinner spinnerMax = new JSpinner(modelMax);
		spinnerMin.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent event) {
				if(modelMin.getNumber().intValue() > modelMax.getNumber().intValue())
					spinnerMax.setValue(spinnerMin.getValue());
			}
		});
		
		spinnerMax.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent event) {
				if(modelMin.getNumber().intValue() > modelMax.getNumber().intValue())
					spinnerMin.setValue(spinnerMax.getValue());
			}
		});
		
		((JSpinner.DefaultEditor)spinnerMin.getEditor()).getTextField().setEditable(false);
		((JSpinner.DefaultEditor)spinnerMax.getEditor()).getTextField().setEditable(false);
		
		return new JSpinner[] {spinnerMin, spinnerMax};
	}
	
	public static JSpinner createSpinner(int start, int min, int max, int step) {
		SpinnerNumberModel model = new SpinnerNumberModel(start, min, max, step);
		JSpinner spinner = new JSpinner(model);
		((JSpinner.DefaultEditor)spinner.getEditor()).getTextField().setEditable(false);
		return spinner;
	}
	
	public static JSpinner createSpinner(Object[] data) {
		SpinnerListModel monthModel = new SpinnerListModel(data);
		JSpinner spinner = new JSpinner(monthModel);
		((JSpinner.DefaultEditor)spinner.getEditor()).getTextField().setEditable(false);
		return spinner;
	}
}
