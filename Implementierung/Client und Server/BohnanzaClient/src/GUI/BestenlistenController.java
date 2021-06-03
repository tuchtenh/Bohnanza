package GUI;

import Bestenliste.Bestenliste;
import User.Benutzer;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class BestenlistenController {

    @FXML
    TableView<Row> bestenliste;



    @FXML
    public void initialize() throws RemoteException, NotBoundException {

        List<Row> rows = makeData();

        makeColumns(bestenliste);
        bestenliste.getItems().addAll(rows);
    }

    public void makeColumns(TableView<Row> bestenliste) {
        TableColumn<Row, String> spieler = new TableColumn<>("Spieler");


        TableColumn<Row, String> spiele = new TableColumn<>("Gewonnene Spiele");


        TableColumn<Row, String> punkte = new TableColumn<>("Max Punkte");

        bestenliste.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        spieler.setMaxWidth(1f * Integer.MAX_VALUE * 50); // 50% width
        spiele.setMaxWidth(1f * Integer.MAX_VALUE * 30); // 30% width
        punkte.setMaxWidth(1f * Integer.MAX_VALUE * 20); // 20% width

        spieler.setCellValueFactory(param -> {
//                int index = Integer.parseInt(param.getTableColumn().getText());
            int index = param.getTableView().getColumns().indexOf(param.getTableColumn());
            List<Cell> cells = param.getValue().getCells();
            return new SimpleStringProperty(cells.size() > index ? cells.get(index).toString() : null);
        });
        spiele.setCellValueFactory(param -> {
//                int index = Integer.parseInt(param.getTableColumn().getText());
            int index = param.getTableView().getColumns().indexOf(param.getTableColumn());
            List<Cell> cells = param.getValue().getCells();
            return new SimpleStringProperty(cells.size() > index ? cells.get(index).toString() : null);
        });
        punkte.setCellValueFactory(param -> {
//                int index = Integer.parseInt(param.getTableColumn().getText());
            int index = param.getTableView().getColumns().indexOf(param.getTableColumn());
            List<Cell> cells = param.getValue().getCells();
            return new SimpleStringProperty(cells.size() > index ? cells.get(index).toString() : null);
        });
        bestenliste.getColumns().add(spieler);
        bestenliste.getColumns().add(spiele);
        bestenliste.getColumns().add(punkte);

    }


    public List<Row> makeData() {
        try {
            final String HOST = "localhost";
            final int PORT = 1099;
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

    public void close(ActionEvent event) throws Exception {

        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    static class Row {
        private final List<Cell> list = new ArrayList<>();

        public List<Cell> getCells() {
            return list;
        }
    }

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





