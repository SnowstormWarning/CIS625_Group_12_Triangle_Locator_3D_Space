package computation;

import input_output.CSVReaderWriter;
import input_output.ParticleVolume;
import parallel.Subtask;
import util.Combinations;

import java.util.Arrays;

public class TriangleLocatorSerial {

    private static float MAX_DIST;
    private CSVReaderWriter readerWriter;

    public TriangleLocatorSerial() throws Exception {
        readerWriter = new CSVReaderWriter();
        ParticleVolume particleVolume = readerWriter.OpenDefaultFile();

        long startTime = System.currentTimeMillis();

        System.out.println("DEBUG: Computing the combination constants");
        long numCombinations = Combinations.choose(particleVolume.GetNumberOfParticles(), 3);

        Subtask subtask = new Subtask(particleVolume, 0, numCombinations-1, MAX_DIST);
        Subtask.Result result = subtask.call();

        long totalTime = System.currentTimeMillis() - startTime;
        System.out.println("Time Elapsed (ms): " + totalTime);

        System.out.println(Arrays.toString(result.getHistogram()));
        //TODO Gage: Added statement below. This sets the histogram value in the writer to whatever you made in here.
        readerWriter.SetOutputHistogramValues(result.getHistogram());
    }

    public static void main(String[] args) throws Exception {
        MAX_DIST = Float.parseFloat(args[0]);
        TriangleLocatorSerial triangleLocator = new TriangleLocatorSerial();
        //TODO Gage: Added statement below. This writes the file.
        triangleLocator.readerWriter.WriteDefaultFile();
    }
}
