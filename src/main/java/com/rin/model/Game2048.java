package com.rin.model;

import javafx.scene.image.Image;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Game2048 {
    private static Game2048 instance;
    private static final int SIDE = 4;
    private int score = 0;
    private int maxScore;
    private int[][] gameField = new int[SIDE][SIDE];
    private boolean isGameStopped = false;
    private final Map<Integer, Image> images = new HashMap<>();

    private Game2048() {
        addImagesToMap();
    }

    public static Game2048 getInstance() {
        if (instance == null) {
            instance = new Game2048();
        }
        return instance;
    }

    public int getSide() {
        return SIDE;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public int[][] getGameField() {
        return gameField;
    }

    public Image getImageByValue(int value) {
        return images.get(value);
    }

    public boolean isGameStopped() {
        return isGameStopped;
    }

    public void setGameStopped(boolean gameStopped) {
        isGameStopped = gameStopped;
    }

    private void addImagesToMap() {
        images.put(0, new Image(("file:src/main/resources/com/rin/img/0.png"), false));
        images.put(2, new Image(("file:src/main/resources/com/rin/img/2.png"), false));
        images.put(4, new Image(("file:src/main/resources/com/rin/img/4.png"), false));
        images.put(8, new Image(("file:src/main/resources/com/rin/img/8.png"), false));
        images.put(16, new Image(("file:src/main/resources/com/rin/img/16.png"), false));
        images.put(32, new Image(("file:src/main/resources/com/rin/img/32.png"), false));
        images.put(64, new Image(("file:src/main/resources/com/rin/img/64.png"), false));
        images.put(128, new Image(("file:src/main/resources/com/rin/img/128.png"), false));
        images.put(256, new Image(("file:src/main/resources/com/rin/img/256.png"), false));
        images.put(512, new Image(("file:src/main/resources/com/rin/img/512.png"), false));
        images.put(1024, new Image(("file:src/main/resources/com/rin/img/1024.png"), false));
        images.put(2048, new Image(("file:src/main/resources/com/rin/img/2048.png"), false));
    }

    public void createGame() {
        gameField = new int[SIDE][SIDE];
        createNewNumber();
        createNewNumber();
    }

    public void createNewNumber() {
        if (!isGameStopped) {
            int randomNumber;
            if (ThreadLocalRandom.current().nextInt(0, 9) == 9) {
                randomNumber = 4;
            } else {
                randomNumber = 2;
            }

            int xPoint = ThreadLocalRandom.current().nextInt(SIDE);
            int yPoint = ThreadLocalRandom.current().nextInt(SIDE);

            if (gameField[xPoint][yPoint] == 0) {
                gameField[xPoint][yPoint] = randomNumber;
            } else {
                createNewNumber();
            }
        }
    }

    public int getMaxTileValue() {
        List<Integer> values = new ArrayList<>();

        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                values.add(gameField[i][j]);
            }
        }
        return Collections.max(values);
    }

    private boolean compressRow(int[] row) {
        int[] rowWithoutChanges = Arrays.copyOf(row, row.length);
        int i = 1;

        while (i < row.length) {
            for (int j = 1; j < row.length; j++) {
                if (row[j - 1] == 0 && row[j] != 0) {
                    row[j - 1] = row[j];
                    row[j] = 0;
                }
            }
            i++;
        }
        return !Arrays.equals(row, rowWithoutChanges);
    }

    private boolean mergeRow(int[] row) {
        int[] rowWithoutChanges = Arrays.copyOf(row, row.length);

        for (int j = 1; j < row.length; j++) {
            if (row[j - 1] == row[j] && (row[j - 1] & row[j]) != 0) {
                row[j - 1] = row[j - 1] + row[j];
                row[j] = 0;
                score = score + row[j - 1];
            }
        }
        return !Arrays.equals(row, rowWithoutChanges);
    }

    public void moveLeft() {
        int[][] gameFieldOriginal = new int[SIDE][SIDE];

        for (int i = 0; i < SIDE; i++) {
            System.arraycopy(gameField[i], 0, gameFieldOriginal[i], 0, SIDE);
        }

        for (int[] ints : gameField) {
            compressRow(ints);
            mergeRow(ints);
        }

        for (int[] ints : gameField) {
            for (int j = 0; j < ints.length; j++) {
                compressRow(ints);
            }
        }

        if (!Arrays.deepEquals(gameField, gameFieldOriginal)) {
            createNewNumber();
        }
    }

    public void moveRight() {
        rotateClockwise();
        rotateClockwise();
        moveLeft();
        rotateClockwise();
        rotateClockwise();
    }

    public void moveUp() {
        rotateClockwise();
        rotateClockwise();
        rotateClockwise();
        moveLeft();
        rotateClockwise();
    }

    public void moveDown() {
        rotateClockwise();
        moveLeft();
        rotateClockwise();
        rotateClockwise();
        rotateClockwise();
    }

    private void rotateClockwise() {
        int[][] result = new int[SIDE][SIDE];

        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                result[i][j] = gameField[SIDE - j - 1][i];
            }
        }
        gameField = result;
    }

    public boolean canUserMove() {
        boolean hasSameValues = false;
        boolean hasNullValues = false;

        for (int i = 0; i < SIDE; i++) {
            int[] row = gameField[i];
            for (int j = 1; j < SIDE; j++) {
                if (row[j - 1] == 0 || row[SIDE - 1] == 0) {
                    hasNullValues = true;
                    break;
                }
            }
        }
        for (int i = 1; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                if (gameField[i][j] == gameField[i - 1][j]) {
                    hasSameValues = true;
                    break;
                }
            }
        }
        for (int i = 0; i < SIDE; i++) {
            for (int j = 1; j < SIDE; j++) {
                if (gameField[i][j] == gameField[i][j - 1]) {
                    hasSameValues = true;
                    break;
                }
            }
        }
        return (hasNullValues || hasSameValues);
    }
}
