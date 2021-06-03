package Exception;

//Exception für das Anbauen bei Phase3
public class NichtAlleKartenAngebautException extends SpielException {

    private String name;

    public NichtAlleKartenAngebautException(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
