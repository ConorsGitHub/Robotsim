package Robotsim;

/**
 * GameRobot class represents a robot that can score points by touching the target, can be destroyed by EvilRobots
 */
public class GameRobot extends Robot {
	private static final long serialVersionUID =1L;
    double bAngle, bSpeed; // Angle and speed of travel
    /**
     * Default constructor for GameRobot.
     */
    public GameRobot() {
        // TODO: Implement if needed
    }
    /**
     * Creates a GameRobot with specified parameters.
     *
     * @param ix Initial x-coordinate of the robot.
     * @param iy Initial y-coordinate of the robot.
     * @param ir Robot's radius.
     * @param ia Initial angle of the robot's movement.
     * @param is Robot's speed.
     */
    public GameRobot(double ix, double iy, double ir, double ia, double is) {
        super(ix, iy, ir);
        bAngle = ia;
        bSpeed = is;
    }
    /**
     * Checks for collisions with other robots and updates the robot's angle accordingly.
     *
     * @param b RobotArena containing all robots.
     */
    @Override
    protected void checkRobot(RobotArena b) {
        bAngle = b.CheckRobotAngle(x, y, rad, bAngle, RobotID);
    }
    /**
     * Draws the GameRobot on the canvas, including wheels.
     *
     * @param mc MyCanvas object for drawing.
     */
    @Override
    public void drawRobot(MyCanvas mc) {
        mc.showCircle(x, y, rad, col);
        mc.Rightwheel(x, y, rad, 'x', bAngle);
        mc.Leftwheel(x, y, rad, 'x', bAngle);
    }
    /**
     * Adjusts the position of the GameRobot based on its angle and speed.
     */
    @Override
    protected void adjustRobot() {
        double radAngle = bAngle * Math.PI / 180; // Put angle in radians
        x += bSpeed * Math.cos(radAngle);   // New X position
        y += bSpeed * Math.sin(radAngle);   // New Y position
    }
    /**
     * Gets a string representation of the type of the robot.
     *
     * @return String representing the type of the robot.
     */
    @Override
    protected String getStrType() {
        return "Game Robot";
    }
}