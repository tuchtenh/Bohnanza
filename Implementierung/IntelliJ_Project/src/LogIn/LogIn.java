package LogIn;

import java.rmi.Remote;

public interface LogIn extends Remote {


  public boolean anmelden(String s, String pw) throws FalscheAnmeldedatenException;


  public boolean registrieren(String s, String pw, String pw2);


  public void weiterleiten();

}