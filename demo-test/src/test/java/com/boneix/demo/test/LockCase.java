package com.boneix.demo.test;

import com.boneix.base.util.EncryptUtils;
import com.boneix.base.util.JsonUtils;
import com.boneix.demo.client.HttpClient;
import com.boneix.demo.domain.DistrKeyInfo;
import com.boneix.demo.domain.ResponseVo;
import com.boneix.demo.domain.SysInfo;
import com.boneix.thread.process.Executor;
import com.boneix.thread.process.TaskAction;
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
 * Created by zhangrong5 on 2016/11/11.
 */
public class LockCase {
    private static Logger logger = LoggerFactory.getLogger(DKSCase.class);

    @Test
    public void test() {
        fail("Not yet implemented");
    }

    @Test
    public void testOneSysManyMachines() {

        List<DistrKeyInfo> distrKeyInfos = createDateList();

        List<TaskAction<String>> tasks = new ArrayList<>();

        for (final DistrKeyInfo distrKeyInfo : distrKeyInfos) {
            TaskAction<String> serverTask = new TaskAction<String>() {
                @Override
                public String doInAction() throws Exception {
                    ResponseVo responseVo = doRestRedisLock(distrKeyInfo);
                    String data=String.valueOf(responseVo.getData());

                    if (data.equals("1")) {
                        return distrKeyInfo.getSysInfo().getAuthorityCode();
                    } else {
                        return "";
                    }
                }
            };
            tasks.add(serverTask);
        }

        if (CollectionUtils.isNotEmpty(tasks)) {
            List<String> taskResult = Executor.getCommonTaskProcess().executeTask(tasks);
            if (CollectionUtils.isNotEmpty(taskResult)) {
                Set<String> tmpSet = new HashSet<>();
                for (String tmp : taskResult) {
                    if (StringUtils.isNotEmpty(tmp)) {
                        tmpSet.add(tmp);
                    }
                }
                logger.info("result size:{} , Distinct size:{}", taskResult.size(), tmpSet.size());
            }
        }
    }

    private List<DistrKeyInfo> createDateList() {
        List<DistrKeyInfo> distrKeyInfos = new ArrayList<>();
        for (int i = 1; i < 2001; i++) {
            for(int j=1;j<6;j++){
                DistrKeyInfo distrKeyInfo = createDate("PCS_"+ i, j, i, "PCS", "PCS_" +j+"_"+ i);
                distrKeyInfos.add(distrKeyInfo);
            }
        }
        return distrKeyInfos;
    }


    private DistrKeyInfo createDate(String key, int invalidTime, int sysId, String sysName, String code) {
        DistrKeyInfo distrKeyInfo = new DistrKeyInfo();
        distrKeyInfo.setKey(key);
        distrKeyInfo.setInvalidTime(invalidTime);
        SysInfo sysInfo = new SysInfo();
        sysInfo.setId(sysId);
        sysInfo.setSystemName(sysName);
        sysInfo.setAuthorityCode(EncryptUtils.MD5(code));
        //sysInfo.setMachineName(code);
        distrKeyInfo.setSysInfo(sysInfo);
        return distrKeyInfo;
    }

    private ResponseVo doRestRedisLock(DistrKeyInfo distrKeyInfo) {
        ResponseVo responseVo = new ResponseVo();
        try {
            String url = "http://localhost:8180/lock/occupy";
            String data = new BASE64Encoder().encode(JsonUtils.toString(distrKeyInfo).getBytes("utf-8"));
            String res = new HttpClient(url, "POST", data).execute();
            if (StringUtils.isNotEmpty(res)) {
                String tmp = new String(new BASE64Decoder().decodeBuffer(res), "utf-8");
                logger.info("doRestRedisLock BASE64Decoder : {},{}", tmp,distrKeyInfo.getSysInfo().getAuthorityCode());
                responseVo = JsonUtils.toBean(tmp, ResponseVo.class);
            }

        } catch (IOException e) {
            logger.error("doRestRedisLock exception : {}", e);
        }
        return responseVo;
    }
}
