package GUI;

import Exception.FeldVollException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

/**
 * KartenController, um Handkarten und Anbaukarten aus P2 anzubauen
 *
 * @author Nils Fitting
 */
public class KarteController {
    @FXML
    ImageView i;
    @FXML
    ChoiceBox choiceBox;
    SpielraumController spiel;

    int anzubauenIndex = -1; //-1 um bewusst Fehlermeldungen zu erzeugen, falls jemals irgendwo etwas schief geht
    boolean anbauenVonHand = false;

    /**
     * Initialisert die GUI für Anbauen in P1
     *
     * @param img   Bild der Karte, die angebaut wird
     * @param spiel Spielcontroller, von wo aus der Controller aufgerufen wurde
     */
    public void setUp(Image img, SpielraumController spiel) {
        anbauenVonHand = true;
        i.setImage(img);
        this.spiel = spiel;
        if (spiel.spielerAnzahl > 3) {
            choiceBox.getItems().add("Bohnenfeld 1");
            choiceBox.getItems().add("Bohnenfeld 2");
        } else {
            choiceBox.getItems().add("Bohnenfeld 1");
            choiceBox.getItems().add("Bohnenfeld 2");
            choiceBox.getItems().add("Bohnenfeld 3");
        }
    }

    /**
     * Initialisert die GUI für Anbauen in P3
     *
     * @param img            Bild der Karte, die angebaut wird
     * @param spiel          Spielcontroller, von wo aus der Controller aufgerufen wurde
     * @param anzubauenIndex Index, auf dem Feld es angebaut wird
     */
    public void setUp(Image img, SpielraumController spiel, int anzubauenIndex) {
        this.anzubauenIndex = anzubauenIndex;
        anbauenVonHand = false;
        i.setImage(img);
        this.spiel = spiel;
        if (spiel.spielerAnzahl > 3) {
            choiceBox.getItems().add("Bohnenfeld 1");
            choiceBox.getItems().add("Bohnenfeld 2");
        } else {
            choiceBox.getItems().add("Bohnenfeld 1");
            choiceBox.getItems().add("Bohnenfeld 2");
            choiceBox.getItems().add("Bohnenfeld 3");
        }
    }

    /**
     * Baut die entsprechende Hand- oder Anbaukarte aus P! bzw. P3 an das ausgewählte Bohnenfeld an
     *
     * @param event ActionEvent, von Button, der Methode startet
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn die URL falsch ist
     */

    public void anbauen(ActionEvent event) throws RemoteException, MalformedURLException {
        int index;
        String choice = (String) choiceBox.getValue();
        if (choice == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setContentText("Bitte Bohnenfeld auswählen");
            alert.showAndWait();
        } else {
            if (choice.equals("Bohnenfeld 1")) {
                index = 0;
            } else if (choice.equals("Bohnenfeld 2")) {
                index = 1;
            } else {
                index = 2;
            }
            try {
                if (anbauenVonHand) {
                    spiel.anbauenPhase1(index);
                } else {
                    spiel.anbauenPhase3(index, anzubauenIndex);
                }
                ((Node) (event.getSource())).getScene().getWindow().hide();
            } catch (FeldVollException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fehler");
                alert.setContentText("Bohnenart falsch oder Feld voll");
                alert.showAndWait();
            }
        }
    }
}
