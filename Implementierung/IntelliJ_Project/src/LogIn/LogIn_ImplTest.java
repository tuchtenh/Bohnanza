package LogIn;

import User.Benutzer;
import com.sun.deploy.association.RegisterFailedException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.security.auth.login.LoginException;

import static org.junit.Assert.*;

public class LogIn_ImplTest {

  private static LogIn_Impl u;
  private static Benutzer b;

   @BeforeClass
   public static void beforeClass(){
       u = new LogIn_Impl();
       b = new Benutzer("Boi", "passwort", 0,0);
    }

    // Erfolgreiches anmelden
    @Test
    public void anmeldenTest1() {
        assertTrue(u.anmelden("Boi", "password"));
    }

    // Falscher Benutzername
    @Test(expected = LoginException.class)
    public void anmeldenTest2() {
        u.anmelden("Boiiiiii", "password");
    }

    // Falscher Passwort
    @Test(expected = LoginException.class)
    public void anmeldenTest3() {
        u.anmelden("Boi", "pass");
    }

    // User mit dem namen schon vorhanden
    @Test(expected = RegisterFailedException.class)
    public void registrierenTest1() {
        u.registrieren("Boi", "passwort", "passwort");
    }

    // Erfolgreich registriert
    @Test
    public void registrierenTest2() {
        assertTrue(u.registrieren("Yoyoyo", "passwort", "passwort"));
    }

    // Passwoerter sind nicht gleich
    @Test(expected = RegisterFailedException.class)
    public void registrierenTest3() {
       u.registrieren("Yoyoyo", "passwort", "p111111");
    }

    //TODO
    @Test
    public void weiterleiten() {
    }
}