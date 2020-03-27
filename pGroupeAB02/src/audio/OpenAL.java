package audio;

import org.lwjgl.openal.*;
import org.lwjgl.system.*;

import java.nio.*;
import static org.lwjgl.stb.STBVorbis.*;
import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.libc.LibCStdlib.*;

public class OpenAL {
    public static void main(String[] args)
    {
        //Initialization
        String defaultDeviceName = alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER);
        long   device            = alcOpenDevice(defaultDeviceName);

        int[] attributes = {0};
        long  context    = alcCreateContext(device, attributes);
        alcMakeContextCurrent(context);

        ALCCapabilities alcCapabilities = ALC.createCapabilities(device);
        ALCapabilities  alCapabilities  = AL.createCapabilities(alcCapabilities);

        AudioBuffer buffer = AudioBuffer.loadFromFile("src/assets/musics/loop.ogg");
        AudioBuffer buffer2 = AudioBuffer.loadFromFile("src/assets/musics/loop3.ogg");

//Request a source
        int sourcePointer = alGenSources();

//Assign the sound we just loaded to the source
        alSourcei(sourcePointer, AL_LOOPING, 1);

//Play the sound


        try {
            //Wait for a second
            alSourcei(sourcePointer, AL_BUFFER, buffer2.handle());

            alSourcePlay(sourcePointer);


            Thread.sleep(1000);

            alSourcei(sourcePointer, AL_BUFFER, buffer2.handle());


            Thread.sleep(1000000);
        } catch (InterruptedException ignored) {
        }

//Terminate OpenAL
        alDeleteSources(sourcePointer);
        buffer.close();
        alcDestroyContext(context);
        alcCloseDevice(device);
    }
}
