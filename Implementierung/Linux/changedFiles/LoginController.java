package GUI;

import Chat.ChatServerInterface;
import Exception.BereitsEingeloggtException;
import LogIn.LogIn;
import Observer.Refresher.Lobby.RefresherLobbyServerInterface;
import Raumverwaltung.Lobby;
import Server.ServerAdresse;
import User.Benutzer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;

import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.rmi.ConnectException;
import java.rmi.ConnectIOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * LoginController, um die Anwendung zu starten und sich in das Spiel einzuloggen
 *
 * @author Nils Fitting, Lukas Bayer
 * @version 1.0
 */
public class LoginController extends Application {

    /*final String HOST = "localhost";
    final int PORT = 1099;*/

    @FXML
    TextField name;
    @FXML
    PasswordField passwort;

    private String HOST = ServerAdresse.getHOST();
    private int PORT = ServerAdresse.getPORT();
    private Benutzer b;
    private double xOffset = 0;
    private double yOffset = 0;

    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    private int width = gd.getDisplayMode().getWidth();


    /**
     * Öffnet das ServerIP-Fenster
     *
     * @param primaryStage die übergebene Stage
     * @throws Exception wenn die main() fehlschlägt
     */
    @FXML
    public void start(Stage primaryStage) throws Exception {
        String gui = "";
        if(width < 1440){
            gui = "LogIn_800.fxml";
        } else {
            gui = "Login.fxml";
        }

        primaryStage.getIcons().add(new Image("/GUI/Bilder/cover.jpg"));
        Parent root = FXMLLoader.load(getClass().getResource(gui));
        primaryStage.setTitle("LogIn");
        primaryStage.setScene(new Scene(root));
        primaryStage.initStyle(StageStyle.UNDECORATED);

        primaryStage.show();
    }

    /**
     * Schließt das Fenster
     *
     * @param event ActionEvent, von Button getriggerd
     */
    public void abmelden(ActionEvent event){
        ((Node) (event.getSource())).getScene().getWindow().hide();
        System.exit(0);
    }

    /**
     * Legt das Verhalten des Registierungs-Buttons fest
     *
     * @throws Exception wenn die main() fehlschlägt
     */
    @FXML
    public void setRegButton() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Registrierung.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Registrierung");
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image("/GUI/Bilder/cover.jpg"));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }


    /**
     * Methode, um sich auch über "Enter" anzumelden, ohne Button zu drücken
     *
     * @param ae ActionEvent, von Button getriggerd
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     * @throws NotBoundException wenn das Objekt nicht an die Registry gebunden ist
     * @throws IOException wenn ein IO-Fehler auftritt
    */
    @FXML
    public void onEnter(ActionEvent ae) throws RemoteException, NotBoundException, IOException {
        setAnmeldeButton(ae);
    }

    /**
     * Legt das Verhalten des Anmelde-Buttons fest
     *
     * @param event - meldet den Benutzer beim Klick auf den Button an oder gibt
     *              einen Fehler zurück, wenn er nicht angemeldet werden kann
     * @throws NotBoundException wenn Objekt nicht an die Registry gebunden
     * @throws IOException wenn ein IO Fehler auftritt
     */
    @FXML
    public void setAnmeldeButton(ActionEvent event) throws NotBoundException, IOException {
        try {

            String benutzer = name.getText();
            String password = passwort.getText();

            Registry registry;

            //Da durch ServerReg eventuell neu gesetzt, wird hier aktualisiert
            HOST = ServerAdresse.getHOST();
            PORT = ServerAdresse.getPORT();

            registry = LocateRegistry.getRegistry(HOST, PORT);
            LogIn login = (LogIn) registry.lookup("LogIn");

            RefresherLobbyServerInterface refresherLobby = (RefresherLobbyServerInterface) registry.lookup("LobbyRefresher");
            ChatServerInterface chatServer = (ChatServerInterface) registry.lookup("LobbyChat");

            if (login.anmelden(benutzer, password)) {
                //Vor dem Anmelden wird der Benutzer aus allen möglichen Sachen rausgeschmissen, falls er noch irgendwo registriert ist

                //chatServer.verlasseChat(benutzer);

                //refresherLobby.leave(benutzer);

                //TODO:Throw Exception, falls Benutzer nicht gefunden
                this.b = login.ladeBenutzer(benutzer);

                String gui = "";
                if (width < 1440) {
                    gui = "Lobby_800.fxml";
                } else {
                    gui = "Lobby.fxml";
                }

                FXMLLoader loader = new FXMLLoader(getClass().getResource(gui));

                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("Lobby");
                stage.setScene(new Scene(root));
                stage.getIcons().add(new Image("/GUI/Bilder/cover.jpg"));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initStyle(StageStyle.UNDECORATED);

                root.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        xOffset = event.getSceneX();
                        yOffset = event.getSceneY();
                    }
                });

                root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        stage.setX(event.getScreenX() - xOffset);
                        stage.setY(event.getScreenY() - yOffset);
                    }
                });

                LobbyController lc = loader.getController();
                try {
                    lc.setUp(b, this);
                    stage.show();
                } catch (BereitsEingeloggtException b) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Fehler");
                    alert.setContentText("Benutzer bereits eingeloggt");
                    alert.showAndWait();
                }
                ((Node) (event.getSource())).getScene().getWindow().hide();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fehler");
                alert.setContentText("Fehler!");
                alert.showAndWait();
            }
        }catch (ConnectIOException s){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setContentText("Server nicht gefunden - Bitte überprüfen Sie ihre Serveradresse");
            alert.showAndWait();
        } catch (ConnectException c){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setContentText("Server nicht gefunden - Bitte überprüfen Sie ihre Serveradresse");
            alert.showAndWait();}
    }


    /**
     * Öffnet das Fenster, um die Serveradresse zu setzen
     *
     * @throws IOException wenn ein IO-Fehler auftritt
     */
    @FXML
    public void serveradresseSetzen() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ServerReg.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Server-Registrierung");
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image("/GUI/Bilder/cover.jpg"));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }
}
