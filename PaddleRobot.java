package Robotsim;

public class PaddleRobot extends Robot {
	private static final long serialVersionUID =1L;
	//this class is for generating a circle that can be used to hit all other Robots
	public PaddleRobot() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param ix
	 * @param iy
	 * @param ir
	 */
	public PaddleRobot(double ix, double iy, double ir) {
		super(ix, iy, ir);
		col = 'b';
	}
	@Override
	protected void checkRobot(RobotArena b) {
	}

	@Override
	protected void adjustRobot() {
	}

	protected String getStrType() {
		return "Paddle";
	}	
}