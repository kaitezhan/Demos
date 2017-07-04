package com.boneix.base.utils.encrypt;


import com.boneix.base.utils.common.StringUtils;
import com.boneix.base.utils.common.ValidationUtils;

/**
 * Created by rzhang on 2017/4/28.
 */
public class EncryptByStarsUtils {

    private EncryptByStarsUtils() {
    }

    /**
     * 身份证加密 只保留前三和后五个字符
     *
     * @param idCard
     * @return
     */
    public static String encryptIdCard(String idCard) {
        if (StringUtils.isNotEmpty(idCard) && idCard.length() > 10) {
            StringBuilder sb = new StringBuilder();
            sb.append(idCard.substring(0, 3));
            sb.append("****");
            sb.append(idCard.substring((idCard.length() - 5)));
            return sb.toString();
        }
        return "";
    }

    /**
     * 姓名加密 只保留最后一个字符
     *
     * @param bankAccountName
     * @return
     */
    public static String encryptAccountName(String bankAccountName) {
        if (StringUtils.isNotEmpty(bankAccountName) && bankAccountName.length() > 1) {
            StringBuilder sb = new StringBuilder();
            if (bankAccountName.length() <= 2) {
                sb.append("*");
            } else {
                sb.append(bankAccountName.substring(0, 1));
                sb.append("*");
            }
            sb.append(bankAccountName.substring((bankAccountName.length() - 1)));
            return sb.toString();
        }
        return "";
    }

    /**
     * 手机号加密 只保留前3位和后4位
     *
     * @param bankAccountMobile
     * @return
     */
    public static String encryptMobile(String bankAccountMobile) {
        if (null != bankAccountMobile && ValidationUtils.validateMobile(bankAccountMobile)) {
            StringBuilder sb = new StringBuilder();
            sb.append(bankAccountMobile.substring(0, 3));
            sb.append("****");
            sb.append(bankAccountMobile.substring((bankAccountMobile.length() - 4)));
            return sb.toString();
        }
        return "";
    }

    /**
     * 银行卡加密 只保留后4位
     *
     * @param bankAccount
     * @return
     */
    public static String encryptBankAccount(String bankAccount) {
        if (StringUtils.isNotEmpty(bankAccount) && bankAccount.length() > 4) {
            StringBuilder sb = new StringBuilder();
            sb.append("**** **** ****");
            sb.append(bankAccount.substring((bankAccount.length() - 4)));
            return sb.toString();
        }
        return "";
    }
}
