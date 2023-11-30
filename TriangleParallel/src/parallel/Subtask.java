package parallel;

import input_output.Particle;
import input_output.ParticleVolume;
import input_output.Triangle;
import util.AppendableLinkedList;
import util.Combinations;
import util.DistanceMeasurement;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.Callable;

public class Subtask implements Callable<AppendableLinkedList<Triangle>> {

    private ParticleVolume particleVolume;
    private int startIndex, endIndex;
    private double maxDist;
    public Subtask(ParticleVolume particleVolume, int startIndex, int endIndex, double maxDist){
        this.particleVolume = particleVolume;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.maxDist = maxDist*maxDist;

        System.out.println("DEBUG: Subtask(" + startIndex + ", " + endIndex + ", " + maxDist + ") created");
    }

    @Override
    public AppendableLinkedList<Triangle> call() throws Exception {
        AppendableLinkedList<Triangle> triangles = new AppendableLinkedList<>();
        for (int i = startIndex; i <= endIndex; i++) {
            // Compute the i-th combination of N choose 3 (N = # of particles)
            int[] ci = Combinations.getCombination(particleVolume.GetNumberOfParticles(), 3, i+1);

            // Get the respective points
            Particle p1 = particleVolume.GetParticle(ci[0]-1);
            Particle p2 = particleVolume.GetParticle(ci[1]-1);
            Particle p3 = particleVolume.GetParticle(ci[2]-1);

            // If the distance between any two particles is too large, move on
            if(DistanceMeasurement.distanceSquared(p1,p2) > maxDist ||
                DistanceMeasurement.distanceSquared(p1,p3) > maxDist ||
                DistanceMeasurement.distanceSquared(p2,p3) > maxDist)
                continue;

            // Form and add the triangle created
            triangles.add(new Triangle(p1,p2,p3));
        }
        return triangles;
    }
}
