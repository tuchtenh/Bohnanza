package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.net.URL;

/**
 * SpielregelnController zum Anzeigen der Spielregeln
 *
 * @author Nils Fitting
 * @version 1.0
 */
public class SpielregelnController {

    @FXML
    Button minimize;

    @FXML
    WebView webView;

    @FXML
    AnchorPane pane;

    /**
     * Initialisierung der Spielregeln
     */
    @FXML
    public void initialize() {
        URL url = getClass().getResource("spielregeln.html");
        webView.getEngine().load(url.toExternalForm());

    }

    /**
     * Methode, um Fenster zu minimieren
     */
    @FXML
    public void minimize() {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setIconified(true);
    }

    /**
     * Methode, um Fenster zu schließen
     *
     * @param event ActionEvent, das von Button geworfen wird
     */
    @FXML
    public void schließen(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

}
