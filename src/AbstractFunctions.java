import java.awt.Color;
import java.awt.Graphics2D;

public abstract class AbstractFunctions {
	protected final double INCREMENT = 0.1;
	private final int TWO_FIFTY_SIX = 256;
	protected final double DELTA_H = 0.001;
	protected final double ACCEPTABLE_ERROR_LIMIT = 0.01;
	protected final double DX = 0.001;
	protected final double ACCEPTABLE_ERROR = 0.0001;
	public static int numOfFunctions=0;
	protected String expression;
	protected boolean visible = true;
	protected Color color;
	protected int num =0;
	public String readExp = "";
	public boolean show = true;
	public AbstractFunctions(String expr) {
		this.expression = expr;
		color = randomColor();
	}
	public Color randomColor() {
		int R = (int)(Math.random()*TWO_FIFTY_SIX);
		int G = (int)(Math.random()*TWO_FIFTY_SIX);
		int B= (int)(Math.random()*TWO_FIFTY_SIX);
		Color color = new Color(R, G, B);
		return color;
	}
	public abstract Color getColor();
	public abstract void setColor(Color c);
	public abstract String getExpr();
	public abstract void setExpr(String s);
	public abstract double eval(double x);//consider divide by 0 situation
	public abstract String derivative(double x);
	public abstract double integral(double upper, double lower);
	public abstract String equal(Function f, double leftX, double rightX);
	public abstract String equal(double x, double leftX, double rightX);
	public abstract String toString();
	public abstract void drawFunction(Graphics2D g2d, int minX, int maxX, double f);
}

