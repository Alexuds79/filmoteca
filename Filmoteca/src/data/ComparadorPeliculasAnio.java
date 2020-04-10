package data;

import java.util.Comparator;

public class ComparadorPeliculasAnio implements Comparator<Peliculas>{
    @Override
    public int compare(Peliculas p1, Peliculas p2){
        return Integer.compare(p1.getAnio(), p2.getAnio());
    }
}
