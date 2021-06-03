package LogIn;


import User.Benutzer;

public class LogIn_Impl implements LogIn {


    @Override
    public boolean anmelden(String s, String pw) {
        return false;
    }


    @Override
    public boolean registrieren(String s, String pw, String pw2) {
        return false;
    }

    @Override
    public Benutzer ladeBenutzer(String s) {
        return null;
    }

}


