package User;

import Exception.NameZuKurzException;
import Exception.PasswortZuKurzException;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Benutzer ist der registierte Benutzer der Software.
 * Er hat einen Namen, ein Passwort und es werden der Punktestand und seine gewonnenen Spiele
 * in der Datenbank gespeichert.
 * Name und Passwort sind von ihm selbst veränderbar, Punktestand und gewonnene Spiele dagegen
 * nicht. Sie werden vor und während jedem Spiel, das der Benutzer spielt, aktualisiert.
 */
public class BenutzerImpl extends UnicastRemoteObject implements Benutzer {

  private String name;

  private String passwort;

  private int punktestand;

  private int gewonnene_Spiele;


  public BenutzerImpl(String name, String passwort, int punktestand, int gewonnene_Spiele) throws RemoteException {
    this.name=name;
    this.passwort=passwort;
    this.punktestand=punktestand;
    this.gewonnene_Spiele=gewonnene_Spiele;
  }


  public String getName() throws RemoteException{
    return this.name;
  }

  public String getPasswort() throws RemoteException{
    return this.passwort;
  }

  public int getPunktestand() throws RemoteException{
    return this.punktestand;
  }


  public int getGewonnene_Spiele() throws RemoteException{
    return this.gewonnene_Spiele;
  }


  public void setName(String s) throws NameZuKurzException,RemoteException{
    if(s!=null && s.length()>=1) {
      this.name = s;
    }
    else{
      throw new NameZuKurzException();
    }
  }


  public void setPasswort(String i) throws PasswortZuKurzException, RemoteException {
    if(i!=null && i.length()>=1) {
      this.passwort = i;
    }
    else{
      throw new PasswortZuKurzException();
    }
  }

  public void setPunktestand(int i) throws RemoteException {
    this.punktestand=i;
  }

  public void setGewonnene_Spiele(int i) throws RemoteException{
    this.gewonnene_Spiele=i;
  }

}