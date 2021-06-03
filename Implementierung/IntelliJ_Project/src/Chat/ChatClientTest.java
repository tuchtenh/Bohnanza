package Chat;

import User.BenutzerImpl;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.TextArea;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.swing.*;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertEquals;

public class ChatClientTest {
    private static ChatServer cs;
    private static ChatClient cc1;
    private static ChatClient cc2;
    private static TextArea textarea1;
    private static TextArea textarea2;
    private static BenutzerImpl be1;
    private static BenutzerImpl be2;

    /**
     * erstellt einen neuen ChatServer, Benutzer, TextAreas und ChatClients für Tests
     *
     * @throws RemoteException      wenn bei Verbindung zum Server etwas schiefläuft
     * @throws InterruptedException wenn die Ausführung unterbrochen wird
     */
    @BeforeClass
    public static void beforeclass() throws RemoteException, InterruptedException {
        cs = new ChatServer();

        be1 = new BenutzerImpl("1111", "pass", 0, 0);
        be2 = new BenutzerImpl("2222", "pass", 0, 0);

        //damit Textarea funktioniert:
        final CountDownLatch latch = new CountDownLatch(1);
        SwingUtilities.invokeLater(() -> {
            new JFXPanel();
            latch.countDown();
        });
        latch.await();

        textarea1 = new TextArea();
        textarea2 = new TextArea();

        cc1 = new ChatClient(textarea1, be1);
        cc2 = new ChatClient(textarea2, be2);
    }


    /**
     * Prueft ob der richtige Benutzer zurueck gegeben wird
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void getBenutzer() throws RemoteException {
        assertEquals(be2, cc2.getBenutzer());
    }


    /**
     * Tested ob der Client die richtige Nachricht erhaltet
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @Test
    public void receiveMessage() throws RemoteException {
        cs.joinChat(cc2);
        cs.joinChat(cc1);
        cc2.receiveMessage("Test text", cc1);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        String ausgabe = "[" + format.format(new Date()) + "]" + cc1.getBenutzer().getName() + ": " + "Test text" + "\n";
        assertEquals(ausgabe, textarea2.getText());

    }
}