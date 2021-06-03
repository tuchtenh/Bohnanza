package Users;


public class Benutzer {

  private String name;

  private String passwort;

  private int punktestand;

  private int gewonnene_Spiele;

  /**
   * @param name
   * @param passwort
   * @param punktestand
   * @param gewonnene_Spiele
   */
  public Benutzer(String name, String passwort, int punktestand, int gewonnene_Spiele){
    this.name=name;
    this.passwort=passwort;
    this.punktestand=punktestand;
    this.gewonnene_Spiele=gewonnene_Spiele;
  }

  /**
   * @return
   */
  public String getName() {
    return this.name;
  }

  /**
   * @return
   */
  public String getPasswort() {
    return this.name;
  }

  /**
   * @return
   */
  public int getPunktestand() {
    return this.punktestand;
  }

  /**
   * @return
   */
  public int getGewonnene_Spiele() {
    return this.gewonnene_Spiele;
  }

  /**
   * @param s
   */
  public void setName(String s) {
    this.name=s;
  }

  /**
   * @param i
   */
  public void setPasswort(String i) {
    this.passwort=i;
  }

  /**
   * @param i
   */
  public void setPunktestand(int i) {
    this.punktestand=i;
  }

  /**
   * @param i
   */
  public void setGewonnene_Spiele(int i) {
    this.gewonnene_Spiele=i;
  }

}