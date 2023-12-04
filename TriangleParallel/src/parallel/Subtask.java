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
        //private AppendableLinkedList<Triangle> triangles;
        private int[] histogram;

        public Result(int[] histogram) {
            //this.triangles = triangles;
            this.histogram = histogram;
        }

        public int[] getHistogram() {
            return histogram;
        }
    }

    private ParticleVolume particleVolume;
    private long startIndex, endIndex;
    private float maxDist;
    public Subtask(ParticleVolume particleVolume, long startIndex, long endIndex, float maxDist){
        this.particleVolume = particleVolume;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.maxDist = maxDist*maxDist;

        System.out.println("DEBUG: Subtask(" + startIndex + ", " + endIndex + ", " + maxDist + ") created");
    }

    @Override
    public Result call() throws Exception {
        //AppendableLinkedList<Triangle> triangles = new AppendableLinkedList<>();
        int[] histogram = new int[(int)Math.ceil(maxDist)];
        System.out.println("Thread " + Thread.currentThread().getName() + " call");
        for (long i = startIndex; i <= endIndex; i++) {
            // Compute the i-th combination of N choose 3 (N = # of particles)
            int[] ci = Combinations.getCombination(particleVolume.GetNumberOfParticles(), 3, i+1);

            // Get the respective points
            Particle p1 = particleVolume.GetParticle(ci[0]-1);
            Particle p2 = particleVolume.GetParticle(ci[1]-1);
            Particle p3 = particleVolume.GetParticle(ci[2]-1);

            float d1 = DistanceMeasurement.distanceSquared(p1,p2);
            if(d1 > maxDist) continue;

            float d2 = DistanceMeasurement.distanceSquared(p1,p3);
            if(d2 > maxDist) continue;

            float d3 = DistanceMeasurement.distanceSquared(p2,p3);
            if(d3 > maxDist) continue;

            d1 = (float)Math.sqrt(d1);
            d2 = (float)Math.sqrt(d2);
            d3 = (float)Math.sqrt(d3);

            // Add to histogram
            histogram[(int)d1]++;
            histogram[(int)d2]++;
            histogram[(int)d3]++;

            // Form and add the triangle created
            //triangles.add(new Triangle(p1,p2,p3));
        }
        return new Result(histogram);
    }
}
