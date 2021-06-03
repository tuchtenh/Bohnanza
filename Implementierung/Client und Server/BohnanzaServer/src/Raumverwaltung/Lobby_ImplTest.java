package Raumverwaltung;

import User.BenutzerImpl;
import org.junit.BeforeClass;
import org.junit.Test;

import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Vector;

import static org.junit.Assert.*;

public class Lobby_ImplTest {
    private static Lobby_Impl lobby;
    private static BenutzerImpl benutzer;
    private static Vector<Spielvorraum_Impl> v;
    private static Spielvorraum_Impl svr1;
    private static Spielvorraum_Impl svr2;

    @BeforeClass
    public static void beforeclass() throws RemoteException {
        svr1 = new Spielvorraum_Impl("TestVorraum1");
        svr2 = new Spielvorraum_Impl("TestVorraum2");
        lobby = new Lobby_Impl();
        benutzer = new BenutzerImpl("Boi", "passwort", 0,0);
        v = new Vector<Spielvorraum_Impl>();
        v.add(svr1);
    }

    // Abmelden von der Lobby
    @Test
    public void abmelden() {
        try {
            assertTrue(lobby.abmelden());
        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }

    // Loescht den Benutzer aus der Datenbank
    @Test
    public void loescheSpieler() {
        try{
            assertTrue(lobby.loescheSpieler(benutzer.getName(),password(benutzer.getPasswort())));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // Tested ob ein Spielraum erstellt wurde.
   @Test
    public void spielVorraumErstellen1() throws RemoteException {
       lobby.spielVorraumErstellen("TestVorraum1");
        assertEquals(v, lobby.getRaeume());
    }

    // Spielrraumname existiert schon
    @Test
    public void spielVorraumErstellen2() throws RemoteException {
        lobby.spielVorraumErstellen("Test");
        assertFalse(lobby.spielVorraumErstellen("Test"));
    }

    //TODO
    @Test
    public void getRaeume() {
        try {
            Vector<Spielvorraum_Impl> vv;
            vv = new Vector<>(4);
            vv.add(svr1);
            vv.add(svr2);
            lobby.spielVorraumErstellen("TestVorraum2");
            assertEquals(vv, lobby.getRaeume());
        }catch (Exception e){
            e.printStackTrace();
        }
    }







    private static String password(String password) {
        String generatedPassword = null;
        try {
            //MD5 Hash
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(password.getBytes());
            byte[] bytes = md5.digest();
            //Konvertierung in Hexadezimalsystem
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Gehashtes Passwort
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return generatedPassword;
    }



}