package com.conqueror.app.entity;

import org.apache.log4j.Logger;

/**
 * @author Bogdan Kaftanatiy
 */
public class GameSynchronizer {
    private final static Logger LOGGER = Logger.getLogger(GameSynchronizer.class);

    private boolean isAnswer = false;
    private int gamerCount = 0;
    private boolean isMove = true;

    public synchronized void waitAnswer() {
        LOGGER.info("Start checking answer for wait");
        while(!isAnswer) {
            LOGGER.info("Start waiting answer");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LOGGER.info("Smth with answer happen");
        }
        LOGGER.info("Finish waiting for answer");
    }

    public synchronized void updateAnswer() {
        isAnswer = true;
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            LOGGER.warn("Stop sleeping in updateAnswer");
        }
        notifyAll();
        LOGGER.info("Answer updated");
    }

    public synchronized void waitGameReady() {
        LOGGER.info("Checking ready game");
        while (gamerCount != 3) {
            LOGGER.info("Start waiting gamers");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        LOGGER.info("Finish waiting for gamers");
    }

    public synchronized void addGamer() {
        gamerCount++;
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            LOGGER.warn("Stop sleeping in addGamer");
        }
        notifyAll();
        LOGGER.info("Gamer count updated");
    }

    public synchronized void waitMoveEnd() {
        LOGGER.info("Checking move end");
        while (isMove) {
            LOGGER.info("Start waiting move end");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        LOGGER.info("Finish waiting for move end");
    }

    public synchronized void endMove() {
        isMove = false;
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            LOGGER.warn("Stop sleeping in endMove");
        }
        notifyAll();
        LOGGER.info("Move ended");
    }

}
