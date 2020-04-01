package audio;

import org.lwjgl.openal.AL10;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.libc.LibCStdlib;

import main.Main;
import sun.misc.IOUtils;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.*;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.stb.STBVorbis.*;
import static org.lwjgl.system.MemoryStack.stackPush;

public class AudioBuffer {
    private final int handle;

    public AudioBuffer(ByteBuffer buffer, int format, int samplerate) {
        this.handle = AL10.alGenBuffers();
        AL10.alBufferData(handle, format, buffer, samplerate);
    }

    public static AudioBuffer loadFrom(String path) {
        try (MemoryStack stack = stackPush()) {

            WaveData waveData = WaveData.create(path);

            AudioBuffer buffer = new AudioBuffer(waveData.data, waveData.format, waveData.samplerate);

            return buffer;
        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
            return null;
        }
    }

    public int lengthInSamples() {
        int size = AL10.alGetBufferi(handle, AL_SIZE);
        int channels = AL10.alGetBufferi(handle, AL_CHANNELS);
        int bits = AL10.alGetBufferi(handle, AL_BITS);

        return size * 8 / (channels * bits);
    }

    public double lenghtInSecounds() {
        int frequency = AL10.alGetBufferi(handle, AL_FREQUENCY);

        return (double) lengthInSamples() / frequency;
    }

    public int handle() {
        return handle;
    }

    public void close() {
        AL10.alDeleteBuffers(handle);
    }
}
