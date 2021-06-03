package LogIn;

import Exception.RegistrationException;
import User.BenutzerImpl;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.security.auth.login.LoginException;

import java.rmi.RemoteException;

import static org.junit.Assert.*;

public class LogIn_ImplTest {

  private static LogIn_Impl u;
  private static BenutzerImpl b;

   @BeforeClass
   public static void beforeClass() throws RemoteException {
       u = new LogIn_Impl();
       b = new BenutzerImpl("Boi", "passwort", 0,0);
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
    @Test(expected = RegistrationException.class)
    public void registrierenTest1() {
        u.registrieren("Boi", "passwort", "passwort");
    }

    // Erfolgreich registriert
    @Test
    public void registrierenTest2() {
        assertTrue(u.registrieren("Yoyoyo", "passwort", "passwort"));
    }

    // Passwoerter sind nicht gleich
    @Test(expected = RegistrationException.class)
    public void registrierenTest3() {
       u.registrieren("Yoyoyo", "passwort", "p111111");
    }


}