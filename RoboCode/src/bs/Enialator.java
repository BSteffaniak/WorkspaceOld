package bs;

import robocode.*;

import java.awt.Color;
import java.awt.Point;

import static java.lang.Math.*;

/**
 * Enialator - a sample robot by Braden Steffaniak
 * <p/>
 * Tracks robots and shoots them on the move!!!1111111111111
 */
public class Enialator extends RateControlRobot
{
	int    random;
	int    bulletPower;
	double bearing;
	
	Point location;
	
	/**
	* run: FoxyCorndog's default behavior
	*/
	public void run()
	{
		// Set the colors.
		setBodyColor(new Color(1, 0, 0));
		setGunColor(Color.black);
		setRadarColor(Color.yellow);
		setBulletColor(Color.black);
		setScanColor(new Color(1, 0, 0));
		
		setGunRotationRate(15);

		setVelocityRate(3);
		
		bearing += getHeading();
		
		location = new Point(0, 0);
		
		bulletPower = 3;
		
		while(true)
		{
			execute();
			
			bearing = (bearing + (int)15) % 360;
		}
	}
	
	/**
	* onHitByBullet: What to do when hit by a bullet.
	* 
	* @param e the robot that shot it.
	*/
	public void onHitByBullet(HitByBulletEvent e)
	{
		random = 0;
		
		while (random < 45)
		{
			random = (int)(Math.random() * 110);
		}
		
		if ((int)Math.random() == 0)
		{	
			turn(random);
		}
		else
		{
			turn(-random);
		}
		
		ahead(100);
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e)
	{
		/*if (e.getVelocity() > 0)
		{
			double j = 360 - e.getHeading() + (180 - e.getBearing() + getHeading());
			
			double enemyVelocity  = e.getVelocity();
			double bulletVelocity = 20 - (3 * bulletPower);
			double distance       = e.getDistance();
			
			double ticks = distance / (bulletVelocity);
			
			double opposite = e.getVelocity() * ticks;
			
			double l = sqrt(pow(distance, 2) + pow(opposite, 2) - (2 * distance * opposite * cos(j)));
			
			double arc = toDegrees((asin(sin(j) / l) * opposite));
			
			//System.out.println("Arc sine: " + arc + ", " + distance);
			
			turnGun(arc);
		}
	
		System.out.println((e.getBearing() < 0 ? "isLeft" : "isRight") + e.getBearing());
		
		fire(bulletPower);*/
		
		double bulletPower = Math.min(3.0,getEnergy());
		double myX = getX();
		double myY = getY();
		double absoluteBearing = getHeadingRadians() + e.getBearingRadians();
		double enemyX = getX() + e.getDistance() * Math.sin(absoluteBearing);
		double enemyY = getY() + e.getDistance() * Math.cos(absoluteBearing);
		double enemyHeading = e.getHeadingRadians();
		double enemyVelocity = e.getVelocity();
	 
	 
		double deltaTime = 0;
		double battleFieldHeight = getBattleFieldHeight(), 
   	    battleFieldWidth = getBattleFieldWidth();
		double predictedX = enemyX, predictedY = enemyY;
		while ((++deltaTime) * (20.0 - 3.0 * bulletPower) < Point2D.Double.distance(myX, myY, predictedX, predictedY))
		{		
			predictedX += Math.sin(enemyHeading) * enemyVelocity;	
			predictedY += Math.cos(enemyHeading) * enemyVelocity;
			if (	predictedX < 18.0 || predictedY < 18.0 || predictedX > battleFieldWidth - 18.0 || predictedY > battleFieldHeight - 18.0)
			{
				predictedX = Math.min(Math.max(18.0, predictedX), battleFieldWidth - 18.0);
				predictedY = Math.min(Math.max(18.0, predictedY), battleFieldHeight - 18.0);
				break;
			}
		}

		double theta = Utils.normalAbsoluteAngle(Math.atan2(
    	predictedX - getX(), predictedY - getY()));
 
		setTurnRadarRightRadians(Utils.normalRelativeAngle(absoluteBearing - getRadarHeadingRadians()));
		setTurnGunRightRadians(Utils.normalRelativeAngle(theta - getGunHeadingRadians()));
		fire(bulletPower);
	}

	/**
	* onHitByBullet: What to do when you're hit by a bullet
	*/
	public void onHitRobot(HitRobotEvent e)
	{
		if (e.getBearing() > -10 && e.getBearing() < 10)
		{
			fire(3);
		}
	
		back(250);
	
		turn(180);
	}
	
	public void onHitWall(HitWallEvent e)
	{
		back(100);
		
		turn(180);
	}

	public void turnGun(double degrees)
	{
		if (degrees < 0)
		{
			turnGunLeft(-degrees);
		}
		else if (degrees > 0)
		{
			turnGunRight(degrees);
		}
		
		bearing = (bearing + degrees) % 360;
	}

	/**
	* turn: Turn the robot the specified degrees.
	*
	* @param degrees The number of degrees to turn.
	*/
	public void turn(double degrees)
	{
		if (degrees < 0)
		{
			turnLeft(-degrees);
		}
		else if (degrees > 0)
		{
			turnRight(degrees);
		}
	
		bearing = (bearing + degrees) % 360;
	}

	/**
	* onWin:  Do a victory dance
	*/
	public void onWin(WinEvent e) {
		for (int i = 0; i < 50; i++) {
			ahead(50);
			back(50);
		}
	}
}
