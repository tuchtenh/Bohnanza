package GUI;

import Chat.ChatClient;
import Chat.ChatClientInterface;
import Chat.ChatServerInterface;
import Exception.ZuVieleSpielerException;
import Observer.Joiner.Joiner;
import Observer.Joiner.JoinerClient;
import Observer.Joiner.JoinerServerInterface;
import Observer.Refresher.Spielvorraum.RefresherSpielvorraum;
import Observer.Refresher.Spielvorraum.RefresherSpielvorraumClient;
import Observer.Refresher.Spielvorraum.RefresherSpielvorraumServerInterface;
import Raumverwaltung.Spielraum;
import Raumverwaltung.Spielvorraum;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

import java.awt.*;
import java.io.IOException;
import java.rmi.RemoteException;

/**
 * SpielvorraumController, um den Spielvorraum zu verwalten
 *
 * @author Lukas Bayer, Nils Fitting
 * @version 1.0
 */
public class SpielvorraumController {
    @FXML
    TextField chatEingabe;
    @FXML
    TextArea chatAusgabe;
    @FXML
    Text nameRaum;
    @FXML
    ListView<String> listeSpieler;
    @FXML
    ListView<String> listeBots;
    @FXML
    ChoiceBox blist;

    Spielvorraum spielvorraum;
    Benutzer aktiverBenutzer;
    ChatClientInterface client;
    ChatServerInterface chat;
    LobbyController lobbyGUI;
    Joiner joinerClient;
    JoinerServerInterface joinerServer;
    RefresherSpielvorraum refresherClient;
    RefresherSpielvorraumServerInterface refresherServer;
    Window currentStage;
    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    private double xOffset = 0;
    private double yOffset = 0;
    private int width = gd.getDisplayMode().getWidth();

