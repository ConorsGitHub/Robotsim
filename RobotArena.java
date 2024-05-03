package Robotsim;

import java.util.ArrayList;
import java.util.Random;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
/**
 * Holds the robot array, the functions to add robots and and manipulate them
 */
public class RobotArena implements Serializable{
	private static final long serialVersionUID =1L;
    double xSize, ySize;                        // Size of the arena
    private ArrayList<Robot> allRobots;         // ArrayList of all Robots in the arena
    Random rand = new Random();
    private Robot selectedRobot;
    /** Default constructor for RobotArena with default size.*/
    RobotArena() {
        this(500, 400);            // Default size
    }
    /**
     * @param xS Initial X size of the arena.
     * @param yS Initial Y size of the arena.
     */
    RobotArena(double xS, double yS) {
        xSize = xS;
        ySize = yS;
        allRobots = new ArrayList<Robot>();                    // List of all Robots, initially empty
        allRobots.add(new TargetRobot(xS / 2, 150, 15));       // Add target Robot
        allRobots.add(new PaddleRobot(xS / 2, yS - 20, 20));   // Add paddle
    }

    /**
     * Gets the X size of the arena.
     *
     * @return The X size of the arena.
     */
    public int loadFile(String fname) {
        int status = 0;
        allRobots.clear();
        try {
            FileInputStream fileInputStream = new FileInputStream(fname);                    // set up input stream
            ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);        // and file
            Robotsim.RobotArena A = (Robotsim.RobotArena) inputStream.readObject();                            // read whole object
            this.xSize = A.xSize;                                                            // load into arena
            this.ySize = A.ySize;
            this.allRobots = A.allRobots;
            inputStream.close();                                                            // close...
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            status = 1;
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
            status = 2;
        }
        return status;
    }

    /**
     * save the arena and its contents into the named file
     *
     * @param fname
     * @return 0 if ok
     */
    public int saveFile(String fname) {
        int status = 0;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fname);                // setup
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            outputStream.writeObject(this);                                                // write arena and contents
            outputStream.close();                                                            // close
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            status = 1;
        }
        return status;
    }
    public double getXSize() {
        return xSize;
    }

    /**
     * Gets the Y size of the arena.
     *
     * @return The Y size of the arena.
     */
    public double getYSize() {
        return ySize;
    }

    /**
     * Draws the arena and all Robots in it using the provided MyCanvas object.
     *
     * @param mc MyCanvas object used for drawing.
     */
    public void drawArena(MyCanvas mc) {
        for (Robot b : allRobots) b.drawRobot(mc);        // Draw all Robots
    }

    /**
     * Checks the state of all Robots in the arena.
     */
    public void checkRobots() {
        for (Robot b : allRobots) b.checkRobot(this);    // Check all Robots
    }

    /**
     * Adjusts the state of all Robots in the arena.
     */
    public void adjustRobots() {
        for (Robot b : allRobots) b.adjustRobot();
    }

    /**
     * Gets the Robot at the specified position.
     *
     * @param x The X-coordinate.
     * @param y The Y-coordinate.
     * @return The robot at the given position, or null if no robot is found.
     */
    public Robot getRobotAtPosition(double x, double y) {
        for (Robot robot : allRobots) {
            double distance = Math.hypot(robot.getX() - x, robot.getY() - y);
            if (distance <= robot.getRad()) {
                return robot; // Robot found at the position
            }
        }
        return null; // No robot found at the position
    }

    /**
     * Sets the selected robot.
     *
     * @param robot The robot to be set as selected.
     */
    public void setSelectedRobot(Robot robot) {
        this.selectedRobot = robot;
    }

    /**
     * Gets the selected robot.
     *
     * @return The selected robot.
     */
    public Robot getSelectedRobot() {
        return this.selectedRobot;
    }

    /**
     * Describes all Robots in the arena.
     *
     * @return ArrayList of strings describing each Robot.
     */
    public ArrayList<String> describeAll() {
        ArrayList<String> ans = new ArrayList<String>();        // Set up empty ArrayList
        for (Robot b : allRobots) ans.add(b.toString());         // Add string defining each Robot
        return ans;                                                // Return string list
    }

    /**
     * Checks and adjusts the angle of a Robot based on its position and size.
     *
     * @param x     Robot X position.
     * @param y     Robot Y position.
     * @param rad   Robot radius.
     * @param ang   Current angle.
     * @param notID Identifier of Robot not to be checked.
     * @return New angle.
     */
    public double CheckRobotAngle(double x, double y, double rad, double ang, int notID) {
        double ans = ang;
        if (x < rad || x > xSize - rad) ans = 180 - ans;
        // If Robot hit (tried to go through) left or right walls, set mirror angle, being 180-angle
        if (y < rad || y > ySize - rad) ans = -ans;
        // If try to go off top or bottom, set mirror angle
        for (Robot b : allRobots)
            if (b.getID() != notID && b.hitting(x, y, rad))
                ans = 180 * Math.atan2(y - b.getY(), x - b.getX()) / Math.PI;
        // Check all Robots except one with the given id
        // If hitting, return angle between the other Robot and this one.
        return ans;        // Return the angle
    }

    /**
     * Checks if a target Robot is hit by any GameRobot in the arena.
     * @param target The target Robot.
     * @return True if hit, false otherwise.
     */
    public boolean checkHit(Robot target) {
        boolean ans = false;
        for (Robot b : allRobots)
            if (b instanceof GameRobot && b.hitting(target)) ans = true;
        // Try all Robots, if GameRobot, check if hitting the target
        return ans;
    }
    /**
     * Checks if a target Robot is hit by any GameRobot in the arena and removes the hitting Robot.
     *
     * @param EvilRobot The target Robot to be hit.
     */
    public void checkHit2(Robot EvilRobot) {
        for (Robot b : allRobots) {
            if ((b instanceof GameRobot && b.hitting(EvilRobot))) {
                allRobots.remove(b);
            }
        }
    }

    /**
     * Deletes a specified Robot from the arena.
     *
     * @param robot The Robot to be deleted.
     */
    public void deleteRobot(Robot robot) {
        allRobots.remove(robot);
    }
    /**
     * Adds a GameRobot to the arena with random initial values.
     */
    public void addGRobot() {
        allRobots.add(new GameRobot(rand.nextInt(400), rand.nextInt(400), 10, rand.nextInt(359 + 1), 6));
    }
    /**
     * Adds a PaddleRobot to the arena with random initial values.
     */
    public void addPRobot() {
        allRobots.add(new PaddleRobot(rand.nextInt(400), rand.nextInt(400), 10));
    }
    /**
     * Adds an EvilRobot to the arena with random initial values.
     */
    public void addERobot() {
        allRobots.add(new EvilRobot(rand.nextInt(400), rand.nextInt(400), 10, rand.nextInt(359 + 1), 6));
    }
    /**
     * Adds a BlockerRobot to the arena with random initial values.
     */
    public void addBRobot() {
        allRobots.add(new BlockerRobot(rand.nextInt(400), rand.nextInt(400), rand.nextInt(20) + 10));
    }
    /**
     * Gets the list of all Robots in the arena.
     *
     * @return ArrayList of all Robots in the arena.
     */
    public ArrayList<Robot> GetAll() {
        return allRobots;
    }
    /*** Adds a WhiskerRobot to the arena with random initial values.*/
    public void addWRobot() {
        allRobots.add(new WhiskerRobot(rand.nextInt(400), rand.nextInt(400), 15, rand.nextInt(359 + 1), 3));
    }
    /*** Adds a BeamRobot to the arena with random initial values.*/
    public void addBeRobot() {
        allRobots.add(new BeamRobot(rand.nextInt(400), rand.nextInt(400), 15, rand.nextInt(359 + 1), 3, 60));
    }
    /*** Adds a BumperRobot to the arena with random initial values.*/
    public void addBuRobot() {
        allRobots.add(new BumperRobot(rand.nextInt(400), rand.nextInt(400), 20, 30, 3, rand.nextInt(359 + 1)));
    }
    /*** Adds a SliderRobot to the arena with random initial values.*/
    public void addSRobot() {
        allRobots.add(new SliderRobot(rand.nextInt(400), rand.nextInt(400), 10, rand.nextInt(359 + 1), 6));
    }
}