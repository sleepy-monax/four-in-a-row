package models.controller;

import models.message.OnAnswerCorrect;
import models.message.OnAnswerIncorrect;
import models.message.OnCountDown;
import models.message.OnRoundFinish;
import models.Game;
import utils.AudioManager;

public class AudioController {
    public AudioController(Game game)
    {
        game.getMessageLoop().registerNotifier(OnCountDown.class, message -> {
            if (message.time() == 5) {
                AudioManager.playEffect("assets/round-timer.wav");
            }

            if (message.time() == 1) {
                AudioManager.playEffect("assets/round-end.wav");
            }
        });

        game.getMessageLoop().registerNotifier(OnRoundFinish.class, message -> {
            AudioManager.stop();
        });

        game.getMessageLoop().registerNotifier(OnAnswerCorrect.class, message -> {
            AudioManager.playEffect("assets/correct.wav");
        });

        game.getMessageLoop().registerNotifier(OnAnswerIncorrect.class, message -> {
            AudioManager.playEffect("assets/wrong.wav");
        });
    }
}
