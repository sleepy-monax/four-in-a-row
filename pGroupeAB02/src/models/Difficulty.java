package models;

public enum Difficulty {
    EASY(3),
    MEDIUM(2),
    HARD(1);

    private int answerTolerance;

    Difficulty(int answerTolerance) {
        this.answerTolerance = answerTolerance;
    }

    public int getAnswerTolerance() {
        return answerTolerance;
    }
}
