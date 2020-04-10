package data;

import java.util.Comparator;

public class ComparadorPeliculas implements Comparator<Peliculas>{
    
    @Override
    public int compare(Peliculas p1, Peliculas p2){
        return p1.getTitulo().compareTo(p2.getTitulo());
    }
}
