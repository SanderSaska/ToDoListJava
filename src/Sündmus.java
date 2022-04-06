import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;

public class Sündmus extends Sündmused {
    private String nimetus;
    private boolean tehtud;

    public Sündmus(String nimetus) {
        this.nimetus = nimetus;
        this.tehtud = false;
    }

    public String getNimetus() {
        return nimetus;
    }

    public void setNimetus(String nimetus) {
        this.nimetus = nimetus;
    }

    public boolean isTehtud() {
        return tehtud;
    }

    public void setTehtud(boolean tehtud) {
        this.tehtud = tehtud;
    }

    @Override
    public String toString() {
        return nimetus;
    }
}
