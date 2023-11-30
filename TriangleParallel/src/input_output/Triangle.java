package input_output;

public class Triangle {

    private Particle p1;
    private Particle p2;
    private Particle p3;
    public Triangle(Particle p1, Particle p2, Particle p3){
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    public Particle getPoint1(){
        return p1;
    }

    public Particle getPoint2(){
        return p2;
    }

    public Particle getPoint3(){
        return p3;
    }
}
