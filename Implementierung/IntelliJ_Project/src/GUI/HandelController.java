package GUI;

import Handel.Haendler;
import Spieleverwaltung.Akteur;
import Spieleverwaltung.Spielemanager;
import Spieleverwaltung.Spielobjekte.Karte;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * HandelController zum Handeln (GUI) zweier Spieler
 *
 * @author Nils Fitting, Lukas Bayer
 */

public class HandelController {

    @FXML
    ListView<ImageView> spielerKarten;
    @FXML
    ListView<ImageView> gegenspielerKarten;
    @FXML
    ListView<ImageView> handkarten;

    @FXML
    Button handeln;
    @FXML
    ListView<ImageView> stapel;
    @FXML
    Button close;
    @FXML
    Button min;

    Vector<ImageView> listeSpieler = new Vector<>();
    SpielraumController sc;
    Vector<String> handkartenNamen = new Vector<>();
    Vector<Karte> handkartenObjekte = new Vector<>();
    Vector<ImageView> listeStapel = new Vector<>();
    HashMap<ImageView, Integer> map = new HashMap<>();
    Vector<Integer> selectedIndizes = new Vector<>();
    Vector<Karte> angebot;
    Spielemanager spielemanager;
    Haendler partner;
    Haendler aktiverHaendler;
    Akteur aktiverSpieler;
    Stage stage;
    double kartenHoehe;


    /**
     * Methode, um Controller zu initialisieren und GUI anzupassen
     *
     * @param sc              SpielraumController, von wo aus aufgerufen
     * @param aktiverHaendler der Handelclient eines selbst
     * @param partner         der Handelclient des Gegenspielers
     * @param stage           die Stage, von wo aus aufgerufen
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn die URL falsch ist
     */
    public void setUp(SpielraumController sc, Haendler aktiverHaendler, Haendler partner, Stage stage) throws RemoteException, MalformedURLException {


        aktiverSpieler = aktiverHaendler.getSpieler();
        handkartenObjekte = aktiverSpieler.getHandkarten();
        this.sc = sc;
        this.stage = stage;
        this.spielemanager = sc.spielemanager;

        if (aktiverHaendler.getSpieler().getName().equals(spielemanager.getActPlayer().getName())) {
            handeln.setVisible(true);
        } else {
            handeln.setVisible(false);
        }

        refreshKarten();

        for (Karte k : sc.handkarten) {
            handkartenNamen.add(k.getBohnenArt());
            handkartenObjekte.add(k);
        }

        //Nur der aktive Spieler darf mit Stapelkarten handeln
        if (spielemanager.getActPlayer().getName().equals(aktiverSpieler.getName())) {
            int stapelsize = spielemanager.getAufgedeckteKarten().size();
            if (stapelsize > 0) {
                listeStapel.add(getResizedImageView(spielemanager.getAufgedeckteKarten().get(0).getImgURL()));
            }
            if (stapelsize > 1) {
                listeStapel.add(getResizedImageView(spielemanager.getAufgedeckteKarten().get(1).getImgURL()));
            }
        } else {
            stapel.setVisible(false);
        }
        stapel.setItems(FXCollections.observableList(listeStapel));

        this.partner = partner;
        this.aktiverHaendler = aktiverHaendler;
    }

    /**
     * Fügt eine Handkarte bei Klick dem Handelsangebot hinzu und setzt stattdessen ein Dummy ImageView
     *
     * @param event MouseEvent, das durch Klick auf Button erzeugt wird
     * @throws MalformedURLException wenn die URL falsch ist
     * @throws RemoteException       wenn etwas bei der Verbindung zum Server schiefläuft
     */
    @FXML
    public void wahlHandkarte(MouseEvent event) throws MalformedURLException, RemoteException {
        int pos = handkarten.getSelectionModel().getSelectedIndex();
        if (!selectedIndizes.contains(pos)) {
            selectedIndizes.add(pos);
            ImageView iv = handkarten.getItems().get(pos);
            listeSpieler.add(iv);

            spielerKarten.setItems(FXCollections.observableList(listeSpieler));

            //Map mit Bildobjekten
            map.put(iv, pos);
            try {
                //Ersetze Objekte durch leere View
                ImageView dummy = new ImageView(new Image("/GUI/Bilder/leer.png"));
                dummy.setPreserveRatio(true);
                dummy.setFitWidth(handkarten.getItems().get(0).getFitWidth());
                dummy.setFitHeight(handkarten.getItems().get(0).getFitHeight());
                handkarten.getItems().set(pos, dummy);
                handkarten.refresh();
            } catch (IndexOutOfBoundsException i) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Achtung");
                alert.setContentText("Bitte gültige Karte aus der Liste auswählen");
                alert.showAndWait();
            }
        }

        macheAngebot();
    }

    /**
     * Fügt eine Stapelkarte bei Klick dem Handelsangebot hinzu und setzt stattdessen ein Dummy ImageView
     *
     * @param event MouseEvent, das durch Klick auf Button erzeugt wird
     * @throws MalformedURLException wenn die URL falsch ist
     * @throws RemoteException       wenn etwas bei der Verbindung zum Server schiefläuft
     */
    @FXML
    public void wahlStapelkarte(MouseEvent event) throws MalformedURLException, RemoteException {
        int pos = stapel.getSelectionModel().getSelectedIndex();
        int keyvalue = (pos + 1) * (-1);

        if (!selectedIndizes.contains(keyvalue)) {
            selectedIndizes.add(keyvalue);
            ImageView iv = stapel.getItems().get(pos);
            listeSpieler.add(iv);
            spielerKarten.setItems(FXCollections.observableList(listeSpieler));

            //Map mit Bildobjekten
            map.put(iv, keyvalue);

            //Ersetze Objekte durch leere View
            ImageView dummy = new ImageView(new Image("/GUI/Bilder/leer.png"));
            dummy.setPreserveRatio(true);
            dummy.setFitWidth(handkarten.getItems().get(0).getFitWidth());
            dummy.setFitHeight(handkarten.getItems().get(0).getFitHeight());
            stapel.getItems().set(pos, dummy);
        }

        macheAngebot();
    }

    /**
     * Entfernt on click eine Karte aus dem Handelsangebot und fügt die wieder an die entsprechende
     * Stelle in den Handkarten oder Stapelkarten hinzu
     *
     * @param event MouseEvent, das durch Klick auf Button erzeugt wird
     * @throws MalformedURLException wenn die URL falsch ist
     * @throws RemoteException       wenn etwas bei der Verbindung zum Server schiefläuft
     */
    public void removeKarte(MouseEvent event) throws MalformedURLException, RemoteException {
        if (spielerKarten.getItems().size() > 0) {
            ImageView iv = spielerKarten.getSelectionModel().getSelectedItem();
            spielerKarten.getItems().remove(iv);
            Integer pos = map.get(iv);
            map.remove(iv);
            selectedIndizes.remove(pos);

            if (pos >= 0) {
                handkarten.getItems().set(pos, iv);
                handkarten.refresh();
            } else {
                pos = (pos * (-1)) - 1;
                stapel.getItems().set(pos, iv);
            }
        }

        macheAngebot();

    }

    /**
     * Unterbreitet dem Handelspartner das Angebot
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn die URL falsch ist
     */
    @FXML
    public void macheAngebot() throws RemoteException, MalformedURLException {
        Vector<Karte> angebot = new Vector<>();
        for (Integer index : selectedIndizes) {
            if (index >= 0) {
                angebot.add(aktiverSpieler.getHandkarten().get(index));
            } else {
                if (index == -1 && listeStapel.size() > 0) {
                    angebot.add(spielemanager.getAufgedeckteKarten().get(0));
                }
                if (index == -2 && listeStapel.size() > 1) {
                    angebot.add(spielemanager.getAufgedeckteKarten().get(1));
                }
            }
        }
        Platform.runLater(() -> {
            try {
                aktiverHaendler.setAngebot(angebot);
                partner.receiveAngebot();
            } catch (RemoteException r) {
                r.printStackTrace();
            } catch (MalformedURLException m) {
                m.printStackTrace();
            }
        });
        refreshAngebot();
    }

    /**
     * Aktiver Haendler unterbreitet den Handel und wartet auf die Antwort des Gegenspielers
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @FXML
    public void handelBeschluss() throws RemoteException {
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Warte auf Anwtort...");
        info.setContentText(partner.getSpieler().getName() + " muss erst noch akzeptieren. \n Klicke auf OK und warte auf Antwort...");
        info.showAndWait();
        disableGUI(true);

        new Thread(() -> {
            try {
                boolean succ = partner.acceptHandel();
                Platform.runLater(() -> {
                    if (succ) {
                        try {
                            akzeptiereHandel();
                        } catch (RemoteException r) {
                            r.printStackTrace();
                        } catch (MalformedURLException m) {
                            m.printStackTrace();
                        }
                    } else {
                        try {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Abgelehnt");
                            alert.setContentText(partner.getSpieler().getName() + " hat das Angebot abgelehnt!");
                            alert.showAndWait();
                            disableGUI(false);
                        } catch (RemoteException r) {
                            r.printStackTrace();
                        }
                    }
                });
            } catch (RemoteException r) {
                r.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException i) {
                i.printStackTrace();
            }

        }).start();
    }

    /**
     * Deaktiviert die GUI entsprechend der Eingabe
     *
     * @param status boolean, je nach gewünschter Wirkung
     */
    public void disableGUI(boolean status) {
        handkarten.setDisable(status);
        handeln.setDisable(status);
        gegenspielerKarten.setDisable(status);
        spielerKarten.setDisable(status);
        stapel.setDisable(status);
        close.setDisable(status);
    }

    /**
     * Händelt die Handelsanfrage des Gegenüber
     *
     * @return boolean, ob Handel angenommen oder nicht
     * @throws ExecutionException   wenn bei der Ausführung ein Fehler auftritt
     * @throws InterruptedException wenn die Ausführung unterbrochen wird
     */
    @FXML
    public boolean handelRequest() throws ExecutionException, InterruptedException {
        final FutureTask anfrage = new FutureTask(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                Alert alert = null;
                try {
                    alert = new Alert(Alert.AlertType.CONFIRMATION, partner.getSpieler().getName() + " möchte den Handel beschließen.\n Nimmst du an?", ButtonType.YES, ButtonType.NO);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES) {
                    akzeptiereHandel();
                    close();
                }
                return (alert.getResult() == ButtonType.YES);
            }
        });
        Platform.runLater(anfrage);
        return (boolean) anfrage.get();
    }

    /**
     * Aktualisiert die Angebotskarten
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn die URL falsch ist
     */
    public void refreshAngebot() throws RemoteException, MalformedURLException {
        angebot = partner.getAngebot();
        Vector<ImageView> bilder = new Vector<>();

        for (Karte k : angebot) {
            bilder.add(getResizedImageView(k.getImgURL()));
        }
        Platform.runLater(() -> {
            gegenspielerKarten.setItems(FXCollections.observableList(bilder));
        });
    }

    /**
     * Füht den Handel aus, wenn Handelspartner bestätigt hat
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn die URL falsch ist
     */
    @FXML
    public void akzeptiereHandel() throws RemoteException, MalformedURLException {
        sc.setHandel(partner.getAngebot());

        //das Entfernen einzelner Objekte ging nicht, da scheinbar irgendetwas in der Referenzierung nicht genau funktioniert
        //daher läuft das über Indizes

        Vector<Integer> stapelIndices = new Vector<>();
        for (Integer index : selectedIndizes) {
            if (index <= -1) {
                stapelIndices.add((index * (-1)) - 1);
            }
        }
        spielemanager.removeAufgedeckteKarten(stapelIndices);
        aktiverSpieler.removeKarten(selectedIndizes);
        sc.refreshHandkarten();
        sc.refresherServer.refreshStapel();
        close();
    }

    /**
     * Aktualisiert die HandkartenViews
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn die URL falsch ist
     */
    public void refreshKarten() throws RemoteException, MalformedURLException {

        Vector<ImageView> handkartenBilder = new Vector<ImageView>();

        //Workaround für die Höhe der Listenelemente
        this.kartenHoehe = handkarten.getPrefHeight() * 0.8;
        for (Karte k : handkartenObjekte) {
            handkartenBilder.add(getResizedImageView(k.getImgURL()));
        }
        handkarten.setItems(FXCollections.observableList(handkartenBilder));
    }

    /**
     * Gibt ein ImageView des Bildes, am Ort der eingegeben Url, zurück
     *
     * @param s String des Bildortes
     * @return ImageView mit dem Bild
     * @throws MalformedURLException wenn die URL falsch ist
     */
    public ImageView getResizedImageView(String s) throws MalformedURLException {
        Image i = sc.getImg(s);
        ImageView iv = new ImageView(i);
        iv.setPreserveRatio(true);
        iv.setFitHeight(kartenHoehe);
        return iv;
    }

    /**
     * Schließt das Handelfenster und setzt die GUI-Buttons entsprechend der Phase
     */
    public void close() {
        sc.handeln1.setDisable(false);
        sc.handeln2.setDisable(false);
        sc.handeln3.setDisable(false);
        sc.handeln4.setDisable(false);
        sc.cardContainer.setDisable(false);
        stage.close();
    }

    /**
     * Schließt den Handel, sobald ein Beteiligter das Fenster schließt
     *
     * @return boolean je nachdem, ob Fenster geschlossen wird.
     * @throws InterruptedException wenn Thread unterbrochen wird
     * @throws ExecutionException   wenn Fehler in der Ausführung
     */
    public boolean closeRequest() throws InterruptedException, ExecutionException {

        final FutureTask anfrage = new FutureTask(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                Alert alert = null;
                try {
                    alert = new Alert(Alert.AlertType.CONFIRMATION, partner.getSpieler().getName() + " hat den Handel beendet", ButtonType.OK);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                alert.show();
                close();
                return (alert.getResult() == ButtonType.OK);
            }
        });
        Platform.runLater(anfrage);
        return (boolean) anfrage.get();
    }

    /**
     * Minimiert das Fenster
     *
     * @param event Event von dem Fenster, von wo aus Methode aufgerufen wird
     */
    @FXML
    public void minimize(ActionEvent event) {
        Stage stage = (Stage) stapel.getScene().getWindow();
        stage.setIconified(true);
    }

    /**
     * Schließt das Fenster
     *
     * @param event Event von dem Fenster, von wo aus Methode aufgerufen wird
     */
    @FXML
    public void schließen(ActionEvent event) {
        sc.handelCloseRequest(partner);
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }


    public Vector<Karte> getAngebot() {
        return this.angebot;
    }

    public void setPartner(Haendler partner) {
        this.partner = partner;
    }

}