    /**
     * setUp für den Spielvorraum
     *
     * @param spielvorraum der Spielraum, der dargestellt wird
     * @param b            der Benutzer, der den Spielraum erstellt hat
     * @param lc           der LobbyController, der den SetUp aufruft
     * @param stage        die Stage, die zum LobbyController gehört
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    public void setUp(Spielvorraum spielvorraum, Benutzer b, LobbyController lc, Stage stage) throws RemoteException {

        //Get Window vom LobbyController nach dem Aufruf
        currentStage = stage;

        this.spielvorraum = spielvorraum;
        this.nameRaum.setText(spielvorraum.getName());
        this.aktiverBenutzer = b;
        this.lobbyGUI = lc;

        this.client = new ChatClient(this.chatAusgabe, aktiverBenutzer);
        this.chat = spielvorraum.getChat();
        chat.joinChat(client);

        joinerServer = spielvorraum.getJoinerServer();
        joinerClient = new JoinerClient(this);
        joinerServer.join(joinerClient);

        refresherServer = spielvorraum.getRefresherServer();
        refresherClient = new RefresherSpielvorraumClient(this, aktiverBenutzer.getName());
        refresherServer.join(refresherClient);

        blist.getItems().add("Bot Leicht");
        blist.getItems().add("Bot Schwer");

        refresh();
        refresherServer.refreshSpieler();

        //set Handle für das Schließen des Fensters
        currentStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                try {
                    spielvorraum.removeSpieler(aktiverBenutzer.getName());
                    lobbyGUI.refreshNumber();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                //Verlasse den RefreshServer, da Spielvorraum nicht mehr sinnvoll vorhanden
                try {
                    refresherServer.leave(refresherClient);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                //Verlasse den Joiner, da Spielvorraum geschlossen wurde
                try {
                    joinerServer.leave(joinerClient);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Updatet die GUI
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    public void refresh() throws RemoteException {
        listeBots.setItems(FXCollections.observableList(spielvorraum.getBotNamen()));
        listeSpieler.setItems(FXCollections.observableList(spielvorraum.getSpielerNamen()));
    }

    /**
     * Updatet die GUI - Liste Bots
     */
    public void refreshBots() {
        Platform.runLater(() -> {
            try {
                lobbyGUI.refreshNumber();
                listeBots.setItems(FXCollections.observableList(spielvorraum.getBotNamen()));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Updatet die GUI - Liste Spieler
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    public void refreshSpieler() throws RemoteException {
        Platform.runLater(() -> {
            try {
                listeSpieler.setItems(FXCollections.observableList(spielvorraum.getSpielerNamen()));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Ermöglicht es einem Spieler, einen Spielvorraum wieder zu verlassen
     *
     * @param event der Button zum Verlassen wurde gedrückt
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @FXML
    public void leave(ActionEvent event) throws RemoteException {
        if (spielvorraum.removeSpieler(aktiverBenutzer.getName())) {
            refresherServer.refreshSpieler();
            refresherServer.leave(refresherClient);
            lobbyGUI.refresherServer.refresh();
            ((Node) (event.getSource())).getScene().getWindow().hide();

            //Verlasse den RefreshServer, da Spielvorraum nicht mehr sinnvoll vorhanden
            try {
                refresherServer.leave(refresherClient);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            //Verlasse den Joiner, da Spielvorraum geschlossen wurde
            try {
                joinerServer.leave(joinerClient);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        //TODO: Was, wenn das verlassen fehlschlägt?
    }

    /**
     * Sendet eine Chatnachricht
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    public void senden() throws RemoteException {
        chat.receiveMessage(chatEingabe.getText(), client);
        chatEingabe.clear();
    }

    /**
     * Fügt einen Bot hinzu
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    public void addBot() throws RemoteException {
        String choice = (String) blist.getValue();
        if (choice == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setContentText("Bitte eine Botschwierigkeitsstufe auswählen");
            alert.showAndWait();
        } else {
            if (choice.equals("Bot Leicht")) {
                try {
                    if (spielvorraum.addBot(0)) {
                        refresherServer.refreshBots();
                    }
                } catch (ZuVieleSpielerException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Achtung!");
                    alert.setContentText("Raum voll!");
                    alert.showAndWait();
                }
            } else {
                try {
                    if (spielvorraum.addBot(1)) {
                        refresherServer.refreshBots();
                    }
                } catch (ZuVieleSpielerException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Achtung!");
                    alert.setContentText("Raum voll!");
                    alert.showAndWait();
                }
            }
        }
    }

    /**
     * Entfernt einen ausgewählten Bot
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    public void removeBot() throws RemoteException {
        String name = listeBots.getSelectionModel().getSelectedItem();
        if (name != null) {
            spielvorraum.removeBot(name);
        }
        refresherServer.refreshBots();
    }

    /**
     * Startet den Spielraum, wenn genügend Spieler vorhanden
     *
     * @throws IOException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @FXML
    public void starteSpiel() throws IOException {
        if (spielvorraum.getAkteure().size() >= 3) {
            Spielraum s = this.spielvorraum.starteSpiel();
            //spielstart(s);
            joinerServer.starteSpiel(s);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Achtung!");
            alert.setContentText("Noch nicht genügend Spieler vorhanden!");
            alert.showAndWait();
        }
    }

    /**
     * Minimiert den Spielvorraum
     */
    @FXML
    public void minimize() {
        Stage stage = (Stage) nameRaum.getScene().getWindow();
        stage.setIconified(true);
    }

    /**
     * Eröffnet die graphische Oberfläche beim Spielen
     *
     * @param spiel Spielraum, der auf Serverseite initailisert wurde
     */
    public void spielstart(Spielraum spiel) {
        Platform.runLater(() -> {
            String gui = "";
            if (width > 1390) {
                gui = "Spielraum_new.fxml";
            } else if (width > 800) {
                gui = "Spielraum_new_1300.fxml";
            } else {
                gui = "Spielraum_new_800.fxml";
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource(gui));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = new Stage();
            stage.setTitle("Spiel");
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

            SpielraumController sp = loader.getController();
            try {
                sp.setUp(spiel, aktiverBenutzer);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                lobbyGUI.loescheSpielvorraum(spielvorraum.getName());
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            stage.show();
            currentStage.hide();

            //Verlasse den RefreshServer, da Spielvorraum nicht mehr sinnvoll vorhanden
            try {
                refresherServer.leave(refresherClient);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            //Verlasse den Joiner, da Spielvorraum geschlossen wurde
            try {
                joinerServer.leave(joinerClient);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    public void closeStage() {
        Platform.runLater(() -> {
            currentStage.hide();
        });
    }
}
