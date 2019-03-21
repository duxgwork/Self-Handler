package com.github.kevin.library.core;

public class Looper {
    public MessageQueue mQueue;
    static ThreadLocal<Looper> sThreadLocal = new ThreadLocal<>();


    private Looper() {
        mQueue = new MessageQueue();
    }

    /**
     * 初始化Looper，并将其保存到ThreadLocal中
     */
    public static void prepare() {
        //判断是否是当前线程的Looper
        if (sThreadLocal.get() != null) {
            throw new RuntimeException("一个线程只能拥有一个Looper");
        }
        sThreadLocal.set(new Looper());

    }

    /**
     * 获取当前线程的Looper对象
     *
     * @return
     */
    public static Looper myLooper() {
        return sThreadLocal.get();//保证返回的Looper对象是当前线程的Looper对象
    }

    public static void loop() {
        Looper me = myLooper();
        if (me == null) {
            throw new RuntimeException("当前线程没有Looper对象，请调用prepare()");
        }
        MessageQueue queue = me.mQueue;
        for (; ; ) {
            Message msg = queue.next();
            if (msg != null) {
                msg.target.dispatchMessage(msg);
            }
        }

    }

}
