package Robotsim;

/**
 * EvilRobot class represents a robot with a predatory behavior that eliminates GameRobots upon collision.
 */
public class EvilRobot extends Robot {
	private static final long serialVersionUID =1L;

    double bAngle, bSpeed; // Angle and speed of travel
    /**
     * Default constructor for EvilRobot.
     */
    public EvilRobot() {
        // TODO: Implement if needed
    }
    /**
     * Creates an EvilRobot with specified parameters.
     *
     * @param ix Initial x-coordinate of the robot.
     * @param iy Initial y-coordinate of the robot.
     * @param ir Robot's radius.
     * @param ia Initial angle of the robot's movement.
     * @param is Robot's speed.
     */
    public EvilRobot(double ix, double iy, double ir, double ia, double is) {
        super(ix, iy, ir);
        bAngle = ia;
        bSpeed = is;
    }
    /**
     * Checks for collisions with other robots and eliminates GameRobots upon collision.
     *
     * @param b RobotArena containing all robots.
     */
    @Override
    protected void checkRobot(RobotArena b) {
        bAngle = b.CheckRobotAngle(x, y, rad, bAngle, RobotID);
        for (Robot bs : b.GetAll()) {
            if (bs != this && bs instanceof GameRobot) {
                if (hitting(bs)) {
                    b.GetAll().remove(bs);
                    break;
                }
            }
        }
    }
    /**
     * Draws the EvilRobot on the canvas, including wheels.
     *
     * @param mc MyCanvas object for drawing.
     */
    @Override
    public void drawRobot(MyCanvas mc) {
        mc.showCircle(x, y, rad, 'g');
        mc.Rightwheel(x, y, rad, 'x', bAngle);
        mc.Leftwheel(x, y, rad, 'x', bAngle);
    }
    /**
     * Adjusts the position of the EvilRobot based on its angle and speed.
     */
    @Override
    protected void adjustRobot() {
        double radAngle = bAngle * Math.PI / 180; // Put angle in radians
        x += (bSpeed / 2) * Math.cos(radAngle);   // New X position
        y += (bSpeed / 2) * Math.sin(radAngle);   // New Y position
    }
    /**
     * Gets a string representation of the type of the robot.
     *
     * @return String representing the type of the robot.
     */
    @Override
    protected String getStrType() {
        return "Evil Robot";
    }
}