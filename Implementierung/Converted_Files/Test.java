import Users.Benutzer;
import java.util.Date;
import java.text.*;
import Chat.Nachricht;

public class Test{

    public static void main(String[] args){
        Benutzer b = new Benutzer("Bitcher42","42424242",0,0);
        Date d = new Date();
        SimpleDateFormat ft = new SimpleDateFormat ("dd.MM.yyyy 'um' hh:mm:ss");

        Nachricht n = new Nachricht(b.getName(),"Hallo Welt! 42!");

        System.out.println(ft.format(d)+"\n"+n.getNutzername()+" schrieb:\n"+n.getText());
    }






}