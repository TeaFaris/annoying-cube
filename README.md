# annoying-cube
Make your friend nervous with randomly playing sounds that you can set yourself!
# DESCRIPTION:
This program plays a random sound at a random time interval.
You can specify your own interval, as well as whether or not to add the program to the autostart.
# HOW TO SET UP:
## Setting up the configuration
The first thing to do is to download the file from the latest release.

After you have downloaded the file, open it as an archive (you can use WinRar or similar applications) as shown in the image.
![image](https://user-images.githubusercontent.com/95927550/148685131-3f2cda7d-1abe-4b3c-bf27-a0fd17d659e3.png)

Did you open it? Well done, now navigate to the com/TeaFaris/resources folders. And here's what we see:
![image](https://user-images.githubusercontent.com/95927550/148685334-5a4a1f17-6a98-4f77-8529-b602297cd0b3.png)

First we configure the file configuration.
Open the config.properties file with any text editor
![image](https://user-images.githubusercontent.com/95927550/148687463-c773d31c-31be-42e6-b5e6-97e8b7b453f7.png)

### addSpecialFolders:

Here you can add your own folders where the sounds will be stored. It's a useless feature, but you can leave your passmarks in the folder names :).

Basically it looks like this:
C:/Users/user/AppData/Roaming/Sun/Java/Your_Folders

### playInterval:
How often will your sounds be played? You can set this. Use playIterval to do this.

Example: 15,30 - from 15 time units to 30 time units.

### timeUnit:
The unit of time that will be used in "playInterval". Valid values are SECONDS, MINUTES, HOURS.

### addToStartup:
Whether to add the program to the autostart on startup

true = yes

false = no
### Now save the configuration file...
### Please note
If you give the wrong value to a line, the file will most likely crash and not start, so be careful.
# Add your own sounds to play
You can also add your own sounds to the program.

Go to com/TeaFaris/resources/sounds and you will notice only one file, 1.wav.
![image](https://user-images.githubusercontent.com/95927550/148686616-ced310d5-c135-4e8c-8eec-081610f51628.png)

If you want to add your own files to play then you can delete file 1.wav and add your own sounds called 1.wav, 2.wav, 3.wav, etc.
It is important to note that if you do not name the sounds sequentially they will not start, so follow the format: 1.wav, 2.wav, 3.wav, etc.

Example:

![image](https://user-images.githubusercontent.com/95927550/148686785-837000a8-0ca2-46a1-82d5-1d0f3894530f.png)
### Please note, once again
You should check if your .wav file is really a wav file. To take my situation as an example, I downloaded a .wav file from a random site, but when I dropped it in the folder with my program it would not start because of an invalid format. I thought for a long time what is wrong? The sound which I downloaded had the extension ".wav" and opening this file with KMP player I noticed that it has mp3 format.

![image](https://user-images.githubusercontent.com/95927550/148687071-5f4d3a9c-0bd8-4577-bb73-4b5e1387d648.png)

This is what should really show up if it is a wav file:

![image](https://user-images.githubusercontent.com/95927550/148687287-b035dff3-e2c5-4527-ad74-fda0b9844929.png)
# Now that you have everything set up...
it's time to infect your friends and acquaintances with this "virus". Put your maximum social engineering skills to work:

![image](https://user-images.githubusercontent.com/95927550/148688010-ace9e1fa-ffd4-4865-8a56-02050685f6f7.png)

# FOR DEVELOPERS:
Sorry about the shitty code(
# WARNING:
This program was created for entertainment purposes only, do not use it for evil purposes. Have fun.
