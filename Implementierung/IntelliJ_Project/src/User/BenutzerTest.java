package User;


import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import Exception.PasswortZuKurzException;
import Exception.NameZuKurzException;

public class BenutzerTest {

    private static Benutzer testUser;

    @BeforeClass
    public static void before() {
        testUser = new Benutzer("User","pw",0,1);
    }

    @Test
    public void getName() {
        testUser.setName("User");
        assertEquals("User",testUser.getName());
    }

    @Test
    public void getPasswort() {
        testUser.setPasswort("pw");
        assertEquals("pw",testUser.getPasswort());
    }

    @Test
    public void getPunktestand() {
        testUser.setPunktestand(3);
        assertEquals(3,testUser.getPunktestand());
    }

    @Test
    public void getGewonnene_Spiele() {
        testUser.setGewonnene_Spiele(2);
        assertEquals(2,testUser.getGewonnene_Spiele());
    }

    @Test
    public void setName() {
        testUser.setName("User1");
        assertEquals("User1",testUser.getName());
    }

    @Test (expected = NameZuKurzException.class)
    public void setName2() throws NameZuKurzException {
        String n = null;
        testUser.setName(n);
    }
    @Test (expected = NameZuKurzException.class)
    public void setName3() throws NameZuKurzException {
        testUser.setName("");
    }

    @Test
    public void setPasswort() {
        testUser.setPasswort("pw2");
        assertEquals("pw2",testUser.getPasswort());
    }

    @Test (expected = PasswortZuKurzException.class)
    public void setPasswort2() throws PasswortZuKurzException {
        testUser.setPasswort("");
    }

    @Test (expected = PasswortZuKurzException.class)
    public void setPasswort3() throws PasswortZuKurzException {
        String p = null;
        testUser.setPasswort(p);
    }

    @Test
    public void setPunktestand() {
        testUser.setPunktestand(4);
        assertEquals(4,testUser.getPunktestand());
    }

    @Test
    public void setGewonnene_Spiele() {
        testUser.setGewonnene_Spiele(5);
        assertEquals(5,testUser.getGewonnene_Spiele());
    }
}