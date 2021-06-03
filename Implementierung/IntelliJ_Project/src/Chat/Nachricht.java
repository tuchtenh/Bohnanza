package Chat;

import java.util.Date;


/**
 *
 */
public class Nachricht {

    private String nutzername;
    private String text;
    private Date zeitstempel = new Date();


    /**
     * Kontruktor der die Information ueber die Nachdricht hat und den Text der Nachricht
     * @param name Benutzername des Senders. darf nicht null sein.
     * @param text inhalt der Nachricht. darf nicht null sein.
     */
    public Nachricht(String name, String text){
        this.text=text;
        this.nutzername=name;
    }


    /**
     * @return
     */
    public String getNutzername(){
        return this.nutzername;
    }


    /**
     * @param n
     */
    public void setNutzername(String n){
        this.nutzername=n;
    }


    /**
     * @return
     */
    public String getText(){
        return this.text;
    }


    /**
     * @param text
     */
    public void setText(String text){
        this.text=text;
    }


    /**
     * @return
     */
    public Date getDate(){
        return this.zeitstempel;
    }

}