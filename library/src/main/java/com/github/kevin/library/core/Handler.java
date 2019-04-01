package com.github.kevin.library.core;

public class Handler {
    private MessageQueue mQueue;

    public Handler() {
        Looper mLooper = Looper.myLooper();
        mQueue = mLooper.mQueue;
    }

    /**
     * 发送消息，压入队列
     *
     * @param msg
     */
    public void sendMessage(Message msg) {
        msg.target = this;
        mQueue.enqueueMessage(msg);
    }

    /**
     *
     * @param r
     */
    public void post(Runnable r) {
        sendMessage(getPostMessage(r));
    }

    /**
     *
     * @param r
     * @return
     */
    private static Message getPostMessage(Runnable r) {
        Message m = new Message();
        m.callback = r;
        return m;
    }


    /**
     * 处理消息
     *
     * @param msg
     */
    public void handleMessage(Message msg) {

    }

    /**
     *
     * @param message
     */
    private static void handleCallback(Message message) {
        message.callback.run();
    }

    /**
     * 分发消息
     *
     * @param msg
     */
    public void dispatchMessage(Message msg) {
        if (msg.callback != null) {
            handleCallback(msg);
        } else {
            handleMessage(msg);
        }
    }

}
