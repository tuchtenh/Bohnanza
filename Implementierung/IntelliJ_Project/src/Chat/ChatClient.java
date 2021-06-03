package Chat;

import User.Benutzer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Ein ChatClient, der dem zugehörigen Benutzer das Chatten ermöglicht.
 *
 * @author Lukas Bayer
 * @version 1.0
 */

public class ChatClient extends UnicastRemoteObject implements ChatClientInterface {

    private Chat chat;
    private javafx.scene.control.TextArea ausgabe;
    private Benutzer b;

    /**
     * Konstruktor des ChatClients
     *
     * @param chatAusgabe TextArea der GUI, in die Nachrichten angezeigt werden
     * @param b           Benutzer des Chats
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    public ChatClient(javafx.scene.control.TextArea chatAusgabe, Benutzer b) throws RemoteException {
        this.ausgabe = chatAusgabe;
        this.b = b;
    }

    public Benutzer getBenutzer() {
        return b;
    }

    /**
     * Formatiert die Nachricht die gesendet wurde und zeigt die an.
     *
     * @param n Inhalt der Nachricht
     * @param c Chatraum der die Nachricht anzeigen soll
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Override
    public void receiveMessage(String n, ChatClientInterface c) throws RemoteException {
        //get Zeit
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

        //Formatiere String
        String msg = "[" + format.format(new Date()) + "]" + c.getBenutzer().getName() + ": " + n + "\n";

        ausgabe.appendText(msg);
    }
}