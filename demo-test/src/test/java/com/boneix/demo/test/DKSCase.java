package com.boneix.demo.test;

import com.boneix.base.util.JsonUtils;
import com.boneix.demo.client.HttpClient;
import com.boneix.demo.domain.ResponseVo;
import com.boneix.demo.domain.SysInfo;
import com.boneix.thread.process.Executor;
import com.boneix.thread.process.TaskAction;
import com.google.common.base.Stopwatch;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.fail;

/**
 * Created by zhangrong5 on 2016/11/1.
 */
public class DKSCase {
    private static Logger logger = LoggerFactory.getLogger(DKSCase.class);

    @Test
    public void test() {
        fail("Not yet implemented");
    }

    @Test
    public void testRest() {
        doRestRedisKey(createSysInfo());
    }

    /**
     * 使用TaskAction方式调用多线程方法
     */
    @Test
    public void testThread() {
        Stopwatch sw = Stopwatch.createStarted();
        List<TaskAction<Integer>> tasks = new ArrayList<>();
        try {
            final SysInfo sysInfo = createSysInfo();
            for (int i = 0; i < 2000; i++) {
                TaskAction<Integer> serverTask = new TaskAction<Integer>() {
                    @Override
                    public Integer doInAction() throws Exception {
                        return queryRedisKey(sysInfo);
                    }
                };
                tasks.add(serverTask);
            }
            if (CollectionUtils.isNotEmpty(tasks)) {
                List<Integer> taskResult = Executor.getCommonTaskProcess().executeTask(tasks);
                if (CollectionUtils.isNotEmpty(taskResult)) {
                    Set<Integer> tmpSet = new HashSet<>();
                    for (Integer tmp : taskResult) {
                        tmpSet.add(tmp);
                    }
                    logger.info("result size:{} , Distinct size:{}", taskResult.size(), tmpSet.size());
                }
            }
        } catch (Exception e) {
            logger.error("this fuction throws Exception:,{}", e);
        }
        logger.info("this fuction cost time:,{}", sw.toString());
    }

    private Integer queryRedisKey(SysInfo sysInfo) {
        ResponseVo responseVo = doRestRedisKey(sysInfo);

        return Integer.valueOf(String.valueOf(responseVo.getData()));

    }

    private ResponseVo doRestRedisKey(SysInfo sysInfo) {
        ResponseVo responseVo = new ResponseVo();
        String url = "http://localhost:8080/key/consume";
        try {
            String data = new BASE64Encoder().encode(JsonUtils.toString(sysInfo).getBytes("utf-8"));
            String res = new HttpClient(url, "POST", data).execute();
            //logger.info("doRestRedisKey : {}", res);
            if (StringUtils.isNotEmpty(res)) {
                String tmp = new String(new BASE64Decoder().decodeBuffer(res), "utf-8");
                logger.info("doRestRedisKey BASE64Decoder : {}",tmp);
                responseVo = JsonUtils.toBean(tmp, ResponseVo.class);
            }

        } catch (IOException e) {
            logger.error("doRestRedisKey exception : {}", e);
        }
        return responseVo;
    }

    private SysInfo createSysInfo() {
        SysInfo sysInfo = new SysInfo();
        sysInfo.setId(5);
        sysInfo.setAuthorityCode("5af83e3196bf99f440f31f2e1a6c9afe");
        return sysInfo;

    }
}
