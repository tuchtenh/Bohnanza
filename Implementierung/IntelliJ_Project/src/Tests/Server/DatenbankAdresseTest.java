package Tests.Server;

import Server.DatenbankAdresse;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DatenbankAdresseTest {
    private static DatenbankAdresse adr;

    /**
     * erstellt eine neue DatenbankAdresse für Tests
     */
    @BeforeClass
    public static void beforeClass() {
        adr = new DatenbankAdresse();
    }

    /**
     * testet, ob der JdbcDriver korrekt zurückgegeben wird
     */
    @Test
    public void getJdbcDriver() {
        assertEquals("org.postgresql.Driver", DatenbankAdresse.getJdbcDriver());
    }

    /**
     * testet, ob die korrekte DbUrl zurückgegeben wird
     */
    @Test
    public void getDbUrl() {
        assertEquals("jdbc:postgresql://localhost:5432/bohnanza", DatenbankAdresse.getDbUrl());
    }

    /**
     * testet, ob PASS korrekt zurückgegeben wird
     */
    @Test
    public void getPASS() {
        assertEquals("root", DatenbankAdresse.getPASS());
    }

    /**
     * testet, ob USER korrekt zurückgegeben wird
     */
    @Test
    public void getUSER() {
        assertEquals("postgres", DatenbankAdresse.getUSER());
    }
}