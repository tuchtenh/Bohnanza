package Spieleverwaltung;

import java.rmi.Remote;

/**
 * Spielemanager ist ein Interface, das laufende Spiele abstrahiert.
 * @see Raumverwaltung.Spielraum_Impl
 */
public interface Spielemanager extends Remote {

  /**
   * Beendet das vom SPielemanager verwaltete, aktuell laufende Spiel und schickt die
   * Spieler aus dem Spiel zurück in die Lobby
   *
   * @return true, wenn alle Spieler in der Lobby sind und das Spiel korrekt beendet wurde,
   * ansonsten false
   */
  public boolean spielBeenden();

  /**
   * Wird aufgerufen, wenn der Spieler, der einen Spielvorraum eröffnet hat, den Button "Spiel starten"
   * betätigt, öffnet die GUI des Spiels und ruft spielMain() auf
   */
  public void spielStarten();

  /**
   * wird aufgerufen, wenn ein Spieler ein laufendes Spiel verlassen möchte oder während das Spiel
   * beendet wird. Der Spieler s wird aus dem Spielraum zurück in die Lobby geschickt.
   *
   * @param s - der Spieler, der das Spiel verlässt
   * @return true, wenn der Spieler das Spiel verlassen hat, ansonsten false
   */

  public boolean spielVerlassen(Spieler s);
  /**
   * Führt das eigentliche Spiel aus, benutzt Phasenmanager, um die einzelnen Phasen auszuführen
   * läuft so lange, bis spielBeenden() aufgerufen wird
   */
  public void spielMain();

}