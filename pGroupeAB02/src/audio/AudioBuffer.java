package audio;

import org.lwjgl.openal.AL10;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.libc.LibCStdlib;

import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_decode_filename;
import static org.lwjgl.system.MemoryStack.stackPush;

public class AudioBuffer {
    private int handle;


    public AudioBuffer(ShortBuffer buffer, int format, int samplerate) {
        this.handle = AL10.alGenBuffers();
        AL10.alBufferData(handle, format, buffer, samplerate);
    }

    public static AudioBuffer loadFromFile(String path) {
        try (MemoryStack stack = stackPush()) {
            IntBuffer channelsBuffer = stack.mallocInt(1);
            IntBuffer sampleRateBuffer = stack.mallocInt(1);

            ShortBuffer rawAudio = stb_vorbis_decode_filename(path, channelsBuffer, sampleRateBuffer);

            int channels = channelsBuffer.get(0);
            int sampleRate = sampleRateBuffer.get(0);

            int format = -1;
            if (channels == 1) {
                format = AL_FORMAT_MONO16;
            } else if (channels == 2) {
                format = AL_FORMAT_STEREO16;
            }

            AudioBuffer buffer = new AudioBuffer(rawAudio, format, sampleRate);

            LibCStdlib.free(rawAudio);

            return buffer;
        } catch (Exception ignored) {
            return null;
        }
    }

    public int lengthInSamples()
    {
        int size = AL10.alGetBufferi(handle, AL_SIZE);
        int channels = AL10.alGetBufferi(handle, AL_CHANNELS);
        int bits = AL10.alGetBufferi(handle, AL_BITS);

        return size * 8 / (channels * bits);
    }

    public double lenghtInSecounds()
    {
        int frequency = AL10.alGetBufferi(handle, AL_FREQUENCY);

        return (double)lengthInSamples() / frequency;
    }

    public int handle() {
        return handle;
    }

    public void close() {
        AL10.alDeleteBuffers(handle);
    }
}
