package LogIn;


public class LogIn_Impl implements LogIn {


    /**
     * Anmeldefunktion zum einloggen in das System.
     * @param s Benutzer name der im Datenbank schon liegt. darf nicht null sein.
     * @param pw Passwort des Benutzernames das im Datenbank schon liegt. darf nicht null sein.
     * @return true falls die anmeldung erfolgreich war, anderfalls false.
     */
    @Override
    public boolean anmelden(String s, String pw){
        return true;
    }

    /**
     * Stellt neue Anmeldedaten in die Datenbank bei erfolgreichem registrieren.
     * @param s Benutzername der noch nicht im Datenbank ist. darf nicht null sein.
     * @param pw Passwort das genutzt wird um spaeter anzmelden zu koennen. darf nicht null sein.
     * @param pw2 Passwort das mit pw ueberstimmt.
     * @return true falls die registrietung erfolgreich war, anderfalls false.
     */
    @Override
    public boolean registrieren(String s, String pw, String pw2) {
        return true;
    }

    /**
     *Nach einer erfolgreichen anmeldung wird der Benutzer zur Lobby weitergeleitet
     */
    @Override
    public void weiterleiten() {

    }
}


