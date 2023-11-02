package input_output;

public class Particle {
	
	private double x,y,z;
	
	public Particle(double xValue, double yValue, double zValue) {
		x = xValue;
		y = yValue;
		z = zValue;
	}
	
	public double[] GetXYZValues() {
		return new double[] { x, y, z };
	}
	
	public double GetX() {
		return x;
	}
	
	public double GetY() {
		return y;
	}
	
	public double GetZ() {
		return z;
	}
	
    @Override
    public String toString() {
        return "Particle:: x: "+x+" y: "+y+" z: "+z;
    }
	
}
