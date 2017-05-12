/*
 * Copyright (C) 2006-2014 Tuniu All rights reserved
 * Author: wangchong
 * Date: 2015-5-4
 * Description:DnsChecker.java 
 */
package com.boneix.base.cache.pool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 做DNS切换之后，需要立即感知
 *
 * @author wangchong
 */
public class DnsChecker {

    private static final Logger LOGGER = LoggerFactory.getLogger(DnsChecker.class);

    private List<HostPair> hostPairs;

    private Map<String, String> realPair = new ConcurrentHashMap<String, String>();

    private ScheduledExecutorService executorService;

    private volatile boolean check = true;

    public DnsChecker(List<HostPair> hostPairs) {
        this.hostPairs = hostPairs;
        executorService = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName(DnsChecker.class.getName() + "-Checker");
                thread.setDaemon(true);
                return thread;
            }
        });
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                if (executorService != null) {
                    try {
                        executorService.shutdown();
                        executorService.awaitTermination(5, TimeUnit.MINUTES);
                    } catch (InterruptedException e) {
                    }
                }
            }
        });

        executorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                resolve();
            }
        }, 0, 2, TimeUnit.SECONDS);

    }

    /**
     * 解析
     */
    private void resolve() {
        boolean check = false;
        for (HostPair hostPair : hostPairs) {
            check &= resolve(hostPair.getHost());
        }
        this.check = check;
    }

    private boolean resolve(String host) {
        try {
            String hostAddress = InetAddress.getByName(host).getHostAddress();
            LOGGER.debug("DNS.Checker resolve - {} to {}", host, hostAddress);
            String realAddress = realPair.get(host);
            if (!hostAddress.equalsIgnoreCase(realAddress)) {
                realPair.put(host, hostAddress);
                return true;
            }
        } catch (UnknownHostException e) {
        }
        return false;
    }

    public boolean check(String hostName, String hostAddress) {
        if (check) {
            String realAddress = realPair.get(hostName);
            // 如果没有默认成功
            if (StringUtils.isEmpty(realAddress)) {
                resolve(hostName);
                return true;
            }
            return realAddress.equals(hostAddress);
        }
        return !check;
    }
}
