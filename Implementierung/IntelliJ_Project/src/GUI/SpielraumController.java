package GUI;

import Chat.ChatClient;
import Chat.ChatClientInterface;
import Chat.ChatServerInterface;
import Exception.AbbauException;
import Exception.BohnenSchutzRegelException;
import Exception.NichtAlleKartenAngebautException;
import Exception.NixAngebautException;
import Handel.Haendler;
import Handel.HandelClient;
import Handel.HandelServerInterface;
import Observer.Finisher.Finisher;
import Observer.Finisher.FinisherClient;
import Observer.Finisher.FinisherServerInterface;
import Observer.Refresher.Spielraum.RefresherSpielraum;
import Observer.Refresher.Spielraum.RefresherSpielraumClient;
import Observer.Refresher.Spielraum.RefresherSpielraumServerInterface;
import Raumverwaltung.Spielraum;
import Spieleverwaltung.Akteur;
import Spieleverwaltung.Spielemanager;
import Spieleverwaltung.Spieler;
import Spieleverwaltung.Spielobjekte.Bohnenfeld;
import Spieleverwaltung.Spielobjekte.Karte;
import User.Benutzer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Vector;
import java.util.concurrent.ExecutionException;


/**
 * SpielraumController, der die graphische Verwaltung des Spiels steuert
 *
 * @author Lukas Bayer, Nils Fitting
 * @version 1.0
 */
public class SpielraumController {
    @FXML
    ListView<ImageView> cardContainer;
    @FXML
    Text anzahlDeckkarten;
    @FXML
    Text anzahlAblagekarten;
    @FXML
    ImageView feldSpieler;
    @FXML
    ImageView feldSpieler1;
    @FXML
    ImageView feldSpieler2;
    @FXML
    ImageView feldSpieler3;
    @FXML
    ImageView feldSpieler4;
    @FXML
    Text spielerTaler;
    @FXML
    Text spielerTaler1;
    @FXML
    Text spielerTaler2;
    @FXML
    Text spielerTaler3;
    @FXML
    Text spielerTaler4;
    @FXML
    Text name1;
    @FXML
    Text name2;
    @FXML
    Text name3;
    @FXML
    Text name4;
    @FXML
    ImageView stapelKarte1;
    @FXML
    ImageView stapelKarte2;
    @FXML
    ImageView spielerFeld1;
    @FXML
    ImageView spielerFeld2;
    @FXML
    ImageView spielerFeld3;
    @FXML
    ImageView spieler1Feld1;
    @FXML
    ImageView spieler1Feld2;
    @FXML
    ImageView spieler1Feld3;
    @FXML
    ImageView spieler2Feld1;
    @FXML
    ImageView spieler2Feld2;
    @FXML
    ImageView spieler2Feld3;
    @FXML
    ImageView spieler3Feld1;
    @FXML
    ImageView spieler3Feld2;
    @FXML
    ImageView spieler3Feld3;
    @FXML
    ImageView spieler4Feld1;
    @FXML
    ImageView spieler4Feld2;
    @FXML
    ImageView spieler4Feld3;
    @FXML
    Text spielerFeldZahl1;
    @FXML
    Text spielerFeldZahl2;
    @FXML
    Text spielerFeldZahl3;
    @FXML
    Text spieler1FeldZahl1;
    @FXML
    Text spieler1FeldZahl2;
    @FXML
    Text spieler1FeldZahl3;
    @FXML
    Text spieler2FeldZahl1;
    @FXML
    Text spieler2FeldZahl2;
    @FXML
    Text spieler2FeldZahl3;
    @FXML
    Text spieler3FeldZahl1;
    @FXML
    Text spieler3FeldZahl2;
    @FXML
    Text spieler3FeldZahl3;
    @FXML
    Text spieler4FeldZahl1;
    @FXML
    Text spieler4FeldZahl2;
    @FXML
    Text spieler4FeldZahl3;
    @FXML
    Button handeln1;
    @FXML
    Button handeln2;
    @FXML
    Button handeln3;
    @FXML
    Button handeln4;
    @FXML
    Button nextPhase;
    @FXML
    VBox karten;
    @FXML
    Text spielerAmZug;
    @FXML
    TextField chatEingabe;
    @FXML
    TextArea chatAusgabe;
    @FXML
    TextArea systemNachrichten;
    @FXML
    ListView<ImageView> anbauKarten;

    SpielraumController sp = this;
    Vector<Karte> handkarten = new Vector<>();
    Spielraum spielraum;
    Spielemanager spielemanager;
    ChatClientInterface client;
    ChatServerInterface chat;
    HandelServerInterface handelServer;
    Haendler handelClient;
    RefresherSpielraum refresherClient;
    RefresherSpielraumServerInterface refresherServer;
    Finisher finisherClient;
    FinisherServerInterface finisherServer;
    Vector<Karte> anzubauen = new Vector<>();
    Benutzer aktiverBenutzer;
    Akteur aktiverSpieler;  //nicht der Spieler, der am Zug ist, sondern der Spieler, welcher zu dem Benutzer gehört
    Vector<Akteur> spieler;
    Vector<Akteur> andereSpieler;
    Stage currentStage;

    int aktuellePhase;
    int spielerAnzahl;
    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    double kartenhoehe;
    private double xOffset = 0;
    private double yOffset = 0;
    private int width = gd.getDisplayMode().getWidth();

    /**
     * Initialisert den Controller mit vorgegebenen Werten
     *
     * @param spielraum Spielraum, der zum aktuellen Spiel gehört
     * @param b         Benutzer der GUI
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn die URL falsch ist
     * @throws InterruptedException  wenn die Ausführung unterbrochen wird
     */
    @FXML
    public void setUp(Spielraum spielraum, Benutzer b) throws RemoteException, MalformedURLException, InterruptedException {

        this.kartenhoehe = cardContainer.getPrefHeight();

        nextPhase.setVisible(false);

        this.spielraum = spielraum;
        this.spielemanager = this.spielraum.getManager();
        this.aktiverBenutzer = b;

        this.finisherClient = new FinisherClient(this);
        this.finisherServer = spielemanager.getFinisherServer();
        finisherServer.join(finisherClient);


        this.chat = this.spielraum.getChat();
        this.client = new ChatClient(this.chatAusgabe, aktiverBenutzer);
        chat.joinChat(client);

        this.refresherServer = spielraum.getRefresherServer();
        this.refresherClient = new RefresherSpielraumClient(this);
        refresherServer.join(refresherClient);

        spielerAnzahl = spielraum.getSpieler().size();

        //muss vor der Zuweisung der Spieler erfolgen, da Reihenfolge geshuffelt
        /*if (!spielemanager.isReady()) {

            //Versuch einen paralleln Thread zu starten TODO: muss auf das SetUp warten
            Thread starter = new Thread(()->{
                try {
                    spielemanager.spielSetUp();
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            });
            starter.wait();

        }*/

        while (!spielemanager.isReady()) {
            //Warte auf Antwort vom Spielemanager
            systemNachrichten.appendText("Warte auf Antwort vom Spielemanager... \n");
            Thread.sleep(100);
        }

        spieler = spielemanager.getSpieler();
        andereSpieler = new Vector<>();

        //suche Spieler
        for (Akteur a : spieler) {
            if (a.isUser() && ((Spieler) a).getUser().equals(aktiverBenutzer)) {
                aktiverSpieler = a;
                System.out.println("Spieler gefunden!");
            } else {
                andereSpieler.add(a);
            }
        }
        this.handkarten = aktiverSpieler.getHandkarten();

        //setze Namen
        name1.setText(andereSpieler.get(0).getName());
        name2.setText(andereSpieler.get(1).getName());
        if (spielerAnzahl >= 4) {
            name3.setText(andereSpieler.get(2).getName());
            name4.setText(null);
        } else {
            name3.setText(null);
            name4.setText(null);
        }
        if (spielerAnzahl == 5) {
            name4.setText(andereSpieler.get(3).getName());
        }

        //Setze Handelsserver auf
        this.handelServer = spielemanager.getHandelServer();
        //TODO: Änderung von HandelClient; hat geklappt?
        this.handelClient = new HandelClient((Spieler) aktiverSpieler);
        this.handelClient.setSC(this);
        handelServer.join(handelClient);

        refreshHandkarten();
        refreshBohnenfelder();
        refreshTaler();
        refreshSpielerAmZug();

        if (spielerAnzahl == 3) {

            feldSpieler3.setImage(null);
            feldSpieler4.setImage(null);

            spieler3FeldZahl1.setText(null);
            spieler3FeldZahl2.setText(null);
            spieler3FeldZahl3.setText(null);

            spieler4FeldZahl1.setText(null);
            spieler4FeldZahl2.setText(null);
            spieler4FeldZahl3.setText(null);

            spielerTaler3.setText(null);
            spielerTaler4.setText(null);

            handeln3.setVisible(false);
            handeln4.setVisible(false);
        } else if (spielerAnzahl == 4) {
            /*File file = new File("./src/GUI/Bilder/bohnenfeld2.PNG");
            String s = file.toURI().toURL().toString();*/

            /*Image img = new Image(s);*/
            Image img = new Image("/GUI/Bilder/bohnenfeld2.PNG");
            feldSpieler1.setPreserveRatio(false);
            feldSpieler1.setFitHeight(130);
            feldSpieler1.setFitWidth(120);
            feldSpieler2.setPreserveRatio(false);
            feldSpieler2.setFitHeight(130);
            feldSpieler2.setFitWidth(120);
            feldSpieler3.setPreserveRatio(false);
            feldSpieler3.setFitHeight(130);
            feldSpieler3.setFitWidth(120);

            feldSpieler.setPreserveRatio(false);
            feldSpieler.setFitHeight(130);
            feldSpieler.setFitWidth(120);

            feldSpieler.setImage(img);
            feldSpieler1.setImage(img);
            feldSpieler2.setImage(img);
            feldSpieler3.setImage(img);

            spielerFeldZahl3.setText(null);
            spieler1FeldZahl3.setText(null);
            spieler2FeldZahl3.setText(null);
            spieler3FeldZahl3.setText(null);

            feldSpieler4.setImage(null);
            spieler4FeldZahl1.setText(null);
            spieler4FeldZahl2.setText(null);
            spieler4FeldZahl3.setText(null);
            spielerTaler4.setText(null);

            handeln4.setVisible(false);
        } else {
            /*File file = new File("./src/GUI/Bilder/bohnenfeld2.PNG");
            String s = file.toURI().toURL().toString();

            Image img = new Image(s);
*/
            Image img = new Image("/GUI/Bilder/bohnenfeld2.PNG");
            feldSpieler1.setPreserveRatio(false);
            feldSpieler1.setFitHeight(130);
            feldSpieler1.setFitWidth(120);
            feldSpieler2.setPreserveRatio(false);
            feldSpieler2.setFitHeight(130);
            feldSpieler2.setFitWidth(120);
            feldSpieler3.setPreserveRatio(false);
            feldSpieler3.setFitHeight(130);
            feldSpieler3.setFitWidth(120);
            feldSpieler4.setPreserveRatio(false);
            feldSpieler4.setFitHeight(130);
            feldSpieler4.setFitWidth(120);

            feldSpieler.setPreserveRatio(false);
            feldSpieler.setFitHeight(130);
            feldSpieler.setFitWidth(120);

            feldSpieler.setImage(img);
            feldSpieler1.setImage(img);
            feldSpieler2.setImage(img);
            feldSpieler3.setImage(img);
            feldSpieler4.setImage(img);

            spielerFeldZahl3.setText(null);
            spieler1FeldZahl3.setText(null);
            spieler2FeldZahl3.setText(null);
            spieler3FeldZahl3.setText(null);
            spieler4FeldZahl3.setText(null);
        }
        //Deaktiviere alle Buttons, die nicht notwendig sind
        sichtbarkeitHandeln(false);
        nextPhase.setVisible(false);

        //Status update
        systemNachrichten.appendText("Spiel gestartet!\n");
        systemNachrichten.appendText("Reihenfolge ist:\n");
        for (Akteur a : spielemanager.getSpieler()) {
            systemNachrichten.appendText(a.getName() + "\n");
        }
        systemNachrichten.appendText("\n");

        refreshPhase();
        this.currentStage = (Stage) chatEingabe.getScene().getWindow();
        //setCloseEvent
        currentStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                try {
                    finisherServer.spielAbbrechen(aktiverSpieler);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        //Controller ist ready
        spielemanager.requestStart();

    }

    /**
     * Öffnet das Handelsfenster beim Spieler selbst und beim Gegenspieler
     *
     * @param partner Handelspartner
     */
    @FXML
    public void starteHandel(Haendler partner) {
        Platform.runLater(() -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Handelsfenster.fxml"));

            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = new Stage();
            stage.setTitle("Handelfenster");
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
            //Disable the Rest
            cardContainer.setDisable(true);
            disableHandeln(true);
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    cardContainer.setDisable(false);
                    disableHandeln(false);
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                partner.schließeFenster();
                            } catch (RemoteException r) {
                                r.printStackTrace();
                            } catch (MalformedURLException m) {
                                m.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException i) {
                                i.printStackTrace();
                            }
                        }
                    });
                }
            });
            HandelController hc = loader.getController();
            try {
                handelClient.setAngebot(new Vector<>());
                handelClient.setHC(hc);
                hc.setUp(this, handelClient, partner, stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
            stage.show();
        });
    }

    /**
     * Schließt das Handelsfenster des Gegenspielers, wenn man es selbst schließt
     *
     * @param partner Handelspartner
     */
    public void handelCloseRequest(Haendler partner) {
        cardContainer.setDisable(false);
        disableHandeln(false);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    partner.schließeFenster();
                } catch (RemoteException r) {
                    r.printStackTrace();
                } catch (MalformedURLException m) {
                    m.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException i) {
                    i.printStackTrace();
                }
            }
        });

    }

    /**
     * Startet den Handel bei Spieler 1
     *
     * @throws IOException wenn ein IO-Fehler auftritt
     */
    @FXML
    public void handelnSpieler1() throws IOException {
        if (andereSpieler.size() >= 1) {
            handelServer.starteHandel(aktiverSpieler, andereSpieler.get(0));

        }
    }

    /**
     * Startet den Handel bei Spieler 2
     *
     * @throws IOException wenn ein IO-Fehler auftritt
     */
    @FXML
    public void handelnSpieler2() throws IOException {
        if (andereSpieler.size() >= 2) {
            handelServer.starteHandel(aktiverSpieler, andereSpieler.get(1));

        }
    }

    /**
     * Startet den Handel bei Spieler 3
     *
     * @throws IOException wenn ein IO-Fehler auftritt
     */
    @FXML
    public void handelnSpieler3() throws IOException {
        if (andereSpieler.size() >= 3) {
            handelServer.starteHandel(aktiverSpieler, andereSpieler.get(2));

        }
    }

    /**
     * Startet den Handel bei Spieler 4
     *
     * @throws IOException wenn ein IO-Fehler auftritt
     */
    @FXML
    public void handelnSpieler4() throws IOException {
        if (andereSpieler.size() >= 4) {
            handelServer.starteHandel(aktiverSpieler, andereSpieler.get(3));

        }
    }

    /**
     * Öffnet das Fenster zum Anbauen einer Handkarte
     *
     * @throws IOException wenn ein IO-Fehler auftritt
     */
    @FXML
    public void anbauenFenster() throws IOException {
        if (aktuellePhase == 1 && aktiverSpieler.getHatAngebaut() < 2 && aktiverSpieler.getName().equals(spielemanager.getActPlayer().getName())) {
            try {
                Image imge = getImg(handkarten.get(0).getImgURL());
                cardContainer.setDisable(true);
                //Object ob = event.getSource();
                //Image imge = ((ImageView) ob).getImage();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("Karte.fxml"));
                Parent root = loader.load();
                KarteController k = loader.getController();
                k.setUp(imge, sp);
                Stage stage = new Stage();
                stage.setTitle("KarteImpl");
                stage.setScene(new Scene(root));

                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    public void handle(WindowEvent we) {
                        cardContainer.setDisable(false);
                    }
                });
                stage.show();
            } catch (ArrayIndexOutOfBoundsException a) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fehler");
                alert.setContentText("Es gibt nichts anzubauen!");
                alert.showAndWait();
            }
        }
    }

    /**
     * Ermöglicht es einem Spieler mit einem Mausklick ein Bohnenfeld zu ernten
     *
     * @param event MouseEvent, von Klick auf Feld erzeugt
     * @throws IOException wenn ein IO-Fehler auftritt
     */
    @FXML
    public void ernten(MouseEvent event) throws IOException {
        Object ob = event.getSource();
        try {
            if (ob == spielerFeld1) {
                /*Vector<Karte> karten = aktiverSpieler.getFelder().get(0).ernten();

                int taler = karten.get(0).getTalerwert(karten.size());
                aktiverSpieler.addTaler(taler);*/
                spielemanager.ernten(aktiverSpieler, 0);

                refresherServer.refreshBohnenfelder();
                refresherServer.refreshTaler();
            } else if (ob == spielerFeld2) {
                /*Vector<Karte> karten = aktiverSpieler.getFelder().get(1).ernten();
                int taler = karten.get(0).getTalerwert(karten.size());
                aktiverSpieler.addTaler(taler);*/

                spielemanager.ernten(aktiverSpieler, 1);

                refresherServer.refreshBohnenfelder();
                refresherServer.refreshTaler();
            } else {
                /*Vector<Karte> karten = aktiverSpieler.getFelder().get(2).ernten();
                int taler = karten.get(0).getTalerwert(karten.size());
                aktiverSpieler.addTaler(taler);*/

                spielemanager.ernten(aktiverSpieler, 2);

                refresherServer.refreshBohnenfelder();
                refresherServer.refreshTaler();
            }
        } catch (ArrayIndexOutOfBoundsException a) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setContentText("Es gibt nichts abzubauen!");
            alert.showAndWait();
        } catch (BohnenSchutzRegelException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setContentText("Bohnenschutzregel! Du kannst gerade kein Feld mit nur einer Karte abbauen!");
            alert.showAndWait();
        } catch (AbbauException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setContentText("Das Feld ist leer!");
            alert.showAndWait();
        }
    }

    /**
     * Ermöglicht Anbau der Gehandelten- bzw Stapelkarten
     *
     * @throws IOException wenn ein IO-Fehler auftritt
     */
    @FXML
    public void anbauenAfterHandel() throws IOException {
        if (aktuellePhase == 3) {
            try {
                int index = anbauKarten.getSelectionModel().getSelectedIndex();
                Image img = getImg(anzubauen.get(index).getImgURL());

                anbauKarten.setDisable(true);
                //Object ob = event.getSource();
                //Image imge = ((ImageView) ob).getImage();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("Karte.fxml"));
                Parent root = loader.load();
                KarteController k = loader.getController();
                k.setUp(img, this, index);
                Stage stage = new Stage();
                stage.setTitle("KarteImpl");
                stage.setScene(new Scene(root));
                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    public void handle(WindowEvent we) {
                        anbauKarten.setDisable(false);
                    }
                });
                stage.show();

            } catch (ArrayIndexOutOfBoundsException a) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fehler");
                alert.setContentText("Es gibt nichts anzubauen!");
                alert.showAndWait();
            }
        }
    }


    /**
     * Interaktion zwischen Spielmanager und GUI (Aktualisierung) in P1
     *
     * @param index Index des Kartenabaus
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn die URL falsch ist
     */
    @FXML
    public void anbauenPhase1(int index) throws RemoteException, MalformedURLException {
        spielemanager.anbauenPhase1(aktiverSpieler, index);
        refreshHandkarten();
        refresherServer.refreshBohnenfelder();
        cardContainer.setDisable(false);
    }

    /**
     * Interaktion zwischen Spielmanager und GUI (Aktualisierung) in P3
     *
     * @param indexFeld  Index des Bohnenfeldes
     * @param indexKarte Index der anzubauenden Karte
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn die URL falsch ist
     */
    @FXML
    public void anbauenPhase3(int indexFeld, int indexKarte) throws MalformedURLException, RemoteException {
        spielemanager.anbauenPhase3(aktiverSpieler, indexFeld, indexKarte);
        refreshHandkarten();
        refreshGehandelteKarten();
        refresherServer.refreshBohnenfelder();
        anbauKarten.setDisable(false);
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
     * Aktualisiert die Handkarten
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn die URL falsch ist
     */
    public void refreshHandkarten() throws RemoteException, MalformedURLException {
        //refresh Handkarten

        handkarten = aktiverSpieler.getHandkarten();
        Vector<ImageView> handkartenBilder = new Vector<ImageView>();
        for (Karte k : handkarten) {
            Image i = getImg(k.getImgURL());
            ImageView img = new ImageView(i);

            /*if(width<1024) {
                img.setFitHeight(87);
                img.setFitWidth(50);
            }*/

            img.setPreserveRatio(true);
            img.setFitHeight(kartenhoehe);

            handkartenBilder.add(img);
/*          stapelKarte1.setImage(i);
            stapelKarte2.setImage(i);*/
        }
        cardContainer.setItems(FXCollections.observableList(handkartenBilder));
    }

    /**
     * Aktualisiert die Talerwerte
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    public void refreshTaler() throws RemoteException {
        spielerTaler.setText(String.valueOf(aktiverSpieler.getTaler()));
        spielerTaler1.setText(String.valueOf(andereSpieler.get(0).getTaler()));
        spielerTaler2.setText(String.valueOf(andereSpieler.get(1).getTaler()));
        if (spielerAnzahl >= 4) {
            spielerTaler3.setText(String.valueOf(andereSpieler.get(2).getTaler()));
        }
        if (spielerAnzahl == 5) {
            spielerTaler4.setText(String.valueOf(andereSpieler.get(3).getTaler()));
        }
    }

    /**
     * Aktualisiert die Bohnenfelder
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn die URL falsch ist
     */
    public void refreshBohnenfelder() throws RemoteException, MalformedURLException {
        //aktiver Spieler
        Vector<Bohnenfeld> spielerFelder = aktiverSpieler.getFelder();
        if (spielerFelder.get(0).getAnzahlKarten() > 0) {
            spielerFeld1.setImage((getImg(spielerFelder.get(0).getImgURL())));
            spielerFeldZahl1.setText(String.valueOf(spielerFelder.get(0).getAnzahlKarten()));
        } else {
            spielerFeld1.setImage(null);
            spielerFeldZahl1.setText("0");

        }
        if (spielerFelder.get(1).getAnzahlKarten() > 0) {
            spielerFeld2.setImage((getImg(spielerFelder.get(1).getImgURL())));
            spielerFeldZahl2.setText(String.valueOf(spielerFelder.get(1).getAnzahlKarten()));
        } else {
            spielerFeld2.setImage(null);
            spielerFeldZahl2.setText("0");
        }
        if (spielerAnzahl < 4) {
            if (spielerFelder.size() > 2 && spielerFelder.get(2).getAnzahlKarten() > 0) {
                spielerFeld3.setImage((getImg(spielerFelder.get(2).getImgURL())));
                spielerFeldZahl3.setText(String.valueOf(spielerFelder.get(2).getAnzahlKarten()));
            } else {
                spielerFeld3.setImage(null);
                spielerFeldZahl3.setText("0");
            }
        } else {
            spielerFeld3.setImage(null);
            spielerFeldZahl3.setText(null);
        }

        //Gegenspieler1
        Vector<Bohnenfeld> spieler1Felder = andereSpieler.get(0).getFelder();
        if (spieler1Felder.get(0).getAnzahlKarten() > 0) {
            spieler1Feld1.setImage((getImg(spieler1Felder.get(0).getImgURL())));
            spieler1FeldZahl1.setText(String.valueOf(spieler1Felder.get(0).getAnzahlKarten()));
        } else {
            spieler1Feld1.setImage(null);
            spieler1FeldZahl1.setText("0");
        }
        if (spieler1Felder.get(1).getAnzahlKarten() > 0) {
            spieler1Feld2.setImage((getImg(spieler1Felder.get(1).getImgURL())));
            spieler1FeldZahl2.setText(String.valueOf(spieler1Felder.get(1).getAnzahlKarten()));
        } else {
            spieler1Feld2.setImage(null);
            spieler1FeldZahl2.setText("0");
        }
        if (spielerAnzahl < 4) {
            if (spieler1Felder.size() > 2 && spieler1Felder.get(2).getAnzahlKarten() > 0) {
                spieler1Feld3.setImage((getImg(spieler1Felder.get(2).getImgURL())));
                spieler1FeldZahl3.setText(String.valueOf(spieler1Felder.get(2).getAnzahlKarten()));
            } else {
                spieler1Feld3.setImage(null);
                spieler1FeldZahl3.setText("0");
            }
        } else {
            spieler1Feld3.setImage(null);
            spieler1FeldZahl3.setText(null);
        }

        //Gegenspieler2
        Vector<Bohnenfeld> spieler2Felder = andereSpieler.get(1).getFelder();
        if (spieler2Felder.get(0).getAnzahlKarten() > 0) {
            spieler2Feld1.setImage((getImg(spieler2Felder.get(0).getImgURL())));
            spieler2FeldZahl1.setText(String.valueOf(spieler2Felder.get(0).getAnzahlKarten()));
        } else {
            spieler2Feld1.setImage(null);
            spieler2FeldZahl1.setText("0");
        }
        if (spieler2Felder.get(1).getAnzahlKarten() > 0) {
            spieler2Feld2.setImage((getImg(spieler2Felder.get(1).getImgURL())));
            spieler2FeldZahl2.setText(String.valueOf(spieler2Felder.get(1).getAnzahlKarten()));
        } else {
            spieler2Feld2.setImage(null);
            spieler2FeldZahl2.setText("0");
        }
        if (spielerAnzahl < 4) {
            if (spieler2Felder.size() > 2 && spieler2Felder.get(2).getAnzahlKarten() > 0) {
                spieler2Feld3.setImage((getImg(spieler2Felder.get(2).getImgURL())));
                spieler2FeldZahl3.setText(String.valueOf(spieler2Felder.get(2).getAnzahlKarten()));
            } else {
                spieler2Feld3.setImage(null);
                spieler2FeldZahl3.setText("0");
            }
        } else {
            spieler2Feld3.setImage(null);
            spieler2FeldZahl3.setText(null);
        }

        //Gegenspieler3
        if (spieler.size() > 3) {
            Vector<Bohnenfeld> spieler3Felder = andereSpieler.get(2).getFelder();
            if (spieler3Felder.get(0).getAnzahlKarten() > 0) {
                spieler3Feld1.setImage((getImg(spieler3Felder.get(0).getImgURL())));
                spieler3FeldZahl1.setText(String.valueOf(spieler3Felder.get(0).getAnzahlKarten()));
            } else {
                spieler3Feld1.setImage(null);
                spieler3FeldZahl1.setText("0");
            }
            if (spieler3Felder.get(1).getAnzahlKarten() > 0) {
                spieler3Feld2.setImage((getImg(spieler3Felder.get(1).getImgURL())));
                spieler3FeldZahl2.setText(String.valueOf(spieler3Felder.get(1).getAnzahlKarten()));
            } else {
                spieler3Feld2.setImage(null);
                spieler3FeldZahl2.setText("0");
            }
            if (spielerAnzahl < 4) {
                if (spieler3Felder.size() > 2 && spieler3Felder.get(2).getAnzahlKarten() > 0) {
                    spieler3Feld3.setImage((getImg(spieler3Felder.get(2).getImgURL())));
                    spieler3FeldZahl3.setText(String.valueOf(spieler3Felder.get(2).getAnzahlKarten()));
                } else {
                    spieler3Feld3.setImage(null);
                    spieler3FeldZahl3.setText("0");
                }
            } else {
                spieler3Feld3.setImage(null);
                spieler3FeldZahl3.setText(null);
            }
        }

        //Gegenspieler3
        if (spieler.size() == 5) {
            Vector<Bohnenfeld> spieler4Felder = andereSpieler.get(3).getFelder();
            if (spieler4Felder.get(0).getAnzahlKarten() > 0) {
                spieler4Feld1.setImage((getImg(spieler4Felder.get(0).getImgURL())));
                spieler4FeldZahl1.setText(String.valueOf(spieler4Felder.get(0).getAnzahlKarten()));
            } else {
                spieler4Feld1.setImage(null);
                spieler4FeldZahl1.setText("0");
            }
            if (spieler4Felder.get(1).getAnzahlKarten() > 0) {
                spieler4Feld2.setImage((getImg(spieler4Felder.get(1).getImgURL())));
                spieler4FeldZahl2.setText(String.valueOf(spieler4Felder.get(1).getAnzahlKarten()));
            } else {
                spieler4Feld2.setImage(null);
                spieler4FeldZahl2.setText("0");
            }
            if (spielerAnzahl < 4) {
                if (spieler4Felder.size() > 2 && spieler4Felder.get(2).getAnzahlKarten() > 0) {
                    spieler4Feld3.setImage((getImg(spieler4Felder.get(2).getImgURL())));
                    spieler4FeldZahl3.setText(String.valueOf(spieler4Felder.get(2).getAnzahlKarten()));
                } else {
                    spieler4Feld3.setImage(null);
                    spieler4FeldZahl3.setText("0");
                }
            } else {
                spieler4Feld3.setImage(null);
                spieler4FeldZahl3.setText(null);
            }
        }
        anzahlAblagekarten.setText(Integer.toString(spielemanager.getAblage().getAnzahlKarten()));
        anzahlDeckkarten.setText(Integer.toString(spielemanager.getDeck().getAnzahlKarten()));
    }

    /**
     * Aktualisiert den Schriftzug "Spieler am Zug:"
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    public void refreshSpielerAmZug() throws RemoteException {

        try {
            this.spielerAmZug.setText(spielemanager.getActPlayer().getName());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    /**
     * Gibt ein Bild nach Eingabe des Pfades zurück
     *
     * @param URL URL des Bildes
     * @return Image
     */
    protected Image getImg(String URL) {
        if (URL != null) {/*
            File file = new File(URL);
            String s = file.toURI().toURL().toString();
            return (new Image(s));*/
            return (new Image(URL));
        }
        return null;
    }

    /**
     * Setzt die gehandelten Karten
     *
     * @param handelKarten Karten, die aus dem Handel mit Spieler hervorgegangen sind
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn die URL falsch ist
     */
    public void setHandel(Vector<Karte> handelKarten) throws RemoteException, MalformedURLException {
        for (Karte k : handelKarten) {
            aktiverSpieler.addGehandelteKarten(k);
        }
        refreshGehandelteKarten();
    }

    /**
     * Verwaltet den Button "Nächste Phase"
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn die URL falsch ist
     */

    public void naechstePhase() throws RemoteException, MalformedURLException {
        try {
            spielemanager.nextPhase();
            if (!spielemanager.getFinished()) {
                //System.out.println(aktuellePhase);
                refresherServer.refreshPhase();
                refreshGehandelteKarten();
            }
            if (aktuellePhase == 4 && !spielemanager.getFinished()) {

                nextPhase.setVisible(false);
                naechstePhase();
            }
        } catch (NixAngebautException e) {
            if (spielemanager.getActPlayer().isUser()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fehler");
                alert.setContentText("Du musst mindestens die erste Handkart anbauen!");
                alert.showAndWait();
            }

        } catch (NichtAlleKartenAngebautException e) {
            if (spielemanager.getActPlayer().isUser()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fehler");
                alert.setContentText("Der Spieler " + e.getName() + " hat noch nicht alles angebaut!");
                alert.showAndWait();
            }
        }
    }

    /**
     * Aktualisiert die Anzahl der Spielkarten im Stapel
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    public void refreshStapel() throws RemoteException {
        Vector<Karte> aufgedeckteKarten = spielemanager.getAufgedeckteKarten();

        if (aufgedeckteKarten.size() > 0) {
            stapelKarte1.setImage(getImg(aufgedeckteKarten.get(0).getImgURL()));
            stapelKarte2.setImage(null);
        }
        if (aufgedeckteKarten.size() > 1) {
            stapelKarte2.setImage(getImg(aufgedeckteKarten.get(1).getImgURL()));
        }
        if (aufgedeckteKarten.size() == 0) {
            stapelKarte1.setImage(null);
            stapelKarte2.setImage(null);
        }

        anzahlAblagekarten.setText(Integer.toString(spielemanager.getAblage().getAnzahlKarten()));
        anzahlDeckkarten.setText(Integer.toString(spielemanager.getDeck().getAnzahlKarten()));

    }

    /**
     * Aktualisiert die Systemanchrichten (GUI) nach einer Phase
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn die URL falsch ist
     */
    public void refreshPhase() throws RemoteException, MalformedURLException {
        aktuellePhase = spielemanager.getPhase();
        Akteur spielerAmZug = spielemanager.getActPlayer();

        systemNachrichten.appendText("Starte Phase" + aktuellePhase + ": ");

        if (aktiverSpieler.getName().equals(spielerAmZug.getName())) {
            nextPhase.setVisible(true);
        } else {
            nextPhase.setVisible(false);
        }


        if (aktuellePhase == 1) {
            //Wegen RMI, Aber eigentlich suuuuper ungünstig und unnötig //TODO bessere Lösung
            Platform.runLater(() -> {
                try {
                    refreshHandkarten();
                    systemNachrichten.appendText("Erste Anbauphase \n");
                    systemNachrichten.appendText("Aktiver Spieler: " + spielerAmZug.getName() + "\n");
                    systemNachrichten.appendText("Anzahl Stapelleerungen: " + spielemanager.getAnzahlLeerungen() + "\n");
                    refresherServer.refreshSpielerAmZug();
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            });


        }
        if (aktuellePhase == 2) {
            Platform.runLater(() -> {
                try {
                    systemNachrichten.appendText("Handelsphase \n");
                    //Aktiviere die Handelschaltflächen
                    if (aktiverSpieler.getName().equals(spielerAmZug.getName())) {
                        sichtbarkeitHandeln(true);
                    }
                    refresherServer.refreshStapel();
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            });

        }
        if (aktuellePhase == 3) {
            Platform.runLater(() -> {
                systemNachrichten.appendText("Zweite Anbauphase \n");
                sichtbarkeitHandeln(false);
                try {
                    refresherServer.refreshStapel();
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            });

        }

        Platform.runLater(() -> {
            try {
                anzahlAblagekarten.setText(Integer.toString(spielemanager.getAblage().getAnzahlKarten()));
                anzahlDeckkarten.setText(Integer.toString(spielemanager.getDeck().getAnzahlKarten()));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });


        if (aktuellePhase == 4) {
            Platform.runLater(() -> {
                systemNachrichten.appendText("Zugphase \n \n");
                nextPhase.setVisible(false);
                try {
                    naechstePhase();
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            });

        }
    }

    /**
     * Aktualisiert die GUI nach dem Handel
     *
     * @throws RemoteException       wenn bei Verbindung zum Server etwas schiefläuft
     * @throws MalformedURLException wenn die URL falsch ist
     */
    public void refreshGehandelteKarten() throws RemoteException, MalformedURLException {
        anzubauen = aktiverSpieler.getGehandelteKarten();
        Vector<ImageView> img = new Vector<>();
        for (Karte k : anzubauen) {
            img.add(new ImageView(getImg(k.getImgURL())));
        }
        anbauKarten.setItems(FXCollections.observableList(img));
    }

    /**
     * Methode, die von Spielmanager gestartet wird, sobald Spiel beendet ist.
     *
     * @param gewinner Spieler, der gewonnen hat
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @FXML
    public void spielBeenden(Akteur gewinner) throws RemoteException {
        if (gewinner.getName().equals(aktiverSpieler.getName())) {
            Platform.runLater(() -> {
                Alert infoErnte = new Alert(Alert.AlertType.INFORMATION);
                infoErnte.setTitle("Spielende!");
                infoErnte.setContentText("Das Spiele ist zu Ende und alle Bohnenfelder wurden noch abgebaut. \n Und gewonnen hat...");
                Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setTitle("Spiel ist zu Ende!");
                try {
                    info.setContentText("Du hast das Spiel gewonnen!\n" +
                            "Du hattest " + gewinner.getTaler() + " Münzen.");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                info.showAndWait();
                currentStage.close();
            });
        } else {
            Platform.runLater(() -> {
                Alert infoErnte = new Alert(Alert.AlertType.INFORMATION);
                infoErnte.setTitle("Spielende!");
                infoErnte.setContentText("Das Spiele ist zu Ende und alle Bohnenfelder wurden noch abgebaut. \n Und gewonnen hat...");

                Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setTitle("Spiel ist zu Ende!");
                try {
                    info.setContentText(gewinner.getName() + " hat das Spiel gewonnen!\n" +
                            "Er hatte " + gewinner.getTaler() + " Münzen, du nur " + aktiverSpieler.getTaler() + ".");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                info.showAndWait();
                currentStage.close();
            });
        }

        refresherServer.leave(refresherClient);
        chat.verlasseChat(client);
    }

    /**
     * Bricht Spiel ab, sobald ein Spieler das Spiel verlässt
     *
     * @param leaver Spieler, der das Spiel verlässt
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @FXML
    public void spielAbbrechen(Akteur leaver) throws RemoteException {
        if (leaver.getName().equals(aktiverSpieler.getName())) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Spiel verlassen!");
                alert.setContentText("Du hast das Spiel verlassen. Es wird nun beendet!");
                alert.showAndWait();
                currentStage.close();
            });
        } else {
            Platform.runLater(() -> {
                Alert info = new Alert(Alert.AlertType.ERROR);
                info.setTitle("Spieler hat das Spiel verlassen!");
                try {
                    info.setContentText(leaver.getName() + " hat das Spiel verlassen!\n" +
                            "Das Spiel wird nun beendet.");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                info.showAndWait();
                currentStage.close();
            });
        }

        refresherServer.leave(refresherClient);
        chat.verlasseChat(client);
    }

    /**
     * Öffnet die Spielregeln
     *
     * @throws IOException wenn ein IO-Fehler auftritt
     */
    @FXML
    public void spielregeln() throws IOException {

        String gui = "";
        if (width < 1670) {
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
     * Öffnet die Bestenliste
     *
     * @throws IOException wenn ein IO-Fehler auftritt
     */
    public void bestenliste() throws IOException {

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
     * Minimiert das Fenster
     */
    @FXML
    public void minimize() {
        Stage stage = (Stage) name1.getScene().getWindow();
        stage.setIconified(true);
    }

    /**
     * Meldet dem Server, dass das Spiel abgebrochen wird
     *
     * @throws RemoteException wenn bei Verbindung zum Server etwas schiefläuft
     */
    @FXML
    public void spAbbrechen() throws RemoteException {
        finisherServer.spielAbbrechen(aktiverSpieler);
    }

    /**
     * Deaktiviert die Handelbutton je nach bool
     *
     * @param bool je nach Situation der GUI
     */
    public void disableHandeln(Boolean bool) {
        if (spielerAnzahl < 4) {
            handeln1.setDisable(bool);
            handeln2.setDisable(bool);

        } else if (spielerAnzahl == 4) {
            handeln1.setDisable(bool);
            handeln2.setDisable(bool);
            handeln3.setDisable(bool);
        } else {
            handeln1.setDisable(bool);
            handeln2.setDisable(bool);
            handeln3.setDisable(bool);
            handeln4.setDisable(bool);
        }
    }

    /**
     * Versteckt oder zeigt die Handelbutton je nach bool
     *
     * @param bool je nach Situation der GUI
     */
    public void sichtbarkeitHandeln(Boolean bool) {
        if (spielerAnzahl < 4) {
            handeln1.setVisible(bool);
            handeln2.setVisible(bool);

        } else if (spielerAnzahl == 4) {
            handeln1.setVisible(bool);
            handeln2.setVisible(bool);
            handeln3.setVisible(bool);
        } else {
            handeln1.setVisible(bool);
            handeln2.setVisible(bool);
            handeln3.setVisible(bool);
            handeln4.setVisible(bool);
        }
    }

}
