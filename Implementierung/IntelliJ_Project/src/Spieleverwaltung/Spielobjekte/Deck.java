package Spieleverwaltung.Spielobjekte;

import Spieleverwaltung.Spielemanager_Impl;
import Exception.StapelLeerException;

/**
 * Ein Deck ist der Kartenstapel der Spielkarten, von dem innerhalb des Spiels gezogen wird.
 */
public class Deck extends Kartenstapel {

    /**
     * Zieht die oberste Karte aus dem Deck, indem die oberste Karte ausgewählt wird
     * und mit removeKarte() aus dem Deck entfernt wird. Die gezogene Karte wird
     * anschließend zurückgegeben
     *
     * @return karte - die gezogene Karte
     * @exception StapelLeerException wenn das Deck bereits leer ist
     * @see Kartenstapel+removeKarte()
     */
    public Karte ziehe() throws StapelLeerException {
  return null;
  }

}