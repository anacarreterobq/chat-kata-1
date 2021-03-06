package org.ejmc.android.simplechat.model;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
//import android.widget.ImageView;
import android.widget.TextView;
import org.ejmc.android.simplechat.R;

import java.util.ArrayList;

public class ChatList
        extends BaseAdapter {

    private ArrayList<Message> listadoMensajes;
    private LayoutInflater lInflater;

    public ChatList(Context context, ArrayList<Message> mensajes) {
        this.lInflater = LayoutInflater.from(context);
        this.listadoMensajes = mensajes;
    }

    @Override
    public int getCount() { return listadoMensajes.size(); }

    @Override
    public Object getItem(int arg0) { return listadoMensajes.get(arg0); }

    @Override
    public long getItemId(int arg0) { return arg0; }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        ContenedorView contenedor = null;

        if (arg1 == null){
            arg1 = lInflater.inflate(R.layout.msg_list, null);

            contenedor = new ContenedorView();
            contenedor.nombre = (TextView) arg1.findViewById(R.id.nick);
            contenedor.mensaje = (TextView) arg1.findViewById(R.id.mensaje);
           // contenedor.logo = (ImageView) arg1.findViewById(R.id.logo);


            arg1.setTag(contenedor);
        } else
            contenedor = (ContenedorView) arg1.getTag();

        Message versiones = (Message) getItem(arg0);
        contenedor.nombre.setText(versiones.getNick());
        contenedor.mensaje.setText(versiones.getMessage());
      //  contenedor.logo.setImageResource(R.drawable.flecha);

        return arg1;
    }

    public void setView(View v){

    }

    class ContenedorView{
        TextView nombre;
        TextView mensaje;
       // ImageView logo;
    }

}
