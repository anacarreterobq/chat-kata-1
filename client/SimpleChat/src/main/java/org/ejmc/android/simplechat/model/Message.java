package org.ejmc.android.simplechat.model;


/**
 * Simple message.
 * 
 * @author startic
 * 
 */
public class Message {
    private String message;
    private String nick;


    public Message(String nick, String message){
        this.message=message;
        this.nick=nick;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }



    public String getMessage() {
        return message;
    }

    public String getNick() {
        return nick;
    }

}
