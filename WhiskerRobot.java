package Robotsim;

/**
 * Represents a whisker robot in the simulation.
 */
public class WhiskerRobot extends Robot {
	private static final long serialVersionUID =1L;

    double bAngle, bSpeed;  // Angle and speed of travel

    /**
     * Default constructor for WhiskerRobot.
     */
    public WhiskerRobot() {
        // TODO Auto-generated constructor stub
    }
    /**
     * Creates a whisker robot with specified parameters.
     * @param ix Initial X position.
     * @param iy Initial Y position.
     * @param ir Initial radius.
     * @param ia Initial angle.
     * @param is Initial speed.
     */
    public WhiskerRobot(double ix, double iy, double ir, double ia, double is) {
        super(ix, iy, ir);
        bAngle = ia;
        bSpeed = is;
    }

    /**
     * Draws the whisker robot, including wheels and whiskers, on the canvas.
     *
     * @param mc MyCanvas object used for drawing.
     */
    @Override
    public void drawRobot(MyCanvas mc) {
        mc.showCircle(x, y, rad, 'y');
        mc.Rightwheel(x, y, rad, 'x', bAngle);
        mc.Leftwheel(x, y, rad, 'x', bAngle);
        mc.showWhiskers(x, y, rad, col, bAngle);
    }

    /**
     * Checks the whisker robot's surroundings and adjusts its course if whiskers hit an obstacle.
     *
     * @param b RobotArena.
     */
    @Override
    protected void checkRobot(RobotArena b) {
        bAngle = b.CheckRobotAngle(x, y, rad, bAngle, RobotID);
        if (WhiskerCol(b)) {
            adjustCourse(bAngle);
        }
    }

    /**
     * Adjusts the course of the whisker robot based on a new angle.
     *
     * @param newAngle The new angle to adjust the course.
     */
    protected void adjustCourse(double newAngle) {
        double angleDiff = 30;
        double avoidanceAngle = (newAngle + angleDiff) % 360;
        bAngle = avoidanceAngle;
        double radAngle = bAngle * Math.PI / 180;
        x += bSpeed * Math.cos(radAngle);  // New X position
        y += bSpeed * Math.sin(radAngle);  // New Y position
    }
    /**
     * Adjusts the position of the whisker robot based on its current angle and speed.
     */
    @Override
    protected void adjustRobot() {
        double radAngle = bAngle * Math.PI / 180;  // Put angle in radians
        x += bSpeed * Math.cos(radAngle);         // New X position
        y += bSpeed * Math.sin(radAngle);         // New Y position
    }
    /**
     * Checks if the whisker robot's whiskers hit an obstacle in its surroundings.
     * @param b RobotArena.
     * @return True if whiskers hit an obstacle, false otherwise.
     */
    public boolean WhiskerCol(RobotArena b) {
        double whiskerLength = 50;
        double leftWhiskerAngle = bAngle + 35;
        double rightWhiskerAngle = bAngle - 35;

        double leftEndX = x + whiskerLength * Math.cos(Math.toRadians(leftWhiskerAngle));
        double leftEndY = y + whiskerLength * Math.sin(Math.toRadians(leftWhiskerAngle));
        double rightEndX = x + whiskerLength * Math.cos(Math.toRadians(rightWhiskerAngle));
        double rightEndY = y + whiskerLength * Math.sin(Math.toRadians(rightWhiskerAngle));

        for (Robot robot : b.GetAll()) {
            if (robot != this) {
                if (robot.hitting(leftEndX, leftEndY, 1) || robot.hitting(rightEndX, rightEndY, 1)) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * Gets the type of the robot as a string.
     * @return The type of the robot as a string.
     */
    protected String getStrType() {
        return "Whisker robot";
    }
}