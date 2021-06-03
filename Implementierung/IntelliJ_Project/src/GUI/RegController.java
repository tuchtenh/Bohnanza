package GUI;

import LogIn.LogIn;
import Server.ServerAdresse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.rmi.ConnectException;
import java.rmi.ConnectIOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * RegController zum Registrieren eines Nutzers
 *
 * @author Nils Fitting
 * @version 1.0
 */
public class RegController {

    /*final String HOST = "localhost";
        final int PORT = 1099;*/

    final String HOST = ServerAdresse.getHOST();
    final int PORT = ServerAdresse.getPORT();

    @FXML
    TextField benutzer;

    @FXML
    PasswordField passwort;

    @FXML
    PasswordField passwortWdh;

    /**
     * Legt das Verhalten des Registierungs-Buttons fest
     *
     * @param event bei Klick wird versucht, den Benutzer in der DB zu registieren
     *              zeigt einen Fehler an, wenn das Passwort nicht gleich ist oder
     *              bereits ein Benutzer mit dem Namen existiert
     * @throws RemoteException   wenn bei Verbindung zum Server etwas schiefläuft
     * @throws IOException       wenn ein IO-Fehler auftritt
     * @throws NotBoundException wenn das Objekt nicht an die Registry gebunden ist
     */
    @FXML
    public void register(ActionEvent event) throws RemoteException, IOException, NotBoundException {
        try {
            String name = benutzer.getText();
            String passwort2 = passwort.getText();
            String passwort2Wdh = passwortWdh.getText();

            if (name.toLowerCase().startsWith("bot_")) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fehler");
                alert.setContentText("Namen mit Bot sind nicht erlaubt");
                alert.showAndWait();
            } else {

                Registry registry;
                registry = LocateRegistry.getRegistry(HOST, PORT);
                LogIn login = (LogIn) registry.lookup("LogIn");

                if (login.registrieren(name, passwort2, passwort2Wdh)) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Registriert");
                    alert.setContentText("Du hast dich erfolgreich registriert!");
                    alert.showAndWait();
                    ((Node) (event.getSource())).getScene().getWindow().hide();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Fehler");
                    alert.setContentText("Wahrscheinlich ist der Benutzername schon belegt!");
                    alert.showAndWait();
                }
            }
        } catch (ConnectIOException s) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setContentText("Server nicht gefunden - Bitte überprüfen Sie ihre Serveradresse");
            alert.showAndWait();
        } catch (ConnectException c) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setContentText("Server nicht gefunden - Bitte überprüfen Sie ihre Serveradresse");
            alert.showAndWait();
        }
    }

    /**
     * Schließen des Fensters
     *
     * @param event ActionsEvent, von Buton getriggerd
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    public void zurueck(ActionEvent event) throws RemoteException {

        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}
