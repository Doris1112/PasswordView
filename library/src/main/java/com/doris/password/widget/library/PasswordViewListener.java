package com.doris.password.widget.library;

/**
 * @author Doris
 * @date 2018/11/3
 */
public interface PasswordViewListener {

    /**
     * 错误
     *
     * @param wrongNumber 错误密码
     */
    void onFail(String wrongNumber);

    /**
     * 正确
     *
     * @param number 正确密码
     */
    void onSuccess(String number);
}
