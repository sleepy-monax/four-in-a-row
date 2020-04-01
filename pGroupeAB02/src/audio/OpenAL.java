package audio;

import org.lwjgl.openal.*;

import utils.ThreadManager;

import static org.lwjgl.openal.ALC10.*;

public class OpenAL {
    private static long device;
    private static long context;

    public static void initialize() {
        String defaultDeviceName = alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER);
        device = alcOpenDevice(defaultDeviceName);

        int[] attributes = { 0 };

        context = alcCreateContext(device, attributes);
        alcMakeContextCurrent(context);

        ALCCapabilities alcCapabilities = ALC.createCapabilities(device);
        ALCapabilities alCapabilities = AL.createCapabilities(alcCapabilities);
    }

    public static void shutdown() {
        alcDestroyContext(context);
        alcCloseDevice(device);
    }

    public static void main(String[] args) {
        ThreadManager.initialize();
        OpenAL.initialize();

        AudioBuffer bufferIntro = AudioBuffer.loadFrom("assets/musics/intro.wav");
        AudioBuffer bufferLoop = AudioBuffer.loadFrom("assets/musics/loop.wav");
        AudioBuffer bufferTransition = AudioBuffer.loadFrom("assets/musics/transition.wav");
        AudioBuffer bufferLoop2 = AudioBuffer.loadFrom("assets/musics/loop2.wav");
        AudioBuffer bufferTransition2 = AudioBuffer.loadFrom("assets/musics/transition2.wav");
        AudioBuffer bufferEnd = AudioBuffer.loadFrom("assets/musics/end.wav");

        System.out.println(bufferIntro.lenghtInSecounds());

        AudioSource source = new AudioSource();
        source.playNowWithTransition(bufferLoop, bufferIntro);

        try {
            // Wait for a second
            Thread.sleep(15000);
            System.out.println("Switching song");
            source.playNowWithTransition(bufferLoop2, bufferTransition);
            Thread.sleep(15000);
            source.playNow(bufferEnd);

            Thread.sleep(1000000);
        } catch (InterruptedException ignored) {
        }

        OpenAL.shutdown();
    }
}
