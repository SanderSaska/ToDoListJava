import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SündmusAjaga extends Sündmus{
    private LocalDateTime aeg;
    private DateTimeFormatter formaat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public SündmusAjaga(String nimetus, LocalDateTime aeg) {
        super(nimetus);
        this.aeg = aeg;
    }

    public void setAeg(LocalDateTime aeg) {
        this.aeg = aeg;
    }

    public LocalDateTime getAeg() {
        return aeg;
    }

    public void kasOnÜletanudPraegustAega(Sündmus sündmus) {
        LocalDateTime praegu = LocalDateTime.now();
        if(praegu.isAfter(aeg)){
            sündmus.setTehtud(true);
        }
    }

    public void puhasta(List<Sündmus> sündmused){
        sündmused.removeIf(Sündmus::isTehtud);
    }

    @Override
    public String toString() {
        return getNimetus() + ";" + formaat.format(aeg);
    }
}
