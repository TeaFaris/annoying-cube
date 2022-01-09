package com.TeaFaris;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Properties;

import static com.TeaFaris.Start.CONFIG_NAME;
import static com.TeaFaris.Start.PATH_TO_VIRUS;

public class ConfigurationWork {
    // This method creates a configuration file and makes it hidden
    public File createConfig(String pathIn) throws IOException {
        File config = new File(PATH_TO_VIRUS + CONFIG_NAME);
        if(!config.exists()) {
            System.out.println("Created File:" + config.getAbsolutePath());
            InputStream in = getClass().getResourceAsStream(pathIn);
            Files.copy(in, config.getAbsoluteFile().toPath());
            FileHandling.hideOrShowFile(config, true);
        }
        return config;
    }
    // This method making all actions related with config properties
    public boolean createLog(String[] folders, String[] nameOfSounds) throws IOException {
        File log = new File(PATH_TO_VIRUS + "log.txt");
        if(!log.exists()) {
            log.createNewFile();
            setLogInfo(log, folders);
            FileHandling.hideOrShowFile(log, true);
            return true;
        } else {
            if (log != null) {
                FileHandling.hideOrShowFile(log, false);
                // get log info
                String[] readiedText = getLogInfo(log);
                if (readiedText != null) {
                    getFoldersForDeleting(readiedText, nameOfSounds);
                } else {
                    getFoldersForDeleting(folders, nameOfSounds);
                }
                // write last folders into the log.txt
                // This is so that if you change folders in the config it can find the former path and remove files and folders from there
                setLogInfo(log, folders);
                FileHandling.hideOrShowFile(log, true);
            } else {
                System.err.println("Invalid file");
                System.exit(-1);
            }
            return false;
        }
    }

    // This part delete the sounds and folders if the folders in config have been changed
    // And yeah, I know that this code looks and works like piece of shit, but it works.
    private void getFoldersForDeleting(String[] folders, String[] namesOfSounds) {
        // Some variables.
        int countOfFolders = folders.length;
        int d = countOfFolders - 1;
        int y = 0;
        // Deleting the file and folders, sorry for shitty code
        String finalPath = null;
        while(d != -1) {
            if (finalPath == null) {
                finalPath = folders[0];
                String finalPathToFile = folders[0];
                if(countOfFolders != 1) {
                    int a = 1;
                    while (a != countOfFolders) {
                        finalPathToFile = finalPathToFile + "/" + folders[a];
                        a++;
                    }
                }
                String folderToDelete = new File(finalPathToFile).getAbsolutePath();
                int g = 0;
                int length = namesOfSounds.length;
                while (g <= length-1) {
                    String fileToDelete = new File(finalPathToFile + "/" + namesOfSounds[g]).getAbsolutePath();
                    File sound = new File(fileToDelete);
                    boolean isExists = sound.exists();
                    if(isExists) {
                        sound.delete();
                    }
                    g++;
                }
                File folder = new File(folderToDelete);
                folder.delete();
            } else {
                int z = 1;
                while(z != countOfFolders - y) {
                    finalPath = finalPath + "/" + folders[z];
                    z++;
                }
                String folderToDelete = new File(finalPath).getAbsolutePath();
                File folder = new File(folderToDelete);
                folder.delete();
                finalPath = folders[0];
            }
            d--;
            y++;
        }
    }
    // get some config property like String.
    public String getConfigProperty(File inputConfig, String key) throws IOException {
        Properties properties = new Properties();
        if(inputConfig != null) {
            FileInputStream fis = new FileInputStream(inputConfig);
            properties.load(fis);
            return properties.getProperty(key);
        }
        else {
            System.err.println("Invalid file.");
            System.exit(-1);
            return null;
        }
    }
    // get some config property like String array
    public String[] getConfigPropertyArray(File inputConfig, String key) throws IOException {
        Properties properties = new Properties();
        if(inputConfig != null) {
            FileInputStream fis = new FileInputStream(inputConfig);
            properties.load(fis);
            String propertyValue = properties.getProperty(key);
            return propertyValue.split(",");
        }
        else {
            System.err.println("Invalid file.");
            System.exit(-1);
            return null;
        }
    }
    // Just reads the log file and returns values, nothing more
    public String[] getLogInfo(File inputLog) throws IOException {
        if(inputLog != null) {
            FileHandling.hideOrShowFile(inputLog, false);

            FileReader fr = new FileReader(inputLog);
            BufferedReader br = new BufferedReader(fr);

            String readiedFile = br.readLine();
            br.close();
            fr.close();

            FileHandling.hideOrShowFile(inputLog, true);
            if(readiedFile != null) {
                return readiedFile.split(",");
            } else {
                return null;
            }
        } else {
            System.err.println("Invalid file.");
            System.exit(-1);
            return null;
        }
    }
    // Sets the values in the log
    public void setLogInfo(File inputLog, String[] folders) throws IOException {
        if(inputLog != null) {
            FileHandling.hideOrShowFile(inputLog, false);
            FileOutputStream fos = new FileOutputStream(inputLog);
            int a = 0;
            String glue = null;
            while(a != folders.length) {
                if(glue == null) {
                    glue = folders[a];
                } else {
                    glue = glue + "," + folders[a];
                }
                a++;
            }
            fos.write(glue.getBytes(StandardCharsets.UTF_8));
            FileHandling.hideOrShowFile(inputLog, true);
        } else {
            System.err.println("Invalid file.");
            System.exit(-1);
        }
    }
    // get some config property like boolean
    public Boolean getConfigBoolean(File inputConfig, String key) throws IOException {
        String bool = getConfigProperty(inputConfig, key);
        if(bool != null) {
            return Boolean.parseBoolean(bool);
        } else {
            System.err.println("Invalid value.");
            System.err.println("Value must be: 'true' or 'false'.");
            System.exit(-1);
            return null;
        }
    }
}
