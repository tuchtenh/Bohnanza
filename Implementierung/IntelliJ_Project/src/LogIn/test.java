package LogIn;

public class test {
    public static void main(String[] args){
        String nutzer = "Boi";
        String password = "password";
        String passwordWdh = "password";
        Boolean registrieren = new Login_entwurf().registrieren(nutzer, password, passwordWdh);
        System.out.println(registrieren);

        //Boolean anmelden = new Login().anmelden(nutzer, password);
        //System.out.println(anmelden);


    }
}
