package Spieleverwaltung;


public class Phasenmanager {

  /**
   * Der actPlayer muss während dieser Phase die erste Karte auf seiner Hand anbauen
   * Er hat außerdem die Möglichkeit, noch eine zweite Karte anzubauen
   *
   * @param actPlayer - der Spieler, der gerade am Zug ist; darf nicht null sein
   * @return true, wenn die Anbau-Phase abgeschlossen ist, ansonsten false
   */
  public boolean anbauphase(Spieler actPlayer) {
    return false;
  }

  /**
   * Zwei Karten werden vom Deck gezogen. Der actPlayer hat die Möglichkeit, diese direkt anzubauen
   * oder mit seinen Mitspielern zu handeln. Sowohl actPlayer als auch Mitspieler können während
   * dieser Phase Angebote (aber jeweils nur mit dem actPlayer) machen. Alle gehandelten Karten, sowie
   * die Karten, die während dieser Phase gezogen werden, müssen danach angebaut werden.
   *
   * @param actPlayer - der Spieler, der gerade am Zug ist; darf nicht null sein
   * @return true, wenn die Handels-Phase abgeschlossen ist, ansonsten false
   */
  public boolean handelsphase(Spieler actPlayer) {
  return false;
  }

  /**
   * Während dieser Phase werden dem actPlayer 3 Karten aus dem Deck hinten in die Handkarten gegeben.
   *
   * @param actPlayer - der Spieler, der gerade am Zug ist; darf nicht null sein
   * @return nextPlayer - der Spieler, der nach dem actPlayer am Zug ist; darf nicht null sein
   */
  public Spieler endphase(Spieler actPlayer) {
    return null;
  }

}