package generation;

import input_output.Particle;

import javax.swing.JFileChooser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class VolumeMaker {
    public static void main(String[] args) {
        double xL,yL,zL;
        double x,y,z;
        String fileName;
        File dir;
        int n;
        GParticle[] particles;
//        JFileChooser fileBrowser = new JFileChooser();
//
//        System.out.println("Provide the folder you want the CSV to be in. \n");
//        fileBrowser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//        int option = fileBrowser.showOpenDialog(null);
//        if (option != JFileChooser.APPROVE_OPTION) return;
//        dir = fileBrowser.getSelectedFile();
        dir = new File(".");
        System.out.println("Provide the name of the CSV to be made. ::");
        Scanner scanner = new Scanner(System.in);
        fileName = scanner.next();
        System.out.println("X Length of the Volume? :: ");
        xL = scanner.nextDouble();
        System.out.println("Y Length of the Volume? :: ");
        yL = scanner.nextDouble();
        System.out.println("Z Length of the Volume? :: ");
        zL = scanner.nextDouble();
        System.out.println("Number of Particles (Please note, particles will not occupy the same space).");
        n = scanner.nextInt();

        GParticle cur;
        Random rand = new Random();
        particles = new GParticle[(int) n];
        for(int i = 0; i < n; i++) {
            cur = new GParticle(rand.nextDouble()*xL,rand.nextDouble()*yL,rand.nextDouble()*zL);
            while(AreDupes(cur,i-1,particles)) {
                cur = new GParticle(rand.nextDouble()*xL,rand.nextDouble()*yL,rand.nextDouble()*zL);
            }
            particles[i] = cur;

        }


        try {
            FileWriter writer = new FileWriter(dir+"/"+fileName+".csv");
            writer.append(xL+","+yL+","+zL+"\n");
            for(int i = 0; i < n; i++) {
                writer.append(particles[i].GetX()+","+particles[i].GetY()+","+particles[i].GetZ());
                if(i != n-1) {
                    writer.append("\n");
                }
            }

            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("Finished");
    }

    public static boolean AreDupes(GParticle part, int curListLength, GParticle[] list) {
        for(int i = 0; i < curListLength; i++) {
            if(part.GetXYZValues() == list[i].GetXYZValues()) return true;
        }
        return false;
    }
}
