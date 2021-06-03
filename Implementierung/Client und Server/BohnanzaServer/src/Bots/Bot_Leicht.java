package Bots;

import Spieleverwaltung.Spielobjekte.Bohnenfeld;
import Spieleverwaltung.Spielobjekte.Karte;
import User.Benutzer;

import java.util.Vector;

public class Bot_Leicht implements Bot {

    private String name;

    private Vector<Bohnenfeld> felder;

    private Vector<Karte>  handkarten;


    @Override
    public void handeln() {

    }

    @Override
    public void anbauen() {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String s) {

    }

    @Override
    public Benutzer getUser() {
        return null;
    }

    @Override
    public Vector<Bohnenfeld> getFelder() {
        return null;
    }

    @Override
    public Vector<Karte> getHandkarten() {
        return null;
    }

    @Override
    public void addKarte(Karte k) {

    }

    @Override
    public boolean isUser() {
        return false;
    }
}