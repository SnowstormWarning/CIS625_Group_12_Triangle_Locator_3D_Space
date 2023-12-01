package input_output;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class CSVReaderWriter {
	
	private String outputFileName = "output";
    private File inFile;
    private JFileChooser inFileBrowser;
    private FileNameExtensionFilter inFilter;
    private BufferedReader inReader;
    private File outFile;
    private JFileChooser outFileBrowser;
    private FileNameExtensionFilter outFilter;
    private FileWriter outWriter;
    private int[] outputHistogramValues;

    private ParticleVolume readVolume;

    public CSVReaderWriter() {
        //Constructor
        inFileBrowser= new JFileChooser();
        inFilter = new FileNameExtensionFilter("CSV Files", "csv");
        JFileChooser outFileBrowser = new JFileChooser();
        outFileBrowser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        inFileBrowser.setFileFilter(inFilter);
    }

    //
    public ParticleVolume OpenFile() {

        //SOURCE (How Use File Explorer):: https://genuinecoder.com/how-to-open-file-explorer-in-java/
        //SOURCE (JFileChooser Usage):: https://stackoverflow.com/questions/796743/how-do-i-add-a-file-browser-inside-my-java-application
        //SOURCE (JFileChooser Filter):: https://stackoverflow.com/questions/15771949/how-do-i-make-jfilechooser-only-accept-txt

        int option = inFileBrowser.showOpenDialog(null);
        if (option != JFileChooser.APPROVE_OPTION) return null;
        inFile = inFileBrowser.getSelectedFile();
        System.out.println(inFile.getPath());

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
        inReader = new BufferedReader(new FileReader(inFile.getPath()));

        String sizing = inReader.readLine();
        String[] sizes = sizing.split(",");

        float xLength,yLength,zLength;

        if(sizes.length == 3) {
            xLength = Float.parseFloat(sizes[0]);
            yLength = Float.parseFloat(sizes[1]);
            zLength = Float.parseFloat(sizes[2]);
        }
        else {
            throw new Exception("Input CSV File Ill Formatted, First line must be the dimensions of the rectangluar volume (ie \"500, 500, 500\"");
        }

        String particleData;
        Map<Integer, Particle> particles = new HashMap<>();
        String[] particleCords;
        float x,y,z;

        //TODO Might Need to Parallelize this Loop.
        int id = 0;
        while((particleData = inReader.readLine()) != null) {
            particleCords = particleData.split(",");
            System.out.println(particleData);
            if(particleCords.length == 3) {

                x = Float.parseFloat(particleCords[0]);
                y = Float.parseFloat(particleCords[1]);
                z = Float.parseFloat(particleCords[2]);
                System.out.println(xLength+","+yLength+","+zLength);
                if(x < 0 || y < 0 || z < 0 || x > xLength || y > yLength || z > zLength)
                    throw new Exception("Input CSV File Ill Formatted, Data lines must contain position cordinates that fall within the dimensions of the rectangular volume described on the first line in the file.");


                particles.put(id, new Particle(x, y, z, id));
                id++;

            }
            else {
                throw new Exception("Input CSV File Ill Formatted, Data lines must be the 3D position of the particle (ie \"500, 500, 500\"");
            }
        }

        readVolume = new ParticleVolume(xLength,yLength,zLength,particles);
    }
    
    public void WriteFile() { //TODO: Writes to the given folder. The file name is always equal to this.outputFileName ("output").
		System.out.println("Provide the folder you want the CSV to be in. \n");
		
		//Until we have a valid input.
		boolean haveValidFile = false;
		while(!haveValidFile) {
			//Request a folder from the user.
	        int option = outFileBrowser.showOpenDialog(null);
	        
	        //If the option is valid. Break the while loop, else, tell them to try again.
			if (option == JFileChooser.APPROVE_OPTION) haveValidFile = true; else System.out.println("Invalid Option.\nProvide the folder you want the CSV to be in. \n");
			
		} // End of request loop.
		
		//Output file is equal to the selected one by the user.
		outFile = outFileBrowser.getSelectedFile();
		
		//Writer Try loop to catch exceptions.
		try {
			//Writer targets the choosen folder .../output.csv
			FileWriter writer = new FileWriter(outFile+"/"+outputFileName+".csv");
			
			//Add column labels to csv file.
			writer.append("Side Distance, Frequency\n");
			
			// Foreach value in the outputHistogramValues
			for(int i = 0; i < outputHistogramValues.length; i++) {
				
				//Debug
				System.out.println("Writing... "+i+","+outputHistogramValues[i]);
				
				//Add the values to the file.
				writer.append(i+","+outputHistogramValues[i]);
				
				//If we are not at the end of the histogram values.
				if(i != outputHistogramValues.length-1) {
					//Then we add a new line character for the next set of values
					writer.append("\n");
				}
				
				//Debug
				System.out.println("Done. i="+i+" oHV.L="+outputHistogramValues.length);
			}// end of for loop
			
			//Close the writer.
			writer.close();
			
			//debug
			System.out.println("Writer Closed");
			
		//end of try, start of catch
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // end of catch
    }//end of write file.
    
    public void SetOutputHistogramValues(int[] histogram) {
    	outputHistogramValues = histogram;
    }
}
