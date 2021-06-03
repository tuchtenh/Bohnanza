package GUI;

import Bestenliste.Bestenliste;
import Server.ServerAdresse;
import User.Benutzer;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


/**
 * BestenlistenController zum Anzeigen einer Bestenliste
 *
 * @author Nils Fitting
 */

public class BestenlistenController {

    @FXML
    TableView<Row> bestenliste;


    /**
     * Initialisiert die Erstellung einer Bestenliste
     */
    @FXML
    public void initialize() {
        getSpalten(bestenliste);
        Platform.runLater(() -> {
            List<Row> rows = getDaten();
            bestenliste.getItems().addAll(rows);
        });
    }

    /**
     * Setzt im TableView die Spalten der Tabelle
     *
     * @param bestenliste der zu befüllende TableView
     */

    public void getSpalten(TableView<Row> bestenliste) {
        TableColumn<Row, String> spieler = new TableColumn<>("Spieler");


        TableColumn<Row, String> spiele = new TableColumn<>("Gewonnene Spiele");


        TableColumn<Row, String> punkte = new TableColumn<>("Max Punkte");

        bestenliste.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        spieler.setMaxWidth(1f * Integer.MAX_VALUE * 50); // 50% width
        spiele.setMaxWidth(1f * Integer.MAX_VALUE * 30); // 30% width
        punkte.setMaxWidth(1f * Integer.MAX_VALUE * 20); // 20% width

        spieler.setCellValueFactory(param -> {
            int index = param.getTableView().getColumns().indexOf(param.getTableColumn());
            List<Cell> cells = param.getValue().getCells();
            return new SimpleStringProperty(cells.size() > index ? cells.get(index).toString() : null);
        });

        spiele.setCellValueFactory(param -> {
            int index = param.getTableView().getColumns().indexOf(param.getTableColumn());
            List<Cell> cells = param.getValue().getCells();
            return new SimpleStringProperty(cells.size() > index ? cells.get(index).toString() : null);
        });

        punkte.setCellValueFactory(param -> {
            int index = param.getTableView().getColumns().indexOf(param.getTableColumn());
            List<Cell> cells = param.getValue().getCells();
            return new SimpleStringProperty(cells.size() > index ? cells.get(index).toString() : null);
        });

        bestenliste.getColumns().add(spieler);
        bestenliste.getColumns().add(spiele);
        bestenliste.getColumns().add(punkte);

    }

    /**
     * Pullt die Daten aus der Datenbank, erstellt eine Reihe (Row) pro Nutzer und fügt sie der Tabelle hinzu
     *
     * @return rows Liste aus Reihen von Benutzerstatistiken
     */
    public List<Row> getDaten() {
        try {
            final String HOST = ServerAdresse.getHOST();
            final int PORT = ServerAdresse.getPORT();
            Registry registry;
            registry = LocateRegistry.getRegistry(HOST, PORT);
            Bestenliste beste = (Bestenliste) registry.lookup("Bestenliste");

            Vector<Benutzer> liste = beste.getListe();
            List<Row> rows = new ArrayList<>();
            for (Benutzer ein : liste) {
                Row e = new Row();
                e.getCells().add(new Cell(ein.getName()));
                e.getCells().add(new Cell(Integer.toString(ein.getGewonnene_Spiele())));
                e.getCells().add(new Cell(Integer.toString(ein.getPunktestand())));
                rows.add(e);
            }
            return rows;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Schließt das Fenster, von dem aus das Event getriggerd wurde
     *
     * @param event ein durch die GUI erzeugtes Event
     */
    public void close(ActionEvent event) {

        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    /**
     * Eine Row-Class zum Anzeigen einzelner Reihen im TableView
     *
     * @author Nils Fitting
     * @version 1.0
     */
    static class Row {
        private final List<Cell> list = new ArrayList<>();

        public List<Cell> getCells() {
            return list;
        }
    }

    /**
     * Eine Cell-Class zum Befüllen der Reihen mit einzelnen Zellen
     *
     * @author Nils Fitting
     * @version 1.0
     */
    static class Cell {
        private final String value;

        public Cell(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }

    }

}





