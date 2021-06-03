package User;

import Exception.NameZuKurzException;
import Exception.PasswortZuKurzException;

/**
 * Benutzer ist der registierte Benutzer der Software.
 * Er hat einen Namen, ein Passwort und es werden der Punktestand und seine gewonnenen Spiele
 * in der Datenbank gespeichert.
 * Name und Passwort sind von ihm selbst veränderbar, Punktestand und gewonnene Spiele dagegen
 * nicht. Sie werden vor und während jedem Spiel, das der Benutzer spielt, aktualisiert.
 */
public class Benutzer {

  private String name;

  private String passwort;

  private int punktestand;

  private int gewonnene_Spiele;


  public Benutzer(String name, String passwort, int punktestand, int gewonnene_Spiele){
    this.name=name;
    this.passwort=passwort;
    this.punktestand=punktestand;
    this.gewonnene_Spiele=gewonnene_Spiele;
  }


  public String getName() {
    return this.name;
  }

  public String getPasswort() {
    return this.passwort;
  }

  public int getPunktestand() {
    return this.punktestand;
  }


  public int getGewonnene_Spiele() {
    return this.gewonnene_Spiele;
  }


  public void setName(String s){
    this.name=s;
  }


  public void setPasswort(String i) throws PasswortZuKurzException {
    this.passwort=i;
  }

  public void setPunktestand(int i) throws NameZuKurzException {
    this.punktestand=i;
  }

  public void setGewonnene_Spiele(int i) {
    this.gewonnene_Spiele=i;
  }

}