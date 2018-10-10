package com.example.cfinvo.hardlibrary;

public class HardControl {
    /* 定义LED控制的三个native方法 */
    /* 加了static可以省略类名直接在主方法调用,不加则必须先实例化后用实例调用 */
    public static native int ledCtrl(int which, int status);
    public static native int ledOpen();
    public static native void ledClose();

    /* 静态块：加了static只会执行一次,不加static则每调用一次都执行 */
    /* 可以使用Crtl+Alt+T生成捕获异常的代码 */
    static {
        try {
            System.loadLibrary("hardcontrol");/* 加载库,库名为hardcontrol */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
