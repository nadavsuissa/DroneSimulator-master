import java.awt.Graphics;
import java.util.Random;

public class Lidar{
	Drone drone;
	double degrees;
	double current_distance = 0;
	
	public Lidar(Drone drone,double degrees) {
		this.drone = drone;
		this.degrees = degrees;
	}
	
	public double getDistance() {
		Point actualPointToShoot = drone.getPointOnMap();
		double rotation = drone.getRotation() + degrees;

		double distanceInCM = 1;
		while (distanceInCM <= WorldParams.lidarLimit) {
			Point p = Tools.getPointByDistance(actualPointToShoot, rotation, distanceInCM);
			if (drone.realMap.isCollide((int) p.x, (int) p.y)) {
				break;
			}
			distanceInCM++;
		}
		
		
		return distanceInCM;
	}

	public void getSimulationDistance() {
		Random ran = new Random();
		double distanceInCM;
		if (ran.nextFloat() <= 0.05f) { // 5% of the time, not getting an answer
			distanceInCM = 0;
		} else {
			distanceInCM = getDistance();
			distanceInCM += ran.nextInt(WorldParams.lidarNoise * 2) - WorldParams.lidarNoise; // +- 5 CM to the final calc
		}
		
		
		
		this.current_distance = distanceInCM; // store it for instance get
	}

	
	public void paint(Graphics g) {
		Point actualPointToShoot= drone.getPointOnMap();
		double fromRotation = drone.getRotation()+degrees;
		Point to = Tools.getPointByDistance(actualPointToShoot, fromRotation, this.current_distance);

		
	
		g.drawLine((int)actualPointToShoot.x,(int)actualPointToShoot.y, (int)to.x, (int)to.y);
	}
	

	
}
