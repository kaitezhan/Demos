package com.boneix.thread;

import com.boneix.thread.pool.FixedTaskProcessPool;
import com.boneix.thread.process.TaskProcess;
import com.boneix.thread.process.TaskProcessManager;

/**
 * Created by rzhang on 2018/3/15.
 */
public class ThreadExecutor {
    private static final String REPAIR_TRADE_CHARGE_RECORD = "REPAIR_TRADE_CHARGE_RECORD";

    private ThreadExecutor() {
    }

    public static TaskProcess repairTradeChargeRecord() {
        return TaskProcessManager.getTaskProcess(REPAIR_TRADE_CHARGE_RECORD, new FixedTaskProcessPool());

    }
}
