package com.boneix.demo.test;

import com.boneix.demo.domain.Guess;
import com.boneix.demo.domain.GuessDAO;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhangrong5 on 2016/10/26.
 */
public class ObjectJMockit {

    private static Logger logger = LoggerFactory.getLogger(ObjectJMockit.class);

    // 表明被修饰实例是将会被自动构建和注入的实例
    @Tested
    Guess guess = new Guess(3);

    // 表明被修饰实例将会自动注入到@Tested修饰的实例中，并且会自动mock掉，除非在测试前被赋值
    @Injectable
    GuessDAO guessDao;

    @Test
    public void behaviorTest_fail3time() {
        new Expectations() {        // Expectations中包含的内部类区块中，体现的是一个录制被测类的逻辑。
            @Mocked(methods = "tryIt")  // 表明被修饰的类对tryIt()方法进行mock。
            Guess g;
            {
                g.tryIt();             // 期待调用Guess.tryIt()方法
                result = false;        // mock掉返回值为false（表明猜不中）
                times = 3;             // 期待以上过程重复3次
                guessDao.saveResult(false, anyInt); // 期待调用guessDAO把猜失败的结果保存
            }
        };
        guess.doit();               // 录制完成后，进行实际的代码调用，也称回放(replay)
    }


}
