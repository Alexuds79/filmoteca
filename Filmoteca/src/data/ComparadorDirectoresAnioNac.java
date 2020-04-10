package data;

import java.util.Comparator;

public class ComparadorDirectoresAnioNac implements Comparator<Directores>{
    @Override
    public int compare(Directores d1, Directores d2){
        return Integer.compare(d1.getFechaNac().getYear(), d2.getFechaNac().getYear());
    }
}
