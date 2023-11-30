package input_output;

public class Particle {

    private float x,y,z;
    private int id;

    public Particle(float xValue, float yValue, float zValue, int id_number) {
        x = xValue;
        y = yValue;
        z = zValue;
        id = id_number;
    }

    public double[] GetXYZValues() {
        return new double[] { x, y, z };
    }

    public float GetX() {
        return x;
    }

    public float GetY() {
        return y;
    }

    public float GetZ() {
        return z;
    }

    @Override
    public String toString() {
        return "Particle:: x: "+x+" y: "+y+" z: "+z;
    }
}
