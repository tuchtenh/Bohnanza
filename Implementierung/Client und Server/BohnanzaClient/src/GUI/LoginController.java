package GUI;

import LogIn.LogIn;

import Server.ServerAdresse;
import User.Benutzer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;


import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class LoginController extends Application {

    /*final String HOST = "localhost";
    final int PORT = 1099;*/

    final String HOST = ServerAdresse.getHOST();
    final int PORT = ServerAdresse.getPORT();


    private Benutzer b;


    @FXML
    public static void main(String[] args) {
        launch(args);
    }



    @FXML
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    @FXML
    public void setRegButton() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Registrierung.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Registrierung");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    TextField name;
    @FXML
    PasswordField passwort;

    @FXML
    public void setAnmeldeButton(ActionEvent event) throws RemoteException, NotBoundException, IOException {
        String benutzer = name.getText();
        String password = passwort.getText();

        Registry registry;
        registry = LocateRegistry.getRegistry(HOST, PORT);
        LogIn login = (LogIn) registry.lookup("LogIn");

        if (login.anmelden(benutzer, password)) {

            //TODO:Throw Exception, falls Benutzer nicht gefunden
            this.b=login.ladeBenutzer(benutzer);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Lobby.fxml"));

            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Registrierung");
            stage.setScene(new Scene(root));

            LobbyController lc = loader.getController();
            try {
                lc.setUp(b);
            }
            catch (Exception e){
                e.printStackTrace();
            }

            stage.show();
            ((Node)(event.getSource())).getScene().getWindow().hide();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setContentText("Fehler!");
            alert.showAndWait();
        }
    }

}
