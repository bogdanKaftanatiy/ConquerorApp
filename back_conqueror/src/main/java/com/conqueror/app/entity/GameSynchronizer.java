package com.conqueror.app.entity;

import org.apache.log4j.Logger;

/**
 * @author Bogdan Kaftanatiy
 */
public class GameSynchronizer {
    private final static Logger LOGGER = Logger.getLogger(GameSynchronizer.class);

    private int answersCount = 0;
    private int gamerCount = 0;
    private int moveEnd;
    private boolean isAttack = false;

    public synchronized void waitAttack() {
        LOGGER.info("Start checking user attack");
        while(!isAttack) {
            LOGGER.info("Start waiting attack");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LOGGER.info("Awake in waitAttack. IsAttack = " + isAttack);
        }
        LOGGER.info("Finish waiting for attack");
    }

    public synchronized void updateAttack() {
        isAttack = true;
        try {
            wait(100);
        } catch (InterruptedException e) {
            LOGGER.warn("Stop sleeping in updateAttack");
        }
        notifyAll();
        LOGGER.info("Attack updated");
    }

    public synchronized void waitAnswers() {
        LOGGER.info("Start checking answer for wait");
        while(answersCount != 2) {
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
        answersCount++;
        try {
            wait(100);
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
            wait(100);
        } catch (InterruptedException e) {
            LOGGER.warn("Stop sleeping in addGamer");
        }
        notifyAll();
        LOGGER.info("Gamer count updated");
    }

    public synchronized void waitMoveEnd() {
        LOGGER.info("Checking move end");
        while (moveEnd == 0) {
            LOGGER.info("Start waiting move end");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LOGGER.warn("Awake in waitMoveEnd. IsMoveEnd = " + moveEnd);
        }
        LOGGER.info("Finish waiting for move end");
    }

    public synchronized void endMove() {
        moveEnd++;
        try {
            wait(100);
        } catch (InterruptedException e) {
            LOGGER.warn("Stop sleeping1 in endMove");
        }
        notifyAll();
        try {
            wait(1000);
        } catch (InterruptedException e) {
            LOGGER.warn("Stop sleeping2 in endMove");
        }
        LOGGER.info("Move ended");
    }

    public void resetSynchronizer() {
        answersCount = 0;
        moveEnd = 0;
    }
}
