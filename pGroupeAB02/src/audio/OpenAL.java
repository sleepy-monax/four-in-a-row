package audio;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALCapabilities;

import static org.lwjgl.openal.ALC10.*;

public class OpenAL {
    private static long device;
    private static long context;

    public static void initialize() {
        String defaultDeviceName = alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER);
        device = alcOpenDevice(defaultDeviceName);

        int[] attributes = {0};

        context = alcCreateContext(device, attributes);
        alcMakeContextCurrent(context);

        ALCCapabilities alcCapabilities = ALC.createCapabilities(device);
        ALCapabilities alCapabilities = AL.createCapabilities(alcCapabilities);
    }

    public static void shutdown() {
        alcDestroyContext(context);
        alcCloseDevice(device);
    }

//    public static void main(String[] args) {
//        ThreadManager.initialize();
//        OpenAL.initialize();
//
//        AudioBuffer bufferIntro = AudioBuffer.loadFrom("assets/effects/click.wav");
//
//        System.out.println(bufferIntro.lengthInSamples());
//        System.out.println(bufferIntro.lenghtInSecounds());
//
//        AudioSource source = new AudioSource();
//        source.playLoop(bufferIntro);
//
//        try {
//            // Wait for a second
//            Thread.sleep(1000000);
//        } catch (InterruptedException ignored) {
//        }
//
//        OpenAL.shutdown();
//    }
}
