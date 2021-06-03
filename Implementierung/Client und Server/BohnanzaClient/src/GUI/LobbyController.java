package GUI;


import Chat.*;
import Server.ServerAdresse;
import User.Benutzer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;


import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class LobbyController {

    /*final String HOST = "localhost";
    final int PORT = 1099;*/
    final String HOST = ServerAdresse.getHOST();
    final int PORT = ServerAdresse.getPORT();


    @FXML
    TextArea chatAusgabe;
    @FXML
    TextArea chatEingabe;

    ChatClientInterface client;

    ChatServerInterface chat;

    public void setUp(Benutzer b) throws Exception{
        client = new ChatClient("LobbyChat",this.chatAusgabe,b);

        Registry registry;
        registry = LocateRegistry.getRegistry(HOST, PORT);

        this.chat = (ChatServerInterface) registry.lookup("LobbyChat");

        chat.joinChat(client);

    }

    public void bestenliste(ActionEvent event) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Bestenliste.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Bestenliste");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void abmelden(ActionEvent event) throws Exception{

        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    public void senden() throws Exception{
        chat.receiveMessage(chatEingabe.getText(),client);
        //chatAusgabe.appendText("Nils" + chatEingabe.getText()+"\n");
        chatEingabe.clear();
    }

}
