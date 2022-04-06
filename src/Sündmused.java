import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Sündmused {
    private List<Sündmus> sündmused;

    public Sündmused() {
        this.sündmused = new ArrayList<>();
    }

    public Sündmus getSündmus(int indeks){
        return sündmused.get(indeks);
    }

    public int SündmusLength(){
        return sündmused.size();
    }

    public void kustutaSündmus(int indeks){
        sündmused.remove(indeks);
    }

    public void lisaSündmus(Sündmus sündmus){
        sündmused.add(sündmus);
    }

    public void väljastaSündmusi(){
        for (int i = 0; i < sündmused.size(); i++) {
            System.out.println(i+1 + ". " + sündmused.get(i).toString());
        }
    }
}
