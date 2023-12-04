package computation;

import input_output.CSVReaderWriter;
import input_output.Particle;
import input_output.ParticleVolume;
import input_output.Triangle;
import parallel.Subtask;
import util.AppendableLinkedList;
import util.Combinations;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TriangleLocator {

    private static int N_THREADS;
    private static float MAX_DIST;
    private CSVReaderWriter readerWriter;

    public TriangleLocator() throws ExecutionException, InterruptedException {
        readerWriter = new CSVReaderWriter();
        ParticleVolume particleVolume = readerWriter.OpenDefaultFile();

        long startTime = System.currentTimeMillis();

        //AppendableLinkedList<Triangle> allTriangles = new AppendableLinkedList<>();
        int[] histogram = new int[(int)Math.ceil(MAX_DIST)];

        System.out.println("DEBUG: Creating thread and promise pools");
        ExecutorService threadPool = Executors.newFixedThreadPool(N_THREADS);
        Future<Subtask.Result>[] promises = new Future[N_THREADS];

        System.out.println("DEBUG: Computing the combination constants");
        long numCombinations = Combinations.choose(particleVolume.GetNumberOfParticles(), 3);
        long partitionSize = numCombinations/N_THREADS;

        System.out.println("DEBUG: Creating the subtasks");
        for (int i = 0; i < N_THREADS; i++) {
            System.out.println("DEBUG: Creating subtask " + i);
            long startIndex = i * partitionSize;
            long endIndex = (i == N_THREADS-1) ? numCombinations-1 : (i+1) * partitionSize - 1;
            promises[i] = threadPool.submit(new Subtask(particleVolume, startIndex, endIndex, MAX_DIST));
        }

        System.out.println("DEBUG: Joining the subtasks");
        try {
            for (Future<Subtask.Result> promise : promises) {
                System.out.println("DEBUG: Attempting to retrieve a subtask result");
                Subtask.Result result = promise.get();
                //allTriangles.append(result.getTriangles());

                for (int i = 0; i < histogram.length; i++) {
                    histogram[i] += result.getHistogram()[i];
                }
            }
        }catch (ExecutionException e){
            System.out.println("Could not retrieve a task (aborted). Cause: " + e.getCause());
        }

        threadPool.shutdown();

        System.out.println("DEBUG: Thread Pool shut down");

        long totalTime = System.currentTimeMillis() - startTime;
        System.out.println("Time Elapsed (ms): " + totalTime);

        System.out.println(Arrays.toString(histogram));

        System.out.println("DEBUG: histogram.length = " + histogram.length);
        //TODO Gage: Added statement below. This sets the histogram value in the writer to whatever you made in here.
        readerWriter.SetOutputHistogramValues(histogram);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MAX_DIST = Float.parseFloat(args[0]);
        N_THREADS = Integer.parseInt(args[1]);
        TriangleLocator triangleLocator = new TriangleLocator();
        //TODO Gage: Added statement below. This writes the file.
        triangleLocator.readerWriter.WriteDefaultFile();
    }

}
