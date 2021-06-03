package Chat;

import User.Benutzer;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ChatWindowTest {

    private static ChatWindow c;
    private static ChatWindow gc;

    @BeforeClass
    public static void BeforeClass(){
        c = new ChatWindow();
        gc = new ChatWindow();
    }

    @Test
    public void sendeNachricht() {
        c.sendeNachricht("aaa");
        assertEquals("aaa",c.getChat());
    }

    //TODO
    //@Test
    //public String getChat() {
     //   assertEquals(" ", gc.getChat());
    //}

}