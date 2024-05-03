package Robotsim;

/**
 * although named BlockerRobot this class represents an obstacle, it is randomly placed on the canvas, cannot touch target or it is removed
 */
public class BlockerRobot extends Robot {
	private static final long serialVersionUID =1L;
    /**
     * Default constructor for BlockerRobot.
     */
    public BlockerRobot() {
        // TODO Auto-generated constructor stub
    }
    /**
     * Creates a BlockerRobot with specified parameters.
     *
     * @param ix Initial x-coordinate of the robot.
     * @param iy Initial y-coordinate of the robot.
     * @param ir Robot's radius.
     */
    public BlockerRobot(double ix, double iy, double ir) {
        super(ix, iy, ir);
        col = 'o'; // Set color to orange for a blocker robot
    }
    /**
     * This method is called to check for interactions with other robots in the arena although it does not do anything as it does not move
     * @param b RobotArena containing all robots.
     */
    @Override
    protected void checkRobot(RobotArena b) {
        // Blocker robot does not perform any checks
    }
    /**
     * This method is called to adjust the position or behavior of the robot.
     * For a BlockerRobot, there is no movement, so this method does nothing.
     */
    @Override
    protected void adjustRobot() {
        // Blocker robot does not move, nothing to adjust
    }
    /**
     * Gets a string representation of the type of the robot.
     * For a BlockerRobot, the type is "Blocker".
     *
     * @return String representing the type of the robot.
     */
    @Override
    protected String getStrType() {
        return "Blocker";
    }
}