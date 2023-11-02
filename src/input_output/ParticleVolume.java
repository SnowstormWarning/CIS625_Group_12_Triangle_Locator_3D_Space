package input_output;

import java.util.List;

public class ParticleVolume {
	
	private double xLength, yLength, zLength;
	private List<Particle> Particles;
	
	public ParticleVolume(double xVolumeLength, double yVolumeLength, double zVolumeLength, List<Particle> ParticleList) {
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
	
	public List<Particle> GetParticleList() {
		return Particles;
	}
	
    @Override
    public String toString() {
    	String output = "ParticleVolume:: \n   Dimensions: x: "+xLength+" y: "+yLength+" z: "+zLength+"\n";
    	for(int i = 0; i < Particles.size(); i++) {
    		output += "   "+Particles.get(i).toString()+"\n";
    	}
        return output;
    }
}
