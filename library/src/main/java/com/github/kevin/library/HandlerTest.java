package com.github.kevin.library;

import com.github.kevin.library.core.Handler;
import com.github.kevin.library.core.Looper;
import com.github.kevin.library.core.Message;

import java.util.UUID;

public class HandlerTest {

    public static void main(String[] args) {
        //同一个进程中的内存可以共享，比如UI线程中创建的Handler，在其他子线程也能使用
        //Handler和Looper的创建必须在UI线程
        Looper.prepare();
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                System.out.println("main = " + Thread.currentThread().getName() +
                        ",msg = " + msg.toString());
            }
        };
        for (int i = 0; i < 10; i++) {
            new Thread() {
                @Override
                public void run() {
                    while (true) {
                        Message msg = new Message();
                        synchronized (UUID.class) {
                            msg.obj = UUID.randomUUID().toString();
                        }
                        System.out.println("send = " + Thread.currentThread().getName() +
                                ",msg = " + msg.toString());
                        handler.sendMessage(msg);
                    }
                }
            }.start();
        }
        Looper.loop();
    }


}

