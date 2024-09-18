/**
 * Represents a collision between a particle and another particle, or a particle and a wall.
 */
public class Event implements Comparable<Event> {
	double _timeOfEvent;
	double _timeEventCreated;
	Particle a;
	Particle b;
	private int count1, count2;
	private double width = 678;

	/**
	 * @param timeOfEvent the time when the collision will take place
	 * @param timeEventCreated the time when the event was first instantiated and added to the queue
	 * @param particles is the particles that were involved in the event
	 */
	public Event (double timeOfEvent, double timeEventCreated, Particle a, Particle b) {
		_timeOfEvent = timeOfEvent;
		_timeEventCreated = timeEventCreated;
		this.a = a;
		this.b = b;
		count1 = 0;
		count2 = 0;
	}

	@Override
	/**
	 * Compares two Events based on their event times. Since you are implementing a maximum heap,
	 * this method assumes that the event with the smaller event time should receive higher priority.
	 */
	public int compareTo (Event e) {
		if (_timeOfEvent < e._timeOfEvent) {
			return +1;
		} else if (_timeOfEvent == e._timeOfEvent) {
			return 0;
		} else {
			return -1;
		}
	}
	
	/**
	 * 
	 * @return true if event is valid, false if otherwise
	 */
	

	public boolean isValidEvent(double currentTime, double width) {
		double collisionTime = Double.POSITIVE_INFINITY;
		double collisionTimeWithWallA_Vertical = Double.POSITIVE_INFINITY;
		double collisionTimeWithWallA_Horizontal = Double.POSITIVE_INFINITY;
		double collisionTimeWithWallB_Vertical = Double.POSITIVE_INFINITY;
		double collisionTimeWithWallB_Horizontal = Double.POSITIVE_INFINITY;

		
		if(a!=null && b!=null) {
			 collisionTime = a.getCollisionTime(b);
		}
		
		if(b == null) {
			collisionTimeWithWallA_Vertical = a.timeToCollideVerticalWall(width);
			collisionTimeWithWallA_Horizontal = a.timeToCollideHorizontalWall(width);

		}
		else {
			collisionTimeWithWallB_Vertical = b.timeToCollideVerticalWall(width);
			collisionTimeWithWallB_Horizontal = b.timeToCollideHorizontalWall(width);
		}
		if(collisionTime == Double.POSITIVE_INFINITY) {
	    	return false;
		}
		
		if(_timeOfEvent < currentTime) {
			return false;
		}
		
		if(collisionTimeWithWallA_Vertical != Double.POSITIVE_INFINITY ||
				collisionTimeWithWallA_Horizontal != Double.POSITIVE_INFINITY ||
				collisionTimeWithWallB_Vertical != Double.POSITIVE_INFINITY ||
				collisionTimeWithWallB_Horizontal != Double.POSITIVE_INFINITY) {
			return true;
		}
		  if(collisionTime <= currentTime || 
			       collisionTimeWithWallA_Vertical <= currentTime || 
			       collisionTimeWithWallA_Horizontal <= currentTime || 
			       collisionTimeWithWallB_Vertical <= currentTime || 
			       collisionTimeWithWallB_Horizontal <= currentTime) {
			        return true;
			    }
		return true;
	}
	
	/**
	 * 
	 * @return particle a
	 */
	public Particle particleA() {
		return a;
	}
	
	/**
	 * 
	 * @return particle b
	 */
	public Particle particleB() {
		return b;
	}

}
