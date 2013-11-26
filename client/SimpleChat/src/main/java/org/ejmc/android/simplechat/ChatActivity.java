package org.ejmc.android.simplechat;


import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.app.ListActivity;
import org.ejmc.android.simplechat.model.Message;
import org.ejmc.android.simplechat.model.ChatList;
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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        user_data = this.getIntent().getExtras();
        user_name = user_data.getString("user_name");
        user_password=user_data.getString("user_password");

        user_name_tv=(TextView) findViewById(R.id.user_name_tv);
        message_et=(EditText) findViewById(R.id.msg_et);
        send_bt=(Button)findViewById(R.id.send_bt);
        chat_lv=(ListView)this.getListView();

        user_name_tv.setText("Chat - "+user_name);

        msg_list.add(new Message("Nombre 1", "Hola"));
        msg_list.add(new Message("Nombre 2", "Que pasa?"));
        msg_list.add(new Message("Nombre 3", "Aqui estamos"));
        msg_list.add(new Message("Nombre 4", "Ok"));
        refresh();

        send_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg_list.add(new Message(user_name,message_et.getText().toString()));
                refresh();
                chat_lv.setSelection(chat_lv.getAdapter().getCount()-1);
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

    public void refresh(){
        adapter = new ChatList(ChatActivity.this, msg_list);
        setListAdapter((ListAdapter) adapter);
    }
}
