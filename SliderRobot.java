package Robotsim;

/**
 * Represents a slider robot in the simulation.
 */
public class SliderRobot extends Robot {
	private static final long serialVersionUID =1L;

    double bAngle, bSpeed;          // Angle and speed of travel

    /**
     * Default constructor for SliderRobot.
     */
    public SliderRobot() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Creates a slider robot with specified parameters.
     *
     * @param ix Initial X position.
     * @param iy Initial Y position.
     * @param ir Initial radius.
     * @param ia Initial angle.
     * @param is Initial speed.
     */
    public SliderRobot(double ix, double iy, double ir, double ia, double is) {
        super(ix, iy, ir);
        bAngle = ia;
        bSpeed = is;
    }

    /**
     * Adjusts the position of the slider robot based on its angle and speed.
     */
    @Override
    protected void adjustRobot() {
        double radAngle = bAngle * Math.PI / 180;        // Put angle in radians
        x += bSpeed * Math.cos(radAngle);                // New X position
    }

    /**
     * Checks and adjusts the angle of the slider robot based on its position and size.
     *
     * @param b RobotArena.
     */
    @Override
    protected void checkRobot(RobotArena b) {
        bAngle = b.CheckRobotAngle(x, y, rad, bAngle, RobotID);
    }

    /**
     * Draws the slider robot and its wheels on the canvas.
     *
     * @param mc MyCanvas object used for drawing.
     */
    @Override
    public void drawRobot(MyCanvas mc) {
        mc.showCircle(x, y, rad, 'o');
        mc.Rightwheel(x, y, rad, 'x', bAngle);
        mc.Leftwheel(x, y, rad, 'x', bAngle);
    }
    /**
     * Gets the type of the robot as a string.
     * @return The type of the robot as a string.
     */
    @Override
    protected String getStrType() {
        return "Game Robot";
    }
}