
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
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

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

       @FXML
    public void register(ActionEvent event) throws RemoteException, IOException, NotBoundException{
        String name = benutzer.getText();
        String passwort2 = passwort.getText();
        String passwort2Wdh = passwortWdh.getText();


        Registry registry;
        registry = LocateRegistry.getRegistry(HOST, PORT);
        LogIn login = (LogIn) registry.lookup("LogIn");

        if(login.registrieren(name, passwort2, passwort2Wdh)){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Registriert");
            alert.setContentText("Du hast dich erflgreich registriert!");
            alert.showAndWait();
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }

    }

}
