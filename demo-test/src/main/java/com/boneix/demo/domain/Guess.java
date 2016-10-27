package com.boneix.demo.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhangrong5 on 2016/10/26.
 */
public class Guess {
    private static Logger logger = LoggerFactory.getLogger(Guess.class);

    private int maxTryTime;                         // 最大重试次数
    private int tryTime = 0;                        // 当前重试次数
    private int number = (int) (Math.random() * 6); // 目标数字
    private GuessDAO guessDAO;                      // 持久化依赖

    public Guess(int maxRetryTime) {
        this.maxTryTime = maxRetryTime;
    }

    public void doit() {
        logger.info("调用方法doit");
        while (tryTime++ < maxTryTime && !tryIt()) {
            // 到达最大尝试次数仍不成功则调用handle
            if (tryTime == maxTryTime) {
                failHandle();
            }
        }
    }

    // 最坏情况下调用maxRetryTime次tryIt(),猜中则保存信息
    public boolean tryIt() {
        logger.info("调用方法tryIt");
        if (number == randomGuess()) {
            guessDAO.saveResult(true, number);
            return true;
        }
        return false;
    }

    public void failHandle() {                      // 失败处理，猜不中时调用
        guessDAO.saveResult(false, number);
    }

    private int randomGuess() {                      // 猜的随机过程
        return (int) (Math.random() * 6);
    }

    public void setGuessDAO(GuessDAO guessDAO) {
        this.guessDAO = guessDAO;
    }
}
