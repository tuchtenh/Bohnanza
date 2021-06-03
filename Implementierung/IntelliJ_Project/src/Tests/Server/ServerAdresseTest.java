package Tests.Server;

import Server.ServerAdresse;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ServerAdresseTest {
    private static ServerAdresse adr;

    /**
     * erstellt eine neue ServerAdresse für Tests
     */
    @BeforeClass
    public static void beforeClass() {
        adr = new ServerAdresse();
    }

    @Before
    public void before() {
        ServerAdresse.setHOST("localhost");
        ServerAdresse.setPORT(2222);
    }

    /**
     * testet, ob HOST korrekt zurückgegeben wird
     */
    @Test
    public void getHOST() {
        assertEquals("localhost", ServerAdresse.getHOST());
    }

    /**
     * testet, ob sich host anpassen lässt
     */
    @Test
    public void setHOST() {
        assertEquals("localhost", ServerAdresse.getHOST());
        ServerAdresse.setHOST("host");
        assertEquals("host", ServerAdresse.getHOST());
    }

    /**
     * testet, ob der korrekte PORT zurückgegeben wird
     */
    @Test
    public void getPORT() {
        assertEquals(2222, ServerAdresse.getPORT());
    }

    /**
     * testet, ob sich PORT anpassen lässt
     */
    @Test
    public void setPORT() {
        assertEquals(2222, ServerAdresse.getPORT());
        ServerAdresse.setPORT(2345);
        assertEquals(2345, ServerAdresse.getPORT());
    }
}