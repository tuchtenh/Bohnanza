package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;

import java.rmi.RemoteException;

/**
 * LöscheController zum Löschen eines Spielaccounts
 *
 * @author Nils Fitting
 * @version 1.0
 */

public class LoeschenController {
    @FXML
    PasswordField pw;

    LobbyController lobby;

    /**
     * setUp für LoeschenController
     *
     * @param lobby LobbyController, von wo aus aufgerufen
     */
    public void setUp(LobbyController lobby) {
        this.lobby = lobby;
    }

    /**
     * Löschen des Spielsaccounts
     *
     * @param event ActionsEvent, von Buton getriggerd
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    public void loeschen(ActionEvent event) throws RemoteException {
        String pass = pw.getText();
        if (lobby.lobby.loescheSpieler(lobby.aktiverBenutzer.getName(), pass)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Erfolg");
            alert.setContentText("Löschen erfolgreich - das Fenster wird nun geschlossen");
            alert.showAndWait();
            lobby.abmelden(event);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setContentText("Passwort falsch");
            alert.showAndWait();
        }
    }

    /**
     * Schließen des Fensters
     *
     * @param event ActionsEvent, von Buton getriggerd
     */
    public void schließen(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}
