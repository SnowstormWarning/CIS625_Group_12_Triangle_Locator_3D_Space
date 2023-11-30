package input_output;

import java.util.Map;

public class ParticleVolume {

    private float xLength, yLength, zLength;
    private Map<Integer, Particle> Particles;
    private long numberOfLines;

    public ParticleVolume(float xVolumeLength, float yVolumeLength, float zVolumeLength, Map<Integer, Particle> ParticleList) {
        xLength = xVolumeLength;
        yLength = yVolumeLength;
        zLength = zVolumeLength;
        Particles = ParticleList;
    }

    public int GetNumberOfParticles() {
        return Particles.size();
    }

    public double[] GetVolumeLengths() {
        return new double[] {xLength, yLength, zLength};
    }

    public double GetXLength() {
        return xLength;
    }

    public double GetYLength() {
        return yLength;
    }

    public double GetZLength() {
        return zLength;
    }

    public Particle GetParticle(int index) {
        return Particles.get(index);
    }

    public Map<Integer, Particle> GetParticleMap() {
        return Particles;
    }

    @Override
    public String toString() {
        String output = "ParticleVolume:: \n   Dimensions: x: "+xLength+" y: "+yLength+" z: "+zLength+"\n";
        for(Particle particle : Particles.values()){
            output += "   "+particle.toString()+"\n";
        }
        return output;
    }
}
