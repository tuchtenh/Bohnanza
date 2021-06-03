package Spieleverwaltung;

import Bots.Bot;
import Spieleverwaltung.Spielobjekte.Ablagestapel;
import Spieleverwaltung.Spielobjekte.Deck;
import java.util.Vector;

/**
 * Spielemanager_Impl ist ein Manager, der das ihm zugewiesene Spiel verwaltet.
 * Er startet das Spiel, indem er die zugehörigen Methoden des Phasenmanagers aufruft, kann
 * es beenden und bestimmt den Spieler, der aktuell am Zug ist.
 */
public class Spielemanager_Impl implements Spielemanager {

  private Spieler actPlayer;

  private Vector reihenfolge;

  private Ablagestapel ablage;
  private Deck deck;
  private Vector<Spieler> spieler;

  private Vector<Bot> bots;

  private Phasenmanager phasenmanager;


  public Spieler getActPlayer() {
    return null;
  }

  public void setActPlayer(Spieler s) {
  }

  /**
   * Beendet das vom SPielemanager verwaltete, aktuell laufende Spiel und schickt die
   * Spieler aus dem Spiel zurück in die Lobby
   *
   * @return true, wenn alle Spieler in der Lobby sind und das Spiel korrekt beendet wurde,
   * ansonsten false
   */
  @Override
  public boolean spielBeenden() {
    return false;
  }

  /**
   * Wird aufgerufen, wenn der Spieler, der einen Spielvorraum eröffnet hat, den Button "Spiel starten"
   * betätigt, öffnet die GUI des Spiels und ruft spielMain() auf
   */
  @Override
  public void spielStarten() {

  }

  /**
   * wird aufgerufen, wenn ein Spieler ein laufendes Spiel verlassen möchte oder während das Spiel
   * beendet wird. Der Spieler s wird aus dem Spielraum zurück in die Lobby geschickt.
   *
   * @param s - der Spieler, der das Spiel verlässt
   * @return true, wenn der Spieler das Spiel verlassen hat, ansonsten false
   */
  @Override
  public boolean spielVerlassen(Spieler s) {
    return false;
  }

  /**
   * Führt das eigentliche Spiel aus, benutzt Phasenmanager, um die einzelnen Phasen auszuführen
   * läuft so lange, bis spielBeenden() aufgerufen wird
   */
  @Override
  public void spielMain() {

  }
  /**
   * Liefert eine Liste mit den Spielern des Spiels in der Reihenfolge, in der sie am Zug sind
   *
   * @return spieler  - eine Liste von Spielern in der richtigen Reihenfolge
   */
  /*public List<Spieler> getReihenfolge() {
  return null;
  }
  */
}