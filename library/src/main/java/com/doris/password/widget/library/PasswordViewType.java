package com.doris.password.widget.library;

/**
 * @author Doris
 * @date 2018/11/3
 */

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.doris.password.widget.library.PasswordViewType.typeCheck;
import static com.doris.password.widget.library.PasswordViewType.typeSet;

/**
 * The type for this PasswordNumberView
 */
@IntDef({typeSet, typeCheck})
@Retention(RetentionPolicy.SOURCE)
public @interface PasswordViewType {

    int typeSet = 0;
    int typeCheck = 1;
}
