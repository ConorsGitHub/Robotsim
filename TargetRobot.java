package Robotsim;

/**
 * Represents a target robot in the simulation.
 */
public class TargetRobot extends Robot {
	private static final long serialVersionUID =1L;

    private int score;  // Score associated with the target robot

    /**
     * Default constructor for TargetRobot.
     */
    public TargetRobot() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Creates a target robot with specified parameters.
     * @param ix Initial X position.
     * @param iy Initial Y position.
     * @param ir Initial radius.
     */
    public TargetRobot(double ix, double iy, double ir) {
        super(ix, iy, ir);
        score = 0;
        col = 'g';
    }
    /**
     * Checks if the target robot has been hit by any game robot and increases the score accordingly.
     * Also checks for collisions with blocker robots and removes them if hit.
     * @param b RobotArena.
     */
    @Override
    protected void checkRobot(RobotArena b) {
        if (b.checkHit(this)) score++;  // If hit by a game robot, increase the score
        for (Robot bs : b.GetAll()) {
            if (bs != this && bs instanceof BlockerRobot) {
                if (hitting(bs)) {
                    b.GetAll().remove(bs);  // Remove blocker robot if hit
                    break;
                }
            }
        }
    }

    /**
     * Draws the target robot and displays its score on the canvas.
     * @param mc MyCanvas object used for drawing.
     */
    public void drawRobot(MyCanvas mc) {
        super.drawRobot(mc);
        mc.showInt(x, y, score);
    }
    /**
     * Adjusts the target robot (currently, nothing to do).
     */
    @Override
    protected void adjustRobot() {
        // Nothing to do at the moment...
    }
    /**
     * Gets the type of the robot as a string.
     * @return The type of the robot as a string.
     */
    protected String getStrType() {
        return "Target";
    }
}