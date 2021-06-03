package LogIn;

import java.rmi.RemoteException;

public class test {
    public static void main(String[] args) throws RemoteException {
        String nutzer = "Boi";
        String password = "password";
        String passwordWdh = "password";
        Boolean registrieren = new LogInImpl().anmelden(nutzer, passwordWdh);
        System.out.println(registrieren);

        //Boolean anmelden = new Login().anmelden(nutzer, password);
        //System.out.println(anmelden);


    }
}
