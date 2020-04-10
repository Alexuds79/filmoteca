package filmotecaMain;

import java.io.Serializable;
import view.View;

public class FilmotecaMain implements Serializable{
    static View v = new View();
    
    public static void main(String[] args) {
        v.compruebaFicheros();
        
        v.runMenu("\n\n1. Archivos\n"
                + "2. Películas\n"
                + "3. Directores\n"
                + "4. Actores\n"
                + "5. Listados\n"
                + "q. Salir\n");
        
        v.exportToBinaryFiles();
    }
    
}
