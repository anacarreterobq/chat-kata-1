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
import android.content.SharedPreferences;
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
        timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(),0, 500);
    }

    public class MyTimerTask extends TimerTask {

        @Override
        public void run() {
                try {
                    conect = new ServerListener(context);
                    SharedPreferences settings = context.getSharedPreferences("Chat-kata", context.MODE_PRIVATE);
                    nextSeq = settings.getInt(context.getUser_name(), 0);
                    conect.execute(url + "?next_seq=" + Integer.toString(nextSeq));

                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    public Timer getTimer(){
        return timer;
    }
}
