package Spieleverwaltung.Spielobjekte;

/**
 * Eine Karte ist die Spielkarte des Spiels. Sie wird auch Bohne genannt und gehört
 * einer Bohnenart an und hat ihre Häufigkeit und ihren Wert in Talern vermerkt.
 */
public class Karte extends Kartenstapel {

  private String bohnenArt;
  private int haeufigkeit;
  private int talerwert;

  public Karte(String bohnenArt, int haeufigkeit, int talerwert) {
    this.bohnenArt = bohnenArt;
    this.haeufigkeit = haeufigkeit;
    this.talerwert = talerwert;
  }

  public String getBohnenArt() {
    return this.bohnenArt;
  }

  public int getHaeufigkeit() {
    return this.haeufigkeit;
  }

  public int getTalerwert() {
    return this.talerwert;
  }

}