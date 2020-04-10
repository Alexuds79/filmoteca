package data;

import java.util.Comparator;

public class ComparadorActoresAnioDebut implements Comparator<Actores>{
    @Override
    public int compare(Actores a1, Actores a2){
        return Integer.compare(a1.getAnioDebut(), a2.getAnioDebut());
    }
}
