package computation;

import input_output.CSVReaderWriter;
import input_output.Particle;
import input_output.ParticleVolume;
import input_output.Triangle;
import parallel.Subtask;
import util.AppendableLinkedList;
import util.Combinations;

import java.util.LinkedList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TriangleLocator {

    private final int N_THREADS = 16;
    private final double MAX_DIST = 10;

    private CSVReaderWriter readerWriter;

    public TriangleLocator() throws ExecutionException, InterruptedException {
        readerWriter = new CSVReaderWriter();
        ParticleVolume particleVolume = readerWriter.OpenFile();

        AppendableLinkedList<Triangle> allTriangles = new AppendableLinkedList<>();

        System.out.println("DEBUG: Creating thread and promise pools");
        ExecutorService threadPool = Executors.newFixedThreadPool(N_THREADS);
        Future<AppendableLinkedList<Triangle>>[] promises = new Future[N_THREADS];

        System.out.println("DEBUG: Computing the combination constants");
        int numCombinations = Combinations.choose(particleVolume.GetNumberOfParticles(), 3);
        int partitionSize = numCombinations/N_THREADS;

        System.out.println("DEBUG: Creating the subtasks");
        for (int i = 0; i < N_THREADS; i++) {
            System.out.println("DEBUG: Creating subtask " + i);
            int startIndex = i * partitionSize;
            int endIndex = (i == N_THREADS-1) ? numCombinations-1 : (i+1) * partitionSize - 1;
            promises[i] = threadPool.submit(new Subtask(particleVolume, startIndex, endIndex, MAX_DIST));
        }

        System.out.println("DEBUG: Joining the subtasks");
        try {
            for (Future<AppendableLinkedList<Triangle>> promise : promises) {
                System.out.println("DEBUG: Attempting to retrieve a subtask result");
                allTriangles.append(promise.get());
            }
        }catch (ExecutionException e){
            System.out.println("Could not retrieve a task (aborted). Cause: " + e.getCause());
        }


    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        new TriangleLocator();
    }

}