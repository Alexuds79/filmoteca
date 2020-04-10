package data;

import java.util.Comparator;

public class ComparadorActoresNombre implements Comparator<Actores>{
    @Override
    public int compare(Actores a1, Actores a2){
        return a1.getNombre().compareTo(a2.getNombre());
    }
}
