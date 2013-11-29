package org.ejmc.android.simplechat.model;

/**
 * Created with IntelliJ IDEA.
 * User: laura
 * Date: 29/11/13
 * Time: 9:21
 * To change this template use File | Settings | File Templates.
 */
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import org.ejmc.android.simplechat.ChatActivity;

public class RepeatListener {

    private ServerListener conect;
    private int nextSeq;
    private String url;
    private Timer timer;
    private ChatActivity context;

    public RepeatListener(int nSeq, ChatActivity context, String url){
        nextSeq = nSeq;
        this.url=url;
        this.context=context;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(),0, 1000);
    }

    public class MyTimerTask extends TimerTask {

        @Override
        public void run() {
                try {
                    conect = new ServerListener(context);
                    conect.execute(url + "?next_seq=" + Integer.toString(nextSeq));

                } catch (Exception e) {
                    e.printStackTrace();
                }

        }
    }


}
