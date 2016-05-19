package javalibrary.math;

/**
 * @author Alex Barter
 * @since 30 Oct 2013
 */
public class Units {

	public enum Distance {
		
		MILLIMETRES(0.001),
		CENTIMETRES(0.01),
		METRES(1),
		KILOMETRES(1000),
		MEGAMETRES(1000000);
		
		/** The factor by which the unit is multiplied **/
		public final double factor;
		
		private Distance(double factor) {
			this.factor = factor;
		}
	}
	
	public enum Time {
		
		NANOSECOND(0.000000001),
		MILLISECOND(0.001),
		SECOND(1),
		MINUTE(60),
		HOUR(3600),
		DAY(86400),
		MONTH(2592000),
		YEAR(31564800);
		
		/** The factor by which the unit is multiplied **/
		public final double factor;
		
		private Time(double factor) {
			this.factor = factor;
		}
		
		public static double convert(double target, Time currentUnit, Time targetUnit) {
			if(currentUnit == targetUnit)
				return target;
			
			if(currentUnit != SECOND) {
				if(currentUnit.factor < 1)
					target = target * currentUnit.factor;
				else
					target = target / currentUnit.factor;
				currentUnit = SECOND;
			}

			return target / targetUnit.factor;
		}
	}
}
