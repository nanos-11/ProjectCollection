package com.nan.projectcollection.Utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 掩藏软键盘
 *
 * @author z
 */
public class HideSoftInputUtil {
    private static InputMethodManager imm;

    private static InputMethodManager getInputMethodManager(Context context) {
        if (imm == null) {
            imm = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        return imm;
    }

    /**
     * 隐藏键盘
     *
     * @param context
     * @param editText
     */
    public static void hideSoftInput(Context context, EditText editText) {
        getInputMethodManager(context).hideSoftInputFromWindow(
                editText.getWindowToken(), 0);
    }

    public static void hideSoftInput(Context context, View view) {
        getInputMethodManager(context).hideSoftInputFromWindow(
                view.getWindowToken(), 0);
    }

    /**
     * 展示键盘
     *
     * @param context
     * @param editText
     */
    public static void showSoftInput(Context context, EditText editText) {
        getInputMethodManager(context).showSoftInput(editText, 0);
    }

    public static void changeKeyBoard(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        if (imm.isActive()) {
            // imm.hideSoftInputFromWindow(getCurrentFocus()
            // .getWindowToken(),
            // InputMethodManager.HIDE_NOT_ALWAYS);
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
