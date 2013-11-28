package org.ejmc.android.simplechat;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.app.ListActivity;
import com.google.gson.Gson;
import org.ejmc.android.simplechat.model.ServerListener;
import org.ejmc.android.simplechat.model.Message;
import org.ejmc.android.simplechat.model.ChatList;
import org.ejmc.android.simplechat.model.ServerSender;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class ChatActivity extends ListActivity {

    private Timer T;
    private Bundle user_data;
    private ArrayList<Message> msg_list =  new ArrayList<Message>();
    private String user_name,user_password, message;
    private TextView user_name_tv;
    private EditText message_et;
    private ChatList adapter;
    private Button send_bt;
    private ListView chat_lv;
    private Gson message_gson;
    private AsyncTask<String, Void, String> response_server;
    private ServerListener conect;
    private ServerSender send;
    private int nextSeq;
    private JSONArray messages_array;
    private SharedPreferences prefs;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        message_gson = new Gson();
        user_data = this.getIntent().getExtras();
        user_name = user_data.getString("user_name");
        user_password=user_data.getString("user_password");

        user_name_tv=(TextView) findViewById(R.id.user_name_tv);
        message_et=(EditText) findViewById(R.id.msg_et);
        send_bt=(Button)findViewById(R.id.send_bt);
        chat_lv=(ListView)this.getListView();

        user_name_tv.setText("Chat - "+user_name);

        prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        nextSeq=prefs.getInt("nextSeq", 0);

        T=new Timer();
        T.scheduleAtFixedRate(new TimerTask() {

            @Override public void run() {
                conect = new ServerListener(ChatActivity.this);
                conect.execute("http://172.19.209.30:8080/chat-kata/api/chat",Integer.toString(nextSeq));
            }
        }, 1000, 1000);



        send_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send = new ServerSender(ChatActivity.this);
                response_server=send.execute("http://172.19.209.30:8080/chat-kata/api/chat", message_gson.toJson(new Message(user_name,message_et.getText().toString())));
            }
        });

    }

    @Override

    protected void onSaveInstanceState(Bundle guardarEstado) {
        super.onSaveInstanceState(guardarEstado);
        guardarEstado.putAll(guardarEstado);
    }

    @Override

    protected void onRestoreInstanceState(Bundle recEstado) {
        super.onRestoreInstanceState(recEstado);
    }

    public void refresh_msg(JSONObject json){
        try {
            messages_array = json.getJSONArray("messages");
            if(messages_array.length()!=0){
                for(int i=nextSeq;i<messages_array.length();i++){
                    JSONObject c = messages_array.getJSONObject(i);
                    msg_list.add(new Message(c.getString("nick"), c.getString("message")));
                }
            }
            nextSeq = json.getInt("nextSeq");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapter = new ChatList(ChatActivity.this, msg_list);
        setListAdapter((ListAdapter) adapter);
        chat_lv.setSelection(chat_lv.getAdapter().getCount()-1);
    }

    public void refresh_send(){
        message_et.setText("");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        prefs.edit().putInt("nextSeq", nextSeq).commit();
    }
}
