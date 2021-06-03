package Bots;

import Spieleverwaltung.Spielobjekte.Bohnenfeld;
import Spieleverwaltung.Spielobjekte.Karte;

import java.util.Vector;

public class Bot_Leicht implements Bot {


    private String name;

    private Vector<Bohnenfeld> felder;

    private Vector<Karte>  handkarten;


    /**
     * leichter bot handelt nicht, funktion macht nichts
     */
    @Override
    public void handeln() {

    }

    /**
     * Methode um eine Karte auf dem Bohnenfeld anzubauen
     */
    @Override
    public void anbauen() {

    }
}