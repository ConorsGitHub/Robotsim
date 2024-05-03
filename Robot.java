package Robotsim;
import java.io.Serializable;
/**
 * Abstract class representing the functions necessary to make a robot.
 */
public abstract class Robot implements Serializable {
	private static final long serialVersionUID =1L;
    protected double x, y, rad;                       // Position and size of Robot
    protected char col;                               // Used to set color
    static int RobotCounter = 0;                       // Used to give each Robot a unique identifier
    protected int RobotID;                            // Unique identifier for the item
    /**
     * Default constructor for the Robot class. Initializes the robot with default values.
     */
    Robot() {
        this(100, 100, 10);
    }
    /**
     * Parameterized constructor for the Robot class.
     *
     * @param ix Initial X-coordinate of the robot.
     * @param iy Initial Y-coordinate of the robot.
     * @param ir Initial radius of the robot.
     */
    Robot(double ix, double iy, double ir) {
        x = ix;
        y = iy;
        rad = ir;
        RobotID = RobotCounter++;            // Set the identifier and increment class static
        col = 'r'; // Default color is red
    }
    /**
     * Gets the X-coordinate of the robot.
     *
     * @return The X-coordinate of the robot.
     */
    public double getX() {
        return x;
    }
    /**
     * Gets the Y-coordinate of the robot.
     *
     * @return The Y-coordinate of the robot.
     */
    public double getY() {
        return y;
    }
    /**
     * Gets the radius of the robot.
     *
     * @return The radius of the robot.
     */
    public double getRad() {
        return rad;
    }
    /**
     * Sets the X and Y coordinates of the robot.
     *
     * @param nx New X-coordinate.
     * @param ny New Y-coordinate.
     */
    public void setXY(double nx, double ny) {
        x = nx;
        y = ny;
    }
    /**
     * Gets the unique identifier of the robot.
     *
     * @return The unique identifier of the robot.
     */
    public int getID() {
        return RobotID;
    }
    /**
     * Draws the robot on the canvas using the provided MyCanvas object.
     *
     * @param mc MyCanvas object used for drawing.
     */
    public void drawRobot(MyCanvas mc) {
        mc.showCircle(x, y, rad, col);
    }
    /**
     * Gets the string type of the robot.
     * This method is meant for internal use within the class hierarchy.
     *
     * @return The string type of the robot.
     */
    protected String getStrType() {
        return "Robot";
    }
    /**
     * Converts the robot's information to a string.
     *
     * @return A string representation of the robot.
     */
    public String toString() {
        return getStrType() + " at " + Math.round(x) + ", " + Math.round(y);
    }
    /**
     * Checks the state of the robot in the given RobotArena.
     *
     * @param b RobotArena where the robot exists.
     */
    protected abstract void checkRobot(RobotArena b);
    /**
     * Adjusts the state of the robot.
     */
    protected abstract void adjustRobot();
    /**
     * Checks if the robot is hitting a circular object specified by its coordinates and radius.
     *
     * @param ox X-coordinate of the other object.
     * @param oy Y-coordinate of the other object.
     * @param or Radius of the other object.
     * @return True if the robot is hitting the specified object, false otherwise.
     */
    public boolean hitting(double ox, double oy, double or) {
        return (ox - x) * (ox - x) + (oy - y) * (oy - y) < (or + rad) * (or + rad);
    }
    /**
     * Checks if the robot is hitting another Robot object.
     *
     * @param oRobot The other Robot object.
     * @return True if the robot is hitting the other Robot, false otherwise.
     */
    public boolean hitting(Robot oRobot) {
        return hitting(oRobot.getX(), oRobot.getY(), oRobot.getRad());
    }
}