package GUI;


import Chat.ChatClient;
import Chat.ChatClientInterface;
import Chat.ChatServerInterface;
import Observer.Refresher.Lobby.RefresherLobby;
import Observer.Refresher.Lobby.RefresherLobbyClient;
import Observer.Refresher.Lobby.RefresherLobbyServerInterface;
import Raumverwaltung.Lobby;
import Raumverwaltung.Spielvorraum;
import Server.ServerAdresse;
import Spieleverwaltung.Spieler;
import User.Benutzer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.awt.*;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Vector;


/**
 * LobbyController, der die GUI der Lobby steuert
 *
 * @author Nils Fitting, Lukas Bayer
 * @version Nils Fitting
 */
public class LobbyController {

    /*final String HOST = "localhost";
    final int PORT = 1099;*/
    final String HOST = ServerAdresse.getHOST();
    final int PORT = ServerAdresse.getPORT();
    public Benutzer aktiverBenutzer;
    @FXML
    TextArea chatAusgabe;
    @FXML
    TextField chatEingabe;
    @FXML
    TextField neuerRaumName;
    @FXML
    ListView<String> listeRaeume;
    @FXML
    ListView<String> listeSpieler;
    @FXML
    ListView<String> activePlayer;
    @FXML
    Text spielerImRaum;
    @FXML
    Button join;
    ChatClientInterface client;
    ChatServerInterface chat;
    FXMLLoader l;
    LoginController lc;
    Lobby lobby;
    RefresherLobbyServerInterface refresherServer;
    RefresherLobby refresherClient;

    @FXML
    ListView<String> anzahl;
    @FXML
    HBox hbox;
    Stage currentStage = new Stage();

    @FXML
    Button minimize;

    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    private int width = gd.getDisplayMode().getWidth();

    private double xOffset = 0;
    private double yOffset = 0;

    /**
     * setUp für die Lobby
     *
     * @param b  der Benutzer, der in die Lobby geleitet wird
     * @param lc der LobbyController, der den SetUp aufruft
     * @throws RemoteException   wenn bei Verbindung zum Server etwas schiefläuft
     * @throws NotBoundException wenn das Objekt nicht an die Registry gebunden ist
     */
    public void setUp(Benutzer b, LoginController lc) throws RemoteException, NotBoundException {
        Registry registry;
        registry = LocateRegistry.getRegistry(HOST, PORT);

        this.chat = (ChatServerInterface) registry.lookup("LobbyChat");
        this.lobby = (Lobby) registry.lookup("Lobby");
        this.lc = lc;
        this.aktiverBenutzer = b;

        refresherServer = (RefresherLobbyServerInterface) registry.lookup("LobbyRefresher");
        refresherClient = new RefresherLobbyClient(this);
        refresherServer.join(refresherClient);

        client = new ChatClient(this.chatAusgabe, b);
        chat.joinChat(client);
        refresh();
        refresherServer.refresh();

        minimize.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                currentStage.setIconified(true);
            }
        });
        join.setDisable(true);
        currentStage = (Stage) chatEingabe.getScene().getWindow();

        //set Handle für das Schließen des Fensters
        currentStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                try {
                    chat.verlasseChat(client);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                try {
                    refresherServer.leave(refresherClient);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                System.out.println("Game is closing");
                System.exit(0);
            }
        });
        scroll();
    }

    /**
     * Ermöglicht das gleichzeitige Scrollen zweier Listen
     */
    @FXML
    public void scroll() {
        Node n1 = listeRaeume.lookup(".scroll-bar");
        if (n1 instanceof ScrollBar) {
            final ScrollBar bar1 = (ScrollBar) n1;
            Node n2 = anzahl.lookup(".scroll-bar");
            if (n2 instanceof ScrollBar) {
                final ScrollBar bar2 = (ScrollBar) n2;
                bar1.valueProperty().bindBidirectional(bar2.valueProperty());
            }
        }
    }

    /**
     * Öffnet ein Fenster, das die Bestenliste anzeigt
     *
     * @param event das Drücken des Button
     * @throws IOException wenn ein IO-Fehler auftritt
     */
    public void bestenliste(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Bestenliste.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Bestenliste");
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image("/GUI/Bilder/cover.jpg"));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setX(currentStage.getX() + currentStage.getWidth() / 4);
        stage.setY(currentStage.getY() + currentStage.getHeight() / 4);
        stage.show();
    }

    /**
     * Öffnet ein Fenster, das die Spielregeln anzeigt
     *
     * @throws IOException wenn ein IO-Fehler auftritt
     */
    @FXML
    public void spielregeln() throws IOException {

        String gui = "";
        if (width < 1440) {
            gui = "Spielregeln_800.fxml";
        } else {
            gui = "Spielregeln.fxml";
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource(gui));
        Parent root = loader.load();
        Stage stage = new Stage();

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

        stage.setTitle("Spielregeln");
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image("/GUI/Bilder/cover.jpg"));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    /**
     * Ermöglicht das Abmelden eines Benutzers, schließt die Lobby
     *
     * @param event Button zum Abmelden wurde gedrückt
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    public void abmelden(ActionEvent event) throws RemoteException {
        chat.verlasseChat(client);
        refresherServer.leave(refresherClient);

        ((Node) (event.getSource())).getScene().getWindow().hide();
        System.exit(0);
    }

    /**
     * Sendet eine Chatnachricht
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    public void senden() throws RemoteException {
        chat.receiveMessage(chatEingabe.getText(), client);
        //chatAusgabe.appendText("Nils" + chatEingabe.getText()+"\n");
        chatEingabe.clear();
    }

    /**
     * Sendet eine Chatnachricht on Enter
     *
     * @throws IOException wenn ein IO-Fehler auftritt
     */
    @FXML
    public void onEnter() throws IOException {
        senden();
    }

    /**
     * Erstellt einen Spielvorraum
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    public void spielvorraumErstellen() throws RemoteException {
        String name = neuerRaumName.getText();
        if (name != null && !name.isEmpty()) {
            if (lobby.spielVorraumErstellen(name)) {
                refresherServer.refresh();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fehler!");
                alert.setContentText("Fehler! Name bereits vergeben!");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler!");
            alert.setContentText("Bitte Name eingeben");
            alert.showAndWait();
        }
        neuerRaumName.clear();
    }

    /**
     * Updatet die GUI
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    public void refresh() throws RemoteException {
        Platform.runLater(() -> {
            try {
                listeRaeume.setItems(FXCollections.observableList(lobby.getNamen()));
                activePlayer.setItems(FXCollections.observableList(chat.getNamen()));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }


    /**
     * Updatet die Anzahl der Spieler in einem Spielvorraum
     */
    public void refreshNumber() {
        Platform.runLater(() -> {
            try {
                Vector<String> anzahlS = new Vector<>();
                String number;
                for (Spielvorraum raum : lobby.getRaeume()) {
                    number = Integer.toString(raum.getAkteure().size());
                    anzahlS.add(number + "/5");
                }
                anzahl.setItems(FXCollections.observableList(anzahlS));
            } catch (RemoteException r) {
                r.printStackTrace();
            }
        });
    }

    /**
     * Zeigt alle Spielvorräume an
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    public void listeRaeumeOnClick() throws RemoteException {
        if (listeRaeume.getItems().size() != 0) {
            try {
                join.setDisable(false);
                String name = listeRaeume.getSelectionModel().getSelectedItem();
                Spielvorraum sv = lobby.getRaum(name);
                listeSpieler.setItems(FXCollections.observableList(new ArrayList<String>()));
                listeSpieler.setItems(FXCollections.observableList(sv.getAkteure()));
            } catch (NullPointerException n) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fehler");
                alert.setContentText("Bitte einen gültigen Spielraum aus der Liste auswählen");
                alert.showAndWait();
            }
        }
    }

    /**
     * Löscht einen ausgewählten Spielraum, wenn keine mensch. Spieler vorhanden
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    public void loescheRaum() throws RemoteException {
        if (listeRaeume.getItems().size() != 0) {
            try {
                String name = listeRaeume.getSelectionModel().getSelectedItem();
                Spielvorraum sv = lobby.getRaum(name);
                join.setDisable(true);
                if (sv.getSpieler().size() > 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Fehler");
                    alert.setContentText("Im Spielvorraum befinden sich noch Spieler - Löschen nicht möglich");
                    alert.showAndWait();
                } else {
                    lobby.loescheRaum(name);
                    refresherServer.refresh();
                    listeSpieler.setItems(null);
                }
            } catch (NullPointerException n) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fehler");
                alert.setContentText("Bitte einen gültigen Spielraum aus der Liste auswählen");
                alert.showAndWait();
            }
        }
    }

    /**
     * Lässt einen Benutzer in eine Spielvorraum beitreten
     *
     * @throws IOException wenn ein IO-Fehler auftritt
     */
    public void joinRaum() throws IOException {

        String name = listeRaeume.getSelectionModel().getSelectedItem();
        Spielvorraum sv = lobby.getRaum(name);

        if (sv.getSpieler().size() < 5) {
            Vector<String> namen = new Vector<>();
            for (Spieler b : sv.getSpieler()) {
                namen.add(b.getName());
            }
            if (namen.contains(aktiverBenutzer.getName())) {
                sv.getRefresherServer().leave(aktiverBenutzer.getName());
                sv.getChat().verlasseChat(aktiverBenutzer.getName());
                sv.removeSpieler(aktiverBenutzer.getName());

               /*Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setTitle("Fehler!");
               alert.setContentText("Sie können den gleichen Spielvorraum nur einmal betreten");
               alert.showAndWait();*/
            }

            /*} else {*/
            join.setDisable(true);
            sv.addSpieler(aktiverBenutzer);

            String gui = "";
            if (width < 1440) {
                gui = "Spielvorraum_800.fxml";
            } else {
                gui = "Spielvorraum.fxml";
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource(gui));

            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Spielvorraum");
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image("/GUI/Bilder/cover.jpg"));
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
            SpielvorraumController sc = loader.getController();
            try {
                sc.setUp(sv, aktiverBenutzer, this, stage);
                listeSpieler.setItems(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            stage.show();
            refresherServer.refresh();
            //}
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Achtung!");
            alert.setContentText("Raum voll!");
            alert.showAndWait();
        }
    }

    /**
     * Löscht einen Spielraum (wird verwendet, wenn Spiel gestartet wird
     *
     * @param name Name des Spielvorraums
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    public void loescheSpielvorraum(String name) throws RemoteException {
        lobby.loescheRaum(name);
        refresherServer.refresh();
    }

    /**
     * Startet das Fenster zum Löschen eines Benutzeraccounts
     *
     * @throws IOException wenn ein IO-Fehler auftritt
     */
    public void loescheSpieler() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Loeschen.fxml"));

        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Accountlöschung");
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image("/GUI/Bilder/cover.jpg"));
        stage.initOwner(currentStage);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);

        stage.setX(currentStage.getX() + currentStage.getWidth() / 3);
        stage.setY(currentStage.getY() + currentStage.getHeight() / 3);

        LoeschenController lo = loader.getController();
        try {
            lo.setUp(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        stage.show();
    }
}