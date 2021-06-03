package Chat;

import User.BenutzerImpl;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.swing.*;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.*;

public class ChatServerTest {
    private static BenutzerImpl be1;
    private static BenutzerImpl be2;
    private static BenutzerImpl be3;
    private static TextArea textarea1;
    private static TextArea textarea2;
    private static TextArea textarea3;
    private static TextField message;
    private static Map<String, ChatClientInterface> clients1;
    private static Map<String, ChatClientInterface> clients3;
    private ChatClient cc1;
    private ChatClient cc2;
    private ChatClient cc3;

    /**
     * erstellt neue Benutzer, TextAreas und ein TextField für Tests
     *
     * @throws RemoteException      wenn bei Verbindung zum Server etwas schiefläuft
     * @throws InterruptedException wenn die Ausführung unterbrochen wird
     */
    @BeforeClass
    public static void beforeclass() throws RemoteException, InterruptedException {
        be1 = new BenutzerImpl("1111", "pass", 0, 0);
        be2 = new BenutzerImpl("2222", "pass", 0, 0);
        be3 = new BenutzerImpl("3333", "pass", 0, 0);


        //damit Textarea funktioniert:
        final CountDownLatch latch = new CountDownLatch(1);
        SwingUtilities.invokeLater(() -> {
            new JFXPanel();
            latch.countDown();
        });
        latch.await();

        textarea1 = new TextArea();
        textarea2 = new TextArea();
        textarea3 = new TextArea();

        message = new TextField("Test");
    }

    /**
     * legt vor jedem Test neue ChatClients an
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Before
    public void Before() throws RemoteException {
        cc1 = new ChatClient(textarea1, be1);
        cc2 = new ChatClient(textarea2, be2);
        cc3 = new ChatClient(textarea3, be3);
    }


    /**
     * Tested ob Die nachricht an alle clients gesendent wurde
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void receiveMessage() throws RemoteException {
        ChatServer cs3 = new ChatServer();
        cs3.joinChat(cc1);
        cs3.joinChat(cc2);
        cs3.joinChat(cc3);
        cs3.receiveMessage(message.getText(), cc2);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        String ausgabe = "[" + format.format(new Date()) + "]" + cc2.getBenutzer().getName() + ": " + "Test" + "\n";
        assertEquals(ausgabe, textarea1.getText());
        assertEquals(ausgabe, textarea2.getText());
        assertEquals(ausgabe, textarea3.getText());
    }


    /**
     * Tested ob der ChatClient hingefuegt wurde
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void joinChat() throws RemoteException {
        clients1 = new ConcurrentHashMap<String, ChatClientInterface>();
        clients1.put("1111", cc1);
        clients1.put("2222", cc2);
        clients1.put("3333", cc3);
        ChatServer cs1 = new ChatServer();
        cs1.joinChat(cc1);
        cs1.joinChat(cc2);
        cs1.joinChat(cc3);
        assertEquals(clients1, cs1.clients);

    }


    /**
     * Tested ob der ChatClient aus dem Map geloescht wird
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void verlasseChat() throws RemoteException {
        clients3 = new ConcurrentHashMap<String, ChatClientInterface>();
        clients3.put("1111", cc1);
        clients3.put("2222", cc2);
        ChatServer cs2 = new ChatServer();
        cs2.joinChat(cc1);
        cs2.joinChat(cc2);
        cs2.joinChat(cc3);
        cs2.verlasseChat(cc3);
        assertEquals(clients3, cs2.clients);
        cs2.verlasseChat(cc2);
        assertNotEquals(clients3, cs2.clients);
    }

    /**
     * Tested ob der ChatClient aus dem Map geloescht wird, wenn er mit seinem Namen
     * genannt wird
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void verlasseChat2() throws RemoteException {
        clients3 = new ConcurrentHashMap<String, ChatClientInterface>();
        clients3.put("1111", cc1);
        clients3.put("2222", cc2);
        ChatServer cs2 = new ChatServer();
        cs2.joinChat(cc1);
        cs2.joinChat(cc2);
        cs2.joinChat(cc3);
        cs2.verlasseChat("3333");
        assertEquals(clients3, cs2.clients);
        cs2.verlasseChat("2222");
        assertNotEquals(clients3, cs2.clients);
    }


    /**
     * Tested ob die Richtigen Client Namen zurueck gegeben werden
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getNamen() throws RemoteException {
        Vector<String> vec = new Vector<>();
        vec.add("1111");
        vec.add("2222");
        vec.add("3333");
        ChatServer cs2 = new ChatServer();
        cs2.joinChat(cc1);
        cs2.joinChat(cc2);
        cs2.joinChat(cc3);
        Vector<String> vec2 = cs2.getNamen();
        assertTrue(vec.containsAll(vec2));
        assertTrue(vec2.containsAll(vec));
    }
}