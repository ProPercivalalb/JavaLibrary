package javalibrary.swing;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JComponent;

public class SwingHelper {

	/**
	 * Recursive method to disable all child components in JComponent
	 * @param component the highest level parent component
	 * @return The original state of all the child components
	 */
	public static Map<Component, Boolean> disableAllChildComponents(JComponent... components) {
		Map<Component, Boolean> stateMap = new HashMap<Component, Boolean>();
		
		for(JComponent component : components) {
			for(Component child : component.getComponents()) {
				stateMap.put(child, child.isEnabled());
				child.setEnabled(false);
				if(child instanceof JComponent)
					stateMap.putAll(disableAllChildComponents((JComponent)child));
			}
		}
		
		return stateMap;
	}

	public static void rewindAllChildComponents(Map<Component, Boolean> stateMap) {
		for(Entry<Component, Boolean> entry : stateMap.entrySet())
			entry.getKey().setEnabled(entry.getValue());
	}
}
