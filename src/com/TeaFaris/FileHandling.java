package com.TeaFaris;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.jar.JarFile;

import static com.TeaFaris.Start.*;

public class FileHandling {
    public static final String FORMAT = ".wav";

    public String[] setAndGetSoundNames() throws IOException {
        int numberOfSounds = getNumberOfSounds();
        String[] soundNames = new String[numberOfSounds];
        int c = 0;
        while (c <= numberOfSounds-1) {
            soundNames[c] = (c+1) + FORMAT;
            c++;
        }
        return soundNames;
    }

    public void extractSounds(String[] soundNames, String pathOut) throws IOException {
        int a = 0;
        while (a <= getNumberOfSounds() - 1) {
            extractSound(SOUNDS_PATH + soundNames[a], pathOut + "/" + soundNames[a]);
            a++;
        }
    }
    // This part of the code reads sounds and copies them into folders
    private void extractSound(String pathIn, String pathOut) throws IOException {
        InputStream in = getClass().getResourceAsStream(pathIn);
        if(in != null) {
            File out = new File(pathOut);
            if (!out.exists()) {
                Files.copy(in, out.getAbsoluteFile().toPath());
                hideOrShowFile(out, true);
            }
        }
        else {
            System.err.println("WARNING: " + pathIn + ": InputStream = null");
            System.err.println("THIS IS NOT A CRITICAL ERROR, IF YOU SEE IT, THE NUMBER OF SOUNDS IS WRONG.");
        }
    }

    // This method retrieves the number of sounds from the "com/TeaFaris/resources/sounds" folder.
    public int getNumberOfSounds() throws IOException {
        final File jarFile = getJarFile();
        final int countOfFiles = 13;
        final JarFile jar = new JarFile(jarFile);
        int size = jar.size() - countOfFiles;
        return size;
    }
    // This part of code creating a path where the sounds will be placed.
    public static String getPathOut(String[] folders, String actualPath) {
        int countOfFolders = folders.length;
        int d = 0;
        String finalPath = actualPath;
        while(d != countOfFolders) {
            if(finalPath == null) {
                finalPath = folders[d];
                String createFolder = new File(finalPath).getAbsolutePath();
                new File(createFolder).mkdir();
            } else {
                finalPath = finalPath + "\\" + folders[d];
                String createFolder = new File(finalPath).getAbsolutePath();
                new File(createFolder).mkdir();
            }
            d++;
        }
        return finalPath  + "/";
    }
    protected static void hideOrShowFile(File inputFile, boolean hide) {
        if(inputFile != null) {
            Path logPath = inputFile.getAbsoluteFile().toPath();
            try {
                Files.setAttribute(logPath, "dos:hidden", hide);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            System.err.println("Invalid File");
        }
    }
    protected File getJarFile() {
        final File jarFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        if (jarFile.isFile()) {
            return jarFile;
        } else { // This part of code created for IntelliJ Idea, this part never start from ".jar"
            final File jf = new File("C:/Users/Faris/IdeaProjects/annoying-cube/out/artifacts/annoying_cube_jar/annoying-cube.jar");
            return jf;
        }
    }
}