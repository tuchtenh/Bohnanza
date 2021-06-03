/*
package Chat;

import static org.junit.Assert.*;

import User.Benutzer;
import User.BenutzerImpl;
import org.junit.BeforeClass;
import org.junit.Test;

import java.rmi.RemoteException;
import java.util.Vector;

public class ChatWindowTest {

    private static ChatServer c;
    private static Nachricht n;
    private static Benutzer b;
    //private static Nachricht n1;
    //private static Nachricht n2;
    private static Vector<Nachricht> nach;

    @BeforeClass
    public static void BeforeClass() throws RemoteException {
        c = new ChatServer();
        n = new Nachricht("Boiii", "Test");
        //n1 = new Nachricht("Nutzer1", "Das ist ");
        //n2 = new Nachricht("Nutzer2", "ein Vogel");
        nach = new Vector<Nachricht>();
        nach.add(n);
        b = new BenutzerImpl("Boi","password",0,0);
    }

    @Test
    public void sendeNachricht() throws RemoteException {
        c.sendeNachricht(n,b);
        assertEquals(nach , c.getChat());
    }

    //Tests mit Date?
    //TODO test mit date
    @Test
    public void getChat() {

    }

}
*/