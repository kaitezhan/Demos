package com.boneix.dks.property;

/**
 * Created by zhangrong5 on 2016/11/1.
 */
public class SystemParamInit {

    public static int keyWarningRang;

    public static int keyProduceRang;

    public static int getKeyProduceRang() {
        return keyProduceRang;
    }

    public static void setKeyProduceRang(int keyProduceRang) {
        SystemParamInit.keyProduceRang = keyProduceRang;
    }

    public static int getKeyWarningRang() {
        return keyWarningRang;
    }

    public static void setKeyWarningRang(int keyWarningRang) {
        SystemParamInit.keyWarningRang = keyWarningRang;
    }
}
