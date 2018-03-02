package com.base.sdk.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.TouchDelegate;
import android.view.View;
import android.view.inputmethod.InputMethodManager;



import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;


/**
 * date：     2015/8/19 0019 17:45
 * <p/>       公共类,主要用于一些常用的方法
 * modify by
 */
public class CommonUtil {

    private static final String TAG = CommonUtil.class.getSimpleName();
    private static final String KEY_LAUNCHER = "com.tomtop.shop.ActivityAlias1";

    /**
     * 根据输入法状态打开或隐藏输入法
     *
     * @param context 上下文
     */
    public static void toggleSoftInput(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 隐藏输入键盘
     *
     * @param context
     * @param view
     */
    public static void hideSoftInput(Context context, View view) {
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 移除焦点
     *
     * @param view view
     */
    public static void removeFocus(View view) {
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
    }





    /**
     * 获取随机字符串
     *
     * @param len
     * @return
     */
    public static String getRandomString(int len) {
        String returnStr;
        char[] ch = new char[len];
        Random rd = new Random();
        for (int i = 0; i < len; i++) {
            ch[i] = (char) (rd.nextInt(9) + 97);
        }
        returnStr = new String(ch);
        return returnStr;
    }


    /**
     * 对当前屏幕进行截屏操作
     */
    public static Bitmap captureScreen(Activity activity) {

        activity.getWindow().getDecorView().setDrawingCacheEnabled(true);

        Bitmap bmp = activity.getWindow().getDecorView().getDrawingCache();

        return bmp;

    }

    /**
     * 判断当前日期是否在给定的两个日期之间
     * @param beginDate 开始日期
     * @param endDate 结束日期
     */
    public static boolean compareDateState(String beginDate, String endDate) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c.setTimeInMillis(System.currentTimeMillis());
            c1.setTime(df.parse(beginDate));
            c2.setTime(df.parse(endDate));
        } catch (java.text.ParseException e) {
            return false;
        }
        int resultBegin = c.compareTo(c1);
        int resultEnd = c.compareTo(c2);
        return resultBegin > 0 && resultEnd < 0;
    }

    //获取设备唯一ID号
    //需加入<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
    //和<uses-permission android:name="android.permission.BLUETOOTH"/>权限
//    public static String getDeviceUniqueId(Context context) {
//        TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
//        String m_szImei = TelephonyMgr.getDeviceId();
//
//        String m_szDevIDShort = "35" + //we make this look like a valid IMEI
//                Build.BOARD.length() % 10 +
//                Build.BRAND.length() % 10 +
//                Build.CPU_ABI.length() % 10 +
//                Build.DEVICE.length() % 10 +
//                Build.DISPLAY.length() % 10 +
//                Build.HOST.length() % 10 +
//                Build.ID.length() % 10 +
//                Build.MANUFACTURER.length() % 10 +
//                Build.MODEL.length() % 10 +
//                Build.PRODUCT.length() % 10 +
//                Build.TAGS.length() % 10 +
//                Build.TYPE.length() % 10 +
//                Build.USER.length() % 10; //13 digits
//
//        String m_szAndroidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
//
//        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//        String m_szWLANMAC = wm.getConnectionInfo().getMacAddress();
//
//        BluetoothAdapter m_BluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//        String m_szBTMAC = m_BluetoothAdapter == null ? "" : m_BluetoothAdapter.getAddress();
//
//        /**
//         * 综上所述，我们一共有五种方式取得设备的唯一标识。它们中的一些可能会返回null，或者由于硬件缺失、权限问题等获取失败。
//         但你总能获得至少一个能用。所以，最好的方法就是通过拼接，或者拼接后的计算出的MD5值来产生一个结果。
//         */
//        String uniqueId = m_szImei + m_szDevIDShort + m_szAndroidID + m_szWLANMAC + m_szBTMAC;
//        return HashCoderUtil.md5Crypt(uniqueId.getBytes());
//    }

    /**
     * description 增加view的触摸范围
     */
    public static void expandViewTouchDelegate(final View view, final int top, final int bottom, final int left, final int right) {

        ((View) view.getParent()).post(new Runnable() {
            @Override
            public void run() {
                Rect bounds = new Rect();
                view.setEnabled(true);
                view.getHitRect(bounds);

                bounds.top -= top;
                bounds.bottom += bottom;
                bounds.left -= left;
                bounds.right += right;

                TouchDelegate touchDelegate = new TouchDelegate(bounds, view);

                if (View.class.isInstance(view.getParent())) {
                    ((View) view.getParent()).setTouchDelegate(touchDelegate);
                }
            }
        });
    }

}
