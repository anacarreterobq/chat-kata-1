package org.ejmc.android.simplechat;


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


public class ChatActivity extends ListActivity {
    /** Called when the activity is first created. */

    private Bundle user_data;
    private ArrayList<Message> msg_list =  new ArrayList<Message>();
    private String user_name,user_password;
    private TextView user_name_tv;
    private EditText message_et;
    private ChatList adapter;
    private Button send_bt;
    private ListView chat_lv;
    private Gson message_gson;
    private String message;
    private AsyncTask<String, Void, String> response_server;
    private ServerListener conect;
    private ServerSender send;
    private int nextSeq;
    private JSONArray messages_array;


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

        conect = new ServerListener(this);
        conect.execute("http://172.19.209.30:8080/chat-kata/api/chat",Integer.toString(nextSeq));
        send = new ServerSender(this);

        user_name_tv.setText("Chat - "+user_name);
        nextSeq=0;


        send_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response_server=send.execute("http://172.19.209.30:8080/chat-kata/api/chat", message_gson.toJson(new Message(user_name,message_et.getText().toString())));

              //  msg_list.add(new Message(user_name,response_server..get().toString());
                conect.execute("http://172.19.209.30:8080/chat-kata/api/chat",Integer.toString(nextSeq));
          //      chat_lv.setSelection(chat_lv.getAdapter().getCount()-1);
                message_et.setText("");

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

    public void refresh(JSONObject json){
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
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        adapter = new ChatList(ChatActivity.this, msg_list);
        setListAdapter((ListAdapter) adapter);
        chat_lv.setSelection(chat_lv.getAdapter().getCount()-1);
    }

}
