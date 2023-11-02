package computation;

import input_output.CSVReaderWriter;
import input_output.ParticleVolume;

public class TriangleLocator {
	
	private static CSVReaderWriter readerWriter;

	public static void main(String args[]) {
		
		readerWriter = new CSVReaderWriter();
		ParticleVolume particleVolume = readerWriter.OpenFile(); // Asks user for file, opens, and reads it. Returns Particle Volume.
		
		ReadingFileTest(particleVolume);
	}
	
	public static void ReadingFileTest(ParticleVolume particleVolume) {
		//Some Example Output and Usage of the Definitions I made! ~Gage H.
		System.out.println(particleVolume.GetXLength());
		System.out.println(particleVolume.GetXLength());
		System.out.println(particleVolume.GetXLength());
		System.out.println(particleVolume.GetNumberOfParticles());
		System.out.println(particleVolume.GetParticle(0));
		System.out.println(particleVolume);
	}
}
