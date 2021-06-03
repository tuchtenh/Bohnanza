package Raumverwaltung;

import Spieleverwaltung.Spieler;
import Bots.Bot;

import javax.swing.*;
import java.rmi.Remote;
import java.rmi.RemoteException;


public interface Spielvorraum extends Remote{

  public void addSpieler(Spieler b) throws RemoteException;

  public String getName() throws RemoteException;

  public void setName(String s) throws RemoteException;


  public void removeBot(Bot b) throws RemoteException;


  public void addBot(Bot b) throws RemoteException;

  public Spielraum starteSpiel() throws RemoteException;

}