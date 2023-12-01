package util;

import input_output.Particle;

public class DistanceMeasurement {

    /**
     * Computes the square distance between two particles
     * @param p1 Particle 1
     * @param p2 Particle 2
     * @return The distance squared between Particles 1 and 2
     */
    public static float distanceSquared(Particle p1, Particle p2){
        // TODO Consider using floats instead to save time. How precise do we need to be?
        float dx = p2.GetX() - p1.GetX();
        float dy = p2.GetY() - p1.GetY();
        float dz = p2.GetZ() - p1.GetZ();
        return dx*dx + dy*dy + dz*dz;
    }

    /**
     * Computes the distance between two particles
     * @param p1 Particle 1
     * @param p2 Particle 2
     * @return The distance between Particles 1 and 2
     */
    public static double distance(Particle p1, Particle p2){
        // TODO Consider using floats instead to save time. How precise do we need to be?
        float dx = p2.GetX() - p1.GetX();
        float dy = p2.GetY() - p1.GetY();
        float dz = p2.GetZ() - p1.GetZ();
        return Math.sqrt(dx*dx + dy*dy + dz*dz);
    }
}
