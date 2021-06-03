package GUI;

import Server.ServerAdresse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;


/**
 * IPController, zum Eintragen der ServerAdresse
 *
 * @author Lukas Bayer
 */
public class IPController {

    @FXML
    TextField ip;

    @FXML
    TextField port;


    /**
     * Initialisiert das Fenster
     */
    @FXML
    public void initialize() {
        ip.setPromptText(ServerAdresse.getHOST());
        port.setPromptText(Integer.toString(ServerAdresse.getPORT()));
    }

    /**
     * Überprüft die eingebene Adresse auf formale Richtigkeit und übergibt sie der Klasse ServerAdresse
     *
     * @param event ActionEvent, das vom Button erzeugt wird.
     */
    @FXML
    public void setVerbindenButton(ActionEvent event) {

        try {
            if (ip.getText().equals("")) {
                ServerAdresse.setHOST(ServerAdresse.getHOST());
            } else {
                ServerAdresse.setHOST((ip.getText()));
            }

            if (port.getText().equals("")) {
                ServerAdresse.setPORT(ServerAdresse.getPORT());
            } else {
                ServerAdresse.setPORT(Integer.parseInt(port.getText()));
            }
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (NumberFormatException n) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setContentText("Bitte nur Zahlen im Adress- und Port-Feld verwenden");
            alert.showAndWait();
        }

    }

    /**
     * Methode zum Schließen des Fensters
     *
     * @param event ActionEvent, das vom Button erzeugt wird
     */
    @FXML
    public void schließen(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }


}