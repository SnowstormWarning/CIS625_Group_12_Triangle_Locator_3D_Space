package input_output;
import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class CSVReaderWriter {
	
	private File file;
	private JFileChooser fileBrowser;
	private FileNameExtensionFilter filter;
	private BufferedReader reader;
	private FileWriter writer;
	
	private ParticleVolume readVolume;
	
	public CSVReaderWriter() {
		//Constructor
		fileBrowser= new JFileChooser();
		filter = new FileNameExtensionFilter("CSV Files", "csv");
		fileBrowser.setFileFilter(filter);
	}
	
	// 
	public ParticleVolume OpenFile() {
		
		//SOURCE (How Use File Explorer):: https://genuinecoder.com/how-to-open-file-explorer-in-java/
		//SOURCE (JFileChooser Usage):: https://stackoverflow.com/questions/796743/how-do-i-add-a-file-browser-inside-my-java-application
		//SOURCE (JFileChooser Filter):: https://stackoverflow.com/questions/15771949/how-do-i-make-jfilechooser-only-accept-txt
		
		int option = fileBrowser.showOpenDialog(null);
		if (option != JFileChooser.APPROVE_OPTION) return null;
		file = fileBrowser.getSelectedFile();
		System.out.println(file.getPath());
		
		try {
			ReadFile();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return readVolume;
	}
	
	private void ReadFile() throws Exception {
		//SOURCE (FileReader By Line Reading):: https://www.digitalocean.com/community/tutorials/java-read-file-line-by-line
			reader = new BufferedReader(new FileReader(file.getPath()));
			
			String sizing = reader.readLine();
			String[] sizes = sizing.split(",");
			
			double xLength,yLength,zLength;
			
			if(sizes.length == 3) {
				xLength = Double.parseDouble(sizes[0]);
				yLength = Double.parseDouble(sizes[1]);
				zLength = Double.parseDouble(sizes[2]);
			}
			else {
				throw new Exception("Input CSV File Ill Formatted, First line must be the dimensions of the rectangluar volume (ie \"500, 500, 500\"");
			}
			
			String particleData;
			List<Particle> particles = new ArrayList<Particle>();
			String[] particleCords;
			double x,y,z;
			
			//TODO Might Need to Parallelize this Loop.
			while((particleData = reader.readLine()) != null) {
				particleCords = particleData.split(",");
				System.out.println(particleData);
				if(particleCords.length == 3) {
					
					x = Double.parseDouble(particleCords[0]);
					y = Double.parseDouble(particleCords[1]);
					z = Double.parseDouble(particleCords[2]);
					System.out.println(xLength+","+yLength+","+zLength);
					if(x < 0 || y < 0 || z < 0 || x > xLength || y > yLength || z > zLength)
						throw new Exception("Input CSV File Ill Formatted, Data lines must contain position cordinates that fall within the dimensions of the rectangular volume described on the first line in the file.");

					
					particles.add(new Particle(x, y, z));
					
				}
				else {
					throw new Exception("Input CSV File Ill Formatted, Data lines must be the 3D position of the particle (ie \"500, 500, 500\"");
				}
			}
			
			readVolume = new ParticleVolume(xLength,yLength,zLength,particles);
	}
}
