package User;

import Exception.NameZuKurzException;
import Exception.PasswortZuKurzException;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Benutzer extends Remote {

  public String getName() throws RemoteException;
  public String getPasswort() throws RemoteException;

  public int getPunktestand() throws RemoteException;


  public int getGewonnene_Spiele() throws RemoteException;


  public void setName(String s) throws NameZuKurzException, RemoteException;


  public void setPasswort(String i) throws PasswortZuKurzException, RemoteException;

  public void setPunktestand(int i) throws RemoteException;

  public void setGewonnene_Spiele(int i) throws RemoteException;

}