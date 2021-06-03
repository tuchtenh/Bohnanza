package Chat;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.Date;


import User.Benutzer;

public class ChatClient extends UnicastRemoteObject implements ChatClientInterface {

    private Chat chat;

    private javafx.scene.control.TextArea ausgabe;

    private Benutzer b;

    public ChatClient(String objName, javafx.scene.control.TextArea chatAusgabe, Benutzer b) throws Exception{

        this.ausgabe=chatAusgabe;

        this.b=b;
    }

    public Benutzer getBenutzer(){
        return b;
    }

    @Override
    public void receiveMessage(String n, ChatClientInterface c) throws RemoteException {
        //get Zeit
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

        //Formatiere String
        String msg = "["+format.format(new Date())+"]"+c.getBenutzer().getName()+": "+n+"\n";

        ausgabe.appendText(msg);
    }
}