package audio;

import static org.lwjgl.openal.AL10.*;

import utils.ThreadManager;

public class AudioSource {
    private final int handle;

    public AudioSource() {
        handle = alGenSources();
    }

    public void reset() {
        stop();
        setLooping(false);
        alSourceUnqueueBuffers(handle);
    }

    public void stop() {
        alSourceStop(handle);
    }

    public void play() {
        alSourcePlay(handle);
    }

    public void setLooping(boolean looping) {
        alSourcei(handle, AL_LOOPING, looping ? AL_TRUE : AL_FALSE);
    }

    public void playNow(AudioBuffer buffer) {
        playNow(buffer, () -> {
        });
    }

    public void setVolume(double volume) {
        alSourcef(handle, AL_GAIN, (float) volume);
    }

    public void setPitch(double pitch) {
        alSourcef(handle, AL_PITCH, (float) pitch);
    }

    public void playNow(AudioBuffer buffer, Runnable then) {
        reset();

        alSourceQueueBuffers(handle, buffer.handle());

        ThreadManager.launch(() -> {
            try {
                Thread.sleep((int) (1000 * (buffer.lenghtInSecounds() + 0.25)));
                then.run();
            } catch (InterruptedException e) {
                stop();
                return;
            }
        });

        play();
    }

    public void playNowWithTransition(AudioBuffer buffer, AudioBuffer transition) {
        playNowWithTransition(buffer, transition, () -> {
        });
    }

    public void playNowWithTransition(AudioBuffer buffer, AudioBuffer transition, Runnable then) {
        reset();

        alSourceQueueBuffers(handle, transition.handle());
        alSourceQueueBuffers(handle, buffer.handle());

        ThreadManager.launch(() -> {
            try {
                Thread.sleep((int) (1000 * (transition.lenghtInSecounds() + 0.05)));
                alSourceUnqueueBuffers(handle);
                setLooping(true);

                then.run();
            } catch (InterruptedException e) {
                stop();
                return;
            }
        });

        play();
    }

    public void close() {
        alDeleteSources(handle);
    }
}
