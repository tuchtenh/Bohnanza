package Raumverwaltung;

import User.Benutzer;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;


public interface Lobby extends Remote {


  public boolean abmelden() throws RemoteException;

  public boolean loescheSpieler(String nutzer, String passwort) throws RemoteException;

  public boolean spielVorraumErstellen(String name) throws RemoteException;

  public Vector<Spielvorraum> getRaeume() throws RemoteException;

}