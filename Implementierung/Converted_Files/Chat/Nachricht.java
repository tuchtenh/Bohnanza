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
     * @param name
     * @param text
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