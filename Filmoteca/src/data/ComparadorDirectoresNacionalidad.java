package data;

import java.util.Comparator;

public class ComparadorDirectoresNacionalidad implements Comparator<Directores>{
    @Override
    public int compare(Directores d1, Directores d2){
        return d1.getNacionalidad().compareTo(d2.getNacionalidad());
    }
}
