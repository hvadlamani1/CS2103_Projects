import java.util.*;
import java.util.function.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import javax.sound.sampled.*;

public class ParticleSimulator extends JPanel {
	private Heap<Event> _events;
	private java.util.List<Particle> _particles;
	private double _duration;
	private int _width;

	public int width() {
		return _width;
	}
	/**
	 * @param filename the name of the file to parse containing the particles
	 */
	public ParticleSimulator (String filename) throws IOException {
		_events = new HeapImpl<>();

		// Parse the specified file and load all the particles.
		Scanner s = new Scanner(new File(filename));
		_width = s.nextInt();
		_duration = s.nextDouble();
		s.nextLine();
		_particles = new ArrayList<>();
		while (s.hasNext()) {
			String line = s.nextLine();
			Particle particle = Particle.build(line);
			_particles.add(particle);
		}

		setPreferredSize(new Dimension(_width, _width));
	}

	@Override
	/**
	 * Draws all the particles on the screen at their current locations
	 * DO NOT MODIFY THIS METHOD
	 */
        public void paintComponent (Graphics g) {
		g.clearRect(0, 0, _width, _width);
		for (Particle p : _particles) {
			p.draw(g);
		}
	}

	// Helper class to signify the final event of the simulation.
	private class TerminationEvent extends Event {
		TerminationEvent (double timeOfEvent) {
			super(timeOfEvent, 0.0, null, null);
		}
	}

	/**
	 * Helper method to update the positions of all the particles based on their current velocities.
	 */
	private void updateAllParticles (double delta) {
		for (Particle p : _particles) {
			p.update(delta);
		}
	}

	/**
	 * Executes the actual simulation.
	 */
	private void simulate (boolean show) {
		
		double lastTime = 0;

		// Create initial events, i.e., all the possible
		// collisions between all the particles and each other,
		// and all the particles and the walls.

		for (Particle p : _particles) {
			for(Particle q : _particles) {
				if(!p.equals(q) && p != null && q!= null) {
					double collisionTime = p.getCollisionTime(q);
					
					//Particle[] particles = {p, q};
					Event collisionEvent = new Event(collisionTime, lastTime, p, q);
					if(collisionTime < Double.POSITIVE_INFINITY) {
					_events.add(collisionEvent);
					}
					
				}
			}
			double collisionTimeWithWallVertical = p.timeToCollideVerticalWall(678);
			Event collisionEventVertical = new Event(collisionTimeWithWallVertical, lastTime, p, null);
			if(collisionTimeWithWallVertical < Double.POSITIVE_INFINITY) {
			_events.add(collisionEventVertical);
			}
			
			double collisionTimeWithWallHorizontal = p.timeToCollideVerticalWall(678);
			Event newCollisionEventHorizontal = new Event(collisionTimeWithWallHorizontal, lastTime, p, null);
			if(collisionTimeWithWallHorizontal < Double.POSITIVE_INFINITY) {
			_events.add(newCollisionEventHorizontal);
			}
			
		}
		
		
		
		_events.add(new TerminationEvent(_duration));
		while (_events.size() > 0) {
			System.out.println(_events.size());
			Event event = _events.removeFirst();
			
			if(event == null) {
				continue;
			}
			
			double delta = event._timeOfEvent - lastTime;

			if (event instanceof TerminationEvent) {
				updateAllParticles(delta);
				break;
			}

			// Check if event still valid; if not, then skip this event
			if(!event.isValidEvent(lastTime, _width)) {
				continue;
			}
			// Since the event is valid, then pause the simulation for the right
			// amount of time, and then update the screen.
			if (show) {
				try {
					Thread.sleep((long) (delta * 500));
				} catch (InterruptedException ie) {}
			}

			System.out.println("event passed");
			// Update positions of all particles
			updateAllParticles(delta);

			Particle a = event.particleA();
			Particle b = event.particleB();

			if (a != null && b != null) {
			    // Particle-particle collision
			    a.updateAfterCollision(event._timeOfEvent, b);

			    // Enqueue new events for the particle(s) that were involved in this event.
			    for (Particle q : _particles) {
			        if (!a.equals(q)) {
			            double newCollisionTime = a.getCollisionTime(q);

			            Event newCollisionEvent1 = new Event(newCollisionTime, event._timeOfEvent, a, q);
			            if (newCollisionTime < Double.POSITIVE_INFINITY) {
			                _events.add(newCollisionEvent1);
			            }

			            Event newCollisionEvent2 = new Event(newCollisionTime, event._timeOfEvent, b, q);
			            if (newCollisionTime < Double.POSITIVE_INFINITY) {
			                _events.add(newCollisionEvent2);
			            }
			        }
			    }
			} 
			else { //if (a == null || b == null) {
				if(a == null) {
				System.out.println("vertical wall event");
		

			    // Enqueue a new event for the particle involved in this event for particle-wall collision
			    double collisionWithHorizontalWall = b.timeToCollideHorizontalWall(678);
			    double collisionWithVerticalWall = b.timeToCollideVerticalWall(678);
			    double newCollisionTime = Math.min(collisionWithHorizontalWall, collisionWithVerticalWall);
			    
			    if(newCollisionTime == collisionWithHorizontalWall) {
			    	a.bounceOffHorizontalWall();
			    }
			    else {
			    	a.bounceOffVerticalWall();
			    }
			    
			    Event newCollisionEvent = new Event(newCollisionTime, event._timeOfEvent, null, a);
			    if (newCollisionTime < Double.POSITIVE_INFINITY) {
			        _events.add(newCollisionEvent);
			    	}
				}
				else {
					System.out.println(" horizontal wall event");


				    // Enqueue a new event for the particle involved in this event for particle-wall collision
				    double collisionWithHorizontalWall = a.timeToCollideHorizontalWall(678);
				    double collisionWithVerticalWall = a.timeToCollideVerticalWall(678);
				    double newCollisionTime = Math.min(collisionWithHorizontalWall, collisionWithVerticalWall);
				    
				    if(newCollisionTime == collisionWithHorizontalWall) {
				    	b.bounceOffHorizontalWall();
				    }
				    else {
				    	b.bounceOffVerticalWall();
				    }
				    Event newCollisionEvent = new Event(newCollisionTime, event._timeOfEvent, a, null);
				    if (newCollisionTime < Double.POSITIVE_INFINITY) {
				        _events.add(newCollisionEvent);
				    	}
				}
			}

			        
			     
			
			
			
			lastTime = event._timeOfEvent;

			// Redraw the screen
			if (show) {
				repaint();
			}
			
			
		}

		// Print out the final state of the simulation
		System.out.println(_width);
		System.out.println(_duration);
		for (Particle p : _particles) {
			System.out.println(p);
		}
	}

	public static void main (String[] args) throws IOException {
		
		if (args.length < 1) {
			System.out.println("Usage: java ParticalSimulator <filename>");
			System.exit(1);
		}
		ParticleSimulator simulator;

		simulator = new ParticleSimulator(args[0]);
		System.out.println(simulator.width());
		JFrame frame = new JFrame();
		frame.setTitle("Particle Simulator");
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(simulator, BorderLayout.CENTER);
		frame.setVisible(true);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		simulator.simulate(true);
	}
}
