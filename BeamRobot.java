package Robotsim;
/**
 * BeamRobot is a Robot with a sensor in the shape of a cone that detects objects and changes direction in order to avoid colliding
 */
public class BeamRobot extends Robot{
	private static final long serialVersionUID =1L;
    double bAngle, bSpeed, sensorsize; // angle and speed of travel
    /**
     * Default constructor for BeamRobot.
     */
    public BeamRobot() {
        // TODO Auto-generated constructor stub
    }
    /**
     * Creates a BeamRobot with specified parameters.
     * @param ix        Initial x-coordinate of the robot.
     * @param iy        Initial y-coordinate of the robot.
     * @param ir        Robot's radius.
     * @param ia        Initial angle of travel.
     * @param is        Speed of travel.
     * @param sen       Size of the sensor.
     */
    public BeamRobot(double ix, double iy, double ir, double ia, double is, double sen) {
        super(ix, iy, ir);
        bAngle = ia;
        bSpeed = is;
        sensorsize = sen;
    }

    @Override
    protected void checkRobot(RobotArena b) {
        bAngle = b.CheckRobotAngle(x, y, rad, bAngle, RobotID);
        if (Beamcol(b)) {
            adjustCourse(bAngle);
        }
    }
    @Override
    public void drawRobot(MyCanvas mc) {
        // Draw the moving robot, wheels, and beam
        mc.showCircle(x, y, rad, col);
        mc.Rightwheel(x, y, rad, 'x', bAngle);
        mc.Leftwheel(x, y, rad, 'x', bAngle);
        mc.showBeam(x, y, rad, bAngle, 70);
    }
    /**
     * Adjusts the course of the robot without doing a 180 turn
     * @param newAngle New angle for adjusting the course.
     */
    protected void adjustCourse(double newAngle) {
        double angleDiff = 30;
        double avoidanceAngle = (newAngle + angleDiff) % 360;
        bAngle = avoidanceAngle;
        double radAngle = bAngle * Math.PI / 180;
        x += bSpeed * Math.cos(radAngle); // new X position
        y += bSpeed * Math.sin(radAngle); // new Y position
    }
    @Override
    protected void adjustRobot() {
        double radAngle = bAngle * Math.PI / 180; // put angle in radiants
        x += bSpeed * Math.cos(radAngle); // new X position
        y += bSpeed * Math.sin(radAngle); // new Y position
    }
    /**
     * Checks for collisions using the beam sensor
     * @param b RobotArena containing all robots.
     * @return True if a collision is detected, false otherwise.
     */
    public boolean Beamcol(RobotArena b) {
        double range = sensorsize;
        double sensorTipX = x + rad * Math.cos(Math.toRadians(bAngle)); // Touching the robot
        double sensorTipY = y + rad * Math.sin(Math.toRadians(bAngle));
        double sensorBaseLeftX = sensorTipX + range * Math.cos(Math.toRadians(bAngle + 30));
        double sensorBaseLeftY = sensorTipY + range * Math.sin(Math.toRadians(bAngle + 30));
        double sensorBaseRightX = sensorTipX + range * Math.cos(Math.toRadians(bAngle - 30));
        double sensorBaseRightY = sensorTipY + range * Math.sin(Math.toRadians(bAngle - 30));

        for (Robot robot : b.GetAll()) {
            if (robot != this) {
                if (robot.hitting(sensorBaseLeftX, sensorBaseLeftY, 1) ||
                        robot.hitting(sensorBaseRightX, sensorBaseRightY, 1)) {
                    return true; // Collision detected
                }
            }
        }
        return false; // No collision detected
    }
    @Override
    protected String getStrType() {
        return "Beam Robot";
    }
}