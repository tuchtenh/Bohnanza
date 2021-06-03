package LogIn;

import User.Benutzer;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface LogIn extends Remote {


  public boolean anmelden(String s, String pw) throws RemoteException;


  public boolean registrieren(String s, String pw, String pw2) throws RemoteException;


  public Benutzer ladeBenutzer(String nutzer) throws RemoteException;


}