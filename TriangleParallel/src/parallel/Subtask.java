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

public class Subtask implements Callable<Subtask.Result> {

    public class Result {
        private AppendableLinkedList<Triangle> triangles;
        private int[] histogram;

        public Result(AppendableLinkedList<Triangle> triangles, int[] histogram) {
            this.triangles = triangles;
            this.histogram = histogram;
        }

        public AppendableLinkedList<Triangle> getTriangles() {
            return triangles;
        }

        public int[] getHistogram() {
            return histogram;
        }
    }

    private ParticleVolume particleVolume;
    private int startIndex, endIndex;
    private double maxDist;
    public Subtask(ParticleVolume particleVolume, int startIndex, int endIndex, double maxDist){
        this.particleVolume = particleVolume;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.maxDist = maxDist;

        System.out.println("DEBUG: Subtask(" + startIndex + ", " + endIndex + ", " + maxDist + ") created");
    }

    @Override
    public Result call() throws Exception {
        AppendableLinkedList<Triangle> triangles = new AppendableLinkedList<>();
        int[] histogram = new int[(int)Math.ceil(maxDist)];
        for (int i = startIndex; i <= endIndex; i++) {
            // Compute the i-th combination of N choose 3 (N = # of particles)
            int[] ci = Combinations.getCombination(particleVolume.GetNumberOfParticles(), 3, i+1);

            // Get the respective points
            Particle p1 = particleVolume.GetParticle(ci[0]-1);
            Particle p2 = particleVolume.GetParticle(ci[1]-1);
            Particle p3 = particleVolume.GetParticle(ci[2]-1);

            double d1 = DistanceMeasurement.distance(p1,p2);
            double d2 = DistanceMeasurement.distance(p1,p3);
            double d3 = DistanceMeasurement.distance(p2,p3);

            // If the distance between any two particles is too large, move on
            if(d1 > maxDist || d2 > maxDist || d3 > maxDist)
                continue;

            // Add to histogram
            histogram[(int)d1]++;
            histogram[(int)d2]++;
            histogram[(int)d3]++;

            // Form and add the triangle created
            triangles.add(new Triangle(p1,p2,p3));
        }
        return new Result(triangles, histogram);
    }
}
