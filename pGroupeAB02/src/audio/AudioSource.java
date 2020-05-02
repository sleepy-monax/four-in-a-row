package audio;

import javafx.application.Platform;
import utils.ThreadManager;

import java.util.concurrent.Future;

import static org.lwjgl.openal.AL10.*;

public class AudioSource {
    private int handle;
    private double volume;
    private boolean muted;
    private Future future;

    public AudioSource() {
        handle = alGenSources();
    }

    public void reset() {
        System.out.println("AUDIO: " + this + " reset!");

        if (future != null) {
            future.cancel(true);
        }

        stop();
        setLooping(false);
        unqueueProcessed();
    }

    private void stop() {
        alSourceStop(handle);
    }

    private void play() {
        alSourcePlay(handle);
    }

    private void unqueueProcessed() {
        int processed_count = alGetSourcei(handle, AL_BUFFERS_PROCESSED);

        System.out.println("AUDIO: " + "Unqueing " + processed_count + " buffer from " + this);

        for (int i = 0; i < processed_count; i++) {
            alSourceUnqueueBuffers(handle);
        }
    }

    public void setLooping(boolean looping) {
        alSourcei(handle, AL_LOOPING, looping ? AL_TRUE : AL_FALSE);
    }

    public void setVolume(double volume) {
        this.volume = volume;

        if (!muted) {
            alSourcef(handle, AL_GAIN, (float) volume);
        }
    }

    public void setMuted(boolean muted) {
        this.muted = muted;

        if (muted) {
            alSourcef(handle, AL_GAIN, (float) 0.0);
        } else {
            alSourcef(handle, AL_GAIN, (float) this.volume);
        }
    }

    public void setPitch(double pitch) {
        alSourcef(handle, AL_PITCH, (float) pitch);
    }

    public void playNow(AudioBuffer buffer) {
        playNow(buffer, null);
    }

    public void playNow(AudioBuffer buffer, Runnable then) {
        System.out.println("AUDIO: " + this + ".PlayNow(" + buffer + ")");

        reset();

        alSourceQueueBuffers(handle, buffer.handle());

        future = ThreadManager.launch(() -> {
            try {
                Thread.sleep((int) (1000 * (buffer.lenghtInSecounds() + 0.1)));
                stop();

                if (then != null) {
                    Platform.runLater(then);
                }
            } catch (InterruptedException e) {
                System.out.println("AUDIO: " + this + " interrupted!");

                stop();
                return;
            }
        });

        play();
    }

    public void playLoop(AudioBuffer buffer) {
        System.out.println("AUDIO: " + this + ".PlayLoop(" + buffer + ")");
        reset();

        System.out.println("AUDIO: " + this + ".PlayLoop(" + buffer + "): queuing buffer...");
        alSourceQueueBuffers(handle, buffer.handle());
        setLooping(true);

        play();
        System.out.println("AUDIO: " + this + ".PlayLoop(" + buffer + "): Start playing");
    }

    public void playLoopWithTransition(AudioBuffer buffer, AudioBuffer transition) {
        playLoopWithTransition(buffer, transition, null);
    }

    public void playLoopWithTransition(AudioBuffer buffer, AudioBuffer transition, Runnable then) {
        System.out.println("AUDIO: " + this + ".playLoopWithTransition(" + buffer + ", " + transition + ")");
        reset();

        alSourceQueueBuffers(handle, transition.handle());
        alSourceQueueBuffers(handle, buffer.handle());

        future = ThreadManager.launch(() -> {
            try {
                Thread.sleep((int) (1000 * (transition.lenghtInSecounds() + 0.1)));
                System.out.println("AUDIO: " + this + " switching to the loop");

                unqueueProcessed();

                setLooping(true);

                if (then != null) {
                    Platform.runLater(then);
                }

                System.out.println("AUDIO: " + this + " switching to the loop completed");
            } catch (InterruptedException e) {
                System.out.println("AUDIO: " + this + " interrupted!");

                stop();
                return;
            }
        });

        play();
    }

    public void close() {
        reset();
        alDeleteSources(handle);
        handle = -1;
    }

    @Override
    public String toString() {
        return "AudioSource{" + handle + "}";
    }
}
