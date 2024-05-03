package Robotsim;

/**
 * BumperRobot class represents a robot equipped with a bumper ring that reverses direction upon collision.
 */
public class BumperRobot extends Robot {
	private static final long serialVersionUID =1L;

    double bumperRad, bAngle, bSpeed; // Bumper parameters
    /**
     * Default constructor for BumperRobot.
     */
    public BumperRobot() {
        // TODO: Implement if needed
    }
    /**
     * Creates a BumperRobot with specified parameters.
     *
     * @param ix           Initial x-coordinate of the robot.
     * @param iy           Initial y-coordinate of the robot.
     * @param ir           Robot's radius.
     * @param bumperRadius Radius of the bumper ring.
     * @param is           Robot's speed.
     * @param ia           Initial angle of the robot's movement.
     */
    public BumperRobot(double ix, double iy, double ir, double bumperRadius, double is, double ia) {
        super(ix, iy, ir);
        bSpeed = is;
        bAngle = ia;
        bumperRad = bumperRadius;
        col = 'b'; // Set a default color or make it configurable
    }

    /**
     * Draws the BumperRobot on the canvas, including the bumper ring.
     *
     * @param mc MyCanvas object for drawing.
     */
    @Override
    public void drawRobot(MyCanvas mc) {
        mc.showCircle(x, y, rad, col);
        mc.Rightwheel(x, y, rad, 'x', bAngle);
        mc.Leftwheel(x, y, rad, 'x', bAngle);
        mc.gc.setStroke(mc.colFromChar('g')); // Set bumper color to blue ('b')
        mc.gc.setLineWidth(2); // Set the width of the bumper ring
        mc.gc.strokeOval(x - bumperRad, y - bumperRad, this.bumperRad * 2, this.bumperRad * 2); // Draw the bumper as a ring
    }
    /**
     * Checks for collisions with other robots and reverses direction upon collision.
     *
     * @param b RobotArena containing all robots.
     */
    @Override
    protected void checkRobot(RobotArena b) {
        bAngle = b.CheckRobotAngle(x, y, rad, bAngle, RobotID);

        // Check for collisions with other robots
        for (Robot other : b.GetAll()) {
            if (other != this && Math.hypot(other.getX() - x, other.getY() - y) < bumperRad + other.getRad()) {
                bAngle += 180; // Reverse direction
                break; // Only handle one collision per check
            }
        }
        // Update position based on angle and speed
        x += bSpeed * Math.cos(Math.toRadians(bAngle));
        y += bSpeed * Math.sin(Math.toRadians(bAngle));
    }
    /**
     * Gets a string representation of the type of the robot.
     *
     * @return String representing the type of the robot.
     */
    @Override
    protected String getStrType() {
        return "Bumper";
    }
    @Override
    protected void adjustRobot() {
        // TODO: Implement if needed
    }
}