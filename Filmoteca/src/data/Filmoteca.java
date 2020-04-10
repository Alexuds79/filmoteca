package data;

import com.coti.tools.Esdia;
import com.coti.tools.Rutas;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Filmoteca implements Serializable{
    //Atributos
    private List<Peliculas> peliculas = new ArrayList<>();
    private List<Directores> directores = new ArrayList<>();
    private List<Actores> actores = new ArrayList<>();
    private final String filenameA = "actores.txt";
    private final String filenameD = "directores.txt";
    private final String filenameP = "peliculas.txt";
    private final String filenameAB = "actores.bin";
    private final String filenameDB = "directores.bin";
    private final String filenamePB = "peliculas.bin";
    private final String foldername = "Filmot18";
    private final String filenameCol = "directores.col";
    private final String filenameHTML = "peliculas.html";

    //Métodos
    public void exportFilmsToHTML(){
        String ruta = Rutas.pathToFileInFolderOnDesktop(foldername, filenameHTML).toString();
        
        try(PrintWriter pw = new PrintWriter(ruta)){
            pw.printf("<!DOCTYPE html><HTML>\n<HEAD><meta charset=\"UTF-8\"><H1>PELÍCULAS</H1></HEAD>");
            
            for(Peliculas p : peliculas){
                pw.printf("%s\n", p.asTableRow());
            }
        }
        catch(FileNotFoundException e){
            System.err.println("No se ha podido localizar el archivo: "+e.toString());
            System.exit(-1);
        }
        finally{
            System.out.printf("\n\nSe ha creado una tabla con %d registros\n\n", this.peliculas.size());
            System.out.println("Ruta del archivo: "+ruta);
        }
    }
    
    public void exportDirToCol(String delim){
        Path p = Rutas.pathToFileInFolderOnDesktop(foldername, filenameCol);
        List<String> lines = new ArrayList<>();
        
        for(Directores d : directores){
            lines.add(d.exportStateWithDelimiters(delim));
        }
        
        try{
            Files.write(p, lines, Charset.forName("UTF8"));
        }
        catch(IOException e){
            System.out.println("IOException: "+e.toString());
        }
    }
    
    public String[] getListOfFilmsAsColumnsAutor(String nombre){
        boolean flag=false;
        Actores act = new Actores();
        
        for (Actores a : actores){
            flag=false;
            if(a.getNombre().equals(nombre)){
                flag=true;
                act = a;
                break;
            }
        }
        
        if(flag==false){
            System.err.println("\nNo existe ningún actor con ese nombre en nuestra base de datos...\n\n");
            return null;
        }
        else{
            List<Peliculas> titulos = new ArrayList<>();

            for(Peliculas p : peliculas){
                if(p.getReparto().contains(nombre)){
                    titulos.add(p);
                }
            }

            String[] lines = new String[titulos.size()];

            for (int i=0; i<lines.length; i++){
                lines[i] = titulos.get(i).exportStateAsColumns();
            }
             
            if(titulos.isEmpty()) return null;
            else return lines;
        }
    }
    
    public void modificaActor(String nombre, String menu){
        boolean flag=false;
        Actores act = new Actores();
        
        for (Actores a : actores){
            flag=false;
            if(a.getNombre().equals(nombre)){
                flag=true;
                act = a;
                break;
            }
        }
        
        if(flag==false){
            System.err.println("\nNo existe ningún actor con ese nombre en nuestra base de datos...\n\n");
        }
        else{
            String opcion;
            boolean salir = false;
            String[] availableOp = {"1", "2", "3", "q"};
        
            do{
                opcion = Esdia.readString(menu, availableOp);
            
                switch(opcion){
                    case "1": modificaFechaNac(act); break;
                    case "2": modificaNacionalidad(act); break;
                    case "3": modificaAnioDebut(act); break;
                    case "q": salir = Esdia.yesOrNo("¿Seguro que quieres salir al menú Actores?"); break;
                }
            }while(!salir);
        }
    }
    
    public void modificaFechaNac(Actores a){
        int anio, mes, dia;
        LocalDate fechaNac;
        anio = Esdia.readInt("Año de Nacimiento (nuevo) : ", 0, 2019);
        mes = Esdia.readInt("Mes de Nacimiento (nuevo) : ", 1, 12);
        dia = Esdia.readInt("Día de Nacimiento (nuevo) : ", 1, 31);
        fechaNac = LocalDate.of(anio, mes, dia);
        
        a.setFechaNac(fechaNac);
    }
    
    public void modificaNacionalidad(Actores a){
        String nacionalidad = Esdia.readString("Nacionalidad nueva: ");
        a.setNacionalidad(nacionalidad);
    }
    
    public void modificaAnioDebut(Actores a){
        int anioDebut = Esdia.readInt("Año de Debut (nuevo): ");
        a.setAnioDebut(anioDebut);
    }
    
    public void removeUnActor(String nombre){
        boolean flag=false;
        Actores act = new Actores();
        
        for (Actores a : actores){
            flag=false;
            if(a.getNombre().equals(nombre)){
                flag=true;
                act = a;
                break;
            }
        }
        
        if(flag==false){
            System.err.println("\nNo existe ningún actor con ese nombre en nuestra base de datos...\n\n");
        }
        else{
            flag=false;
            int i=1;
            
            for(Peliculas p : peliculas){
                if(act.getPeliculas().contains(p.getTitulo())){
                    if(flag==false){
                        flag=true;
                        System.err.println("\nNo podemos eliminar el actor. Películas dadas de alta de "+act.getNombre()+":");
                    }
                    
                    System.err.printf("%d) ", i++);
                    System.err.println(p.getTitulo());
                }
            }
            System.out.println("\n");
            
            if(flag==false){
                actores.remove(act);
            }
        }
    }
    
    public void addUnActor(String nombre, LocalDate fechaNac, String nacionalidad, int anioDebut, List<String> peliculas){
        Actores newActor = new Actores();
        boolean flag=false;
        
        newActor.setAnioDebut(anioDebut);
        newActor.setFechaNac(fechaNac);
        newActor.setNacionalidad(nacionalidad);
        newActor.setNombre(nombre);
        newActor.setPeliculas(peliculas);
        
        for(Actores a: actores){
            flag=false;
            if(a.getNombre().equals(nombre)){
                flag=true;
                System.err.println("\nYa existe un actor con ese mismo nombre en la base de datos...");
                System.err.println("Puede modificar la información de "+nombre+" en la sección de modificaciones\n\n");
                break;
            }
        }
        
        if(flag==false){
            this.actores.add(newActor);
        }
    }
    
    public void modificaDirector(String nombre, String menu){
        boolean flag=false;
        Directores dir = new Directores();
        
        for (Directores d : directores){
            flag=false;
            if(d.getNombre().equals(nombre)){
                flag=true;
                dir = d;
                break;
            }
        }
        
        if(flag==false){
            System.err.println("\nNo existe ningún director con ese nombre en nuestra base de datos...\n\n");
        }
        else{
            String opcion;
            boolean salir = false;
            String[] availableOp = {"1", "2", "3", "q"};
        
            do{
                opcion = Esdia.readString(menu, availableOp);
            
                switch(opcion){
                    case "1": modificaFechaNac(dir); break;
                    case "2": modificaNacionalidad(dir); break;
                    case "3": modificaOcupacion(dir); break;
                    case "q": salir = Esdia.yesOrNo("¿Seguro que quieres salir al menú Directores?"); break;
                }
            }while(!salir);
        }
    }
    
    public void modificaFechaNac(Directores dir){
        int anio, mes, dia;
        LocalDate fechaNac;
        anio = Esdia.readInt("Año de Nacimiento (nuevo) : ", 0, 2019);
        mes = Esdia.readInt("Mes de Nacimiento (nuevo) : ", 1, 12);
        dia = Esdia.readInt("Día de Nacimiento (nuevo) : ", 1, 31);
        fechaNac = LocalDate.of(anio, mes, dia);
        
        dir.setFechaNac(fechaNac);
    }
    
    public void modificaNacionalidad(Directores dir){
        String nacionalidad = Esdia.readString("Nacionalidad nueva: ");
        dir.setNacionalidad(nacionalidad);
    }
    
    public void modificaOcupacion(Directores dir){
        String ocupacion = Esdia.readString("Ocupación nueva: ");
        dir.setOcupacion(ocupacion);
    }
    
    public void removeUnDirector(String nombre){
        boolean flag=false;
        Directores dir = new Directores();
        
        for (Directores d : directores){
            flag=false;
            if(d.getNombre().equals(nombre)){
                flag=true;
                dir = d;
                break;
            }
        }
        
        if(flag==false){
            System.err.println("\nNo existe ningún director con ese nombre en nuestra base de datos...\n\n");
        }
        else{
            flag=false;
            int i=1;
            
            for(Peliculas p : peliculas){
                if(dir.getPeliculas().contains(p.getTitulo())){
                    if(flag==false){
                        flag=true;
                        System.err.println("\nNo podemos eliminar el director. Películas dadas de alta de "+dir.getNombre()+":");
                    }
                    
                    System.err.printf("%d) ", i++);
                    System.err.println(p.getTitulo());
                }
            }
            System.out.println("\n");
            
            if(flag==false){
                directores.remove(dir);
            }
        }
    }
    
    public void addUnDirector(String nombre, LocalDate fechaNac, String nacionalidad, String ocupacion, List<String> peliculas){
        Directores newDir = new Directores();
        boolean flag=false;
        
        newDir.setNombre(nombre);
        newDir.setFechaNac(fechaNac);
        newDir.setNacionalidad(nacionalidad);
        newDir.setOcupacion(ocupacion);
        newDir.setPeliculas(peliculas);
        
        for(Directores d : directores){
            flag=false;
            if(d.getNombre().equals(nombre)){
                flag=true;
                System.err.println("\nYa existe un director con ese mismo nombre en la base de datos...");
                System.err.println("Puede modificar la información de "+nombre+" en la sección de modificaciones\n\n");
                break;
            }
        }
        
        if(flag==false){
            this.directores.add(newDir);
        }
        
    }
    
    public void consultaPelicula(String titulo){
        boolean flag=false;
        Peliculas film = new Peliculas();
        
        for (Peliculas p : peliculas){
            flag=false;
            if(p.getTitulo().equals(titulo)){
                flag=true;
                film = p;
                break;
            }
        }
        
        if(flag==false){
            System.err.println("\nNo existe una película con ese título en nuestra base de datos...\n\n");
        }
        else{
            System.out.println("\n\nTÍTULO: "+film.getTitulo());
            System.out.println("AÑO: "+film.getAnio());
            System.out.println("DURACIÓN: "+film.getDuracion());
            System.out.println("PAÍS: "+film.getPais());
            System.out.println("DIRECCIÓN: "+film.getDireccion());
            System.out.println("GUION: "+film.getGuion());
            System.out.println("MÚSICA: "+film.getMusica());
            System.out.println("FOTOGRAFÍA: "+film.getFotografia());
            System.out.println("REPARTO: "+film.getReparto());
            System.out.println("PRODUCTORA: "+film.getProductora());
            System.out.println("GÉNERO: "+film.getGenero());
            System.out.println("SINOPSIS: "+film.getSinopsis());
            System.out.println("\n");
        }
    }
    
    public void modificaPeliculas(String titulo, String menu){
        boolean flag=false;
        Peliculas film = new Peliculas();
        
        for (Peliculas p : peliculas){
            flag=false;
            if(p.getTitulo().equals(titulo)){
                flag=true;
                film = p;
                break;
            }
        }
        
        if(flag==false){
            System.err.println("\nNo existe una película con ese título en nuestra base de datos...\n\n");
        }
        else{
            String opcion;
            boolean salir = false;
            String[] availableOp = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "q"};
        
            do{
                opcion = Esdia.readString(menu, availableOp);
            
                switch(opcion){
                    case "1": modificaAnio(film); break;
                    case "2": modificaDuracion(film); break;
                    case "3": modificaPais(film); break;
                    case "4": modificaGuion(film); break;
                    case "5": modificaMusica(film); break;
                    case "6": modificaFoto(film); break;
                    case "7": modificaProductora(film); break;
                    case "8": modificaGenero(film); break;
                    case "9": modificaSinopsis(film); break;
                    case "q": salir = Esdia.yesOrNo("¿Seguro que quieres salir al menú Películas?"); break;
                }
            }while(!salir);
        }
    }
    
    public void modificaSinopsis(Peliculas p){
        String cadena = Esdia.readString("Ingrese información nueva: "); 
        p.setSinopsis(cadena);
    }
    
    public void modificaGenero(Peliculas p){
        String cadena = Esdia.readString("Ingrese información nueva: "); 
        p.setGenero(cadena);
    }
    
    public void modificaProductora(Peliculas p){
        String cadena = Esdia.readString("Ingrese información nueva: "); 
        p.setProductora(cadena);
    }
    
    public void modificaFoto(Peliculas p){
        String cadena = Esdia.readString("Ingrese información nueva: "); 
        p.setFotografia(cadena);
    }
    
    public void modificaMusica(Peliculas p){
        String cadena = Esdia.readString("Ingrese información nueva: "); 
        p.setMusica(cadena);
    }
    
    public void modificaGuion(Peliculas p){
        String cadena = Esdia.readString("Ingrese información nueva: "); 
        p.setGuion(cadena);
    }
    
    public void modificaPais(Peliculas p){
        String cadena = Esdia.readString("Ingrese información nueva: "); 
        p.setPais(cadena);
    }
    
    public void modificaAnio(Peliculas p){
        int valor = Esdia.readInt("Ingrese un nuevo valor: ");
        p.setAnio(valor);
    }
    
    public void modificaDuracion(Peliculas p){
        int valor = Esdia.readInt("Ingrese un nuevo valor: ");
        p.setDuracion(valor);
    }
    
    public void removeUnaPelicula(String titulo){
        boolean flag=false;
        
        for (Peliculas p : peliculas){
            flag=false;
            if(p.getTitulo().equals(titulo)){
                flag=true;
                peliculas.remove(p);
                break;
            }
        }
        
        if(flag==false){
            System.err.println("\nNo existe una película con ese título en nuestra base de datos...\n\n");
        }
        else{
            for(Directores d : directores){
                if(d.getPeliculas().contains(titulo)){
                    d.getPeliculas().remove(titulo);
                }
            }
            
            for(Actores a : actores){
                if(a.getPeliculas().contains(titulo)){
                    a.getPeliculas().remove(titulo);
                }
            }
        }
        
    }
    
    public void addUnaPelicula(String titulo, int anio, int duracion, String pais, List<String> direccion, String guion, String musica, String foto, List<String> reparto, String productora, String genero, String sinopsis){
        Peliculas newfilm = new Peliculas();
        boolean flag=false;
        
        newfilm.setAnio(anio);
        
        //Eliminamos posibles duplicados (en caso de que el usuario introduzca el mismo actor dos veces)
        //Lo hacemos con un Set, que no admite duplicados
        Set<String> hs = new HashSet<>();
        hs.addAll(direccion);
        direccion.clear();
        direccion.addAll(hs);
        newfilm.setDireccion(direccion);
        
        newfilm.setDuracion(duracion);
        newfilm.setFotografia(foto);
        newfilm.setGenero(genero);
        newfilm.setGuion(guion);
        newfilm.setMusica(musica);
        newfilm.setPais(pais);
        newfilm.setProductora(productora);
        
        //Eliminamos de forma análoga los posibles duplicados en reparto
        Set<String> hs2 = new HashSet<>();
        hs2.addAll(reparto);
        reparto.clear();
        reparto.addAll(hs2);
        newfilm.setReparto(reparto);
        
        newfilm.setSinopsis(sinopsis);
        newfilm.setTitulo(titulo);
        
        for(Peliculas p: peliculas){
            flag=false;
            if(p.getTitulo().equals(titulo)){
                flag=true;
                System.err.println("\nYa existe una película con ese mismo título en la base de datos...");
                System.err.println("Puede modificar la información de "+titulo+" en la sección de modificaciones\n\n");
                break;
            }
        }
        hs.clear();
        if(flag==false){
            peliculas.add(newfilm);
        
            for(String s : direccion){
                flag=false;
                for(Directores d : directores){
                    if(s.equals(d.getNombre())){
                        flag=true;
                        d.getPeliculas().add(titulo);
                        hs.addAll(d.getPeliculas()); //Eliminamos posibles duplicados
                        d.getPeliculas().clear();
                        d.getPeliculas().addAll(hs);
                        break;
                    }
                }
                if(flag==false){
                    Directores newDir = new Directores();
                    newDir.setNombre(s);
                    newDir.getPeliculas().add(titulo);
                    newDir.setOcupacion("Director");
                    directores.add(newDir);
                }   
            }
        
            hs.clear();
            for(String s : reparto){
                flag=false;
                for(Actores a : actores){
                    if(s.equals(a.getNombre())){
                        flag=true;
                        a.getPeliculas().add(titulo);
                        hs.addAll(a.getPeliculas()); //Eliminamos posibles duplicados
                        a.getPeliculas().clear();
                        a.getPeliculas().addAll(hs);
                        break;
                    }
                }
                if(flag==false){
                    Actores newAct = new Actores();
                    newAct.setNombre(s);
                    newAct.getPeliculas().add(titulo);
                    actores.add(newAct);
                }
            }
        }
        
    }
    
    public void sortByFilmsAnio(){
        Comparator<Peliculas> c = new ComparadorPeliculasAnio();
        
        Collections.sort(peliculas, c);
    }
    
    public void sortByFilms(){
        Comparator<Peliculas> c = new ComparadorPeliculas();
        
        Collections.sort(peliculas, c);
    }
    
    public void sortByDirectors(){
        Comparator<Directores> cYear = new ComparadorDirectoresAnioNac();
        Comparator<Directores> cCountry = new ComparadorDirectoresNacionalidad();
        
        Collections.sort(directores, cYear);
        Collections.sort(directores, cCountry);
    }
    
    public void sortByActors(){
        Comparator<Actores> cName = new ComparadorActoresNombre();
        Comparator<Actores> cAnioDebut = new ComparadorActoresAnioDebut();
        
        Collections.sort(actores, cName);
        Collections.sort(actores, cAnioDebut);
    }
    
    public String[] getListOfFilmsAsColumns(){
        String[] lines = new String[peliculas.size()];
        
        for (int i=0; i<lines.length; i++){
            lines[i] = peliculas.get(i).exportStateAsColumns();
        }
        
        return lines;
    }
    
    public String[] getListOfDirectorsAsColumns(boolean flag){ 
        String[] lines = new String[directores.size()];
        
        for (int i=0; i<lines.length; i++){
            lines[i] = directores.get(i).exportStateAsColumns(flag);
        }
        
        return lines;
    }
    
    public String[] getListOfActorsAsColumns(boolean flag){
        String[] lines = new String[actores.size()];
        
        for (int i=0; i<lines.length; i++){
            lines[i] = actores.get(i).exportStateAsColumns(flag);
        }
        
        return lines;
    }
    
    public void addActoresBin(Path pathActores){
        List<Actores> actoresCarga = new ArrayList<>();
        FileInputStream fis;
        BufferedInputStream bis;
        ObjectInputStream ois = null;
        
        try{
            fis = new FileInputStream(pathActores.toFile());
            bis = new BufferedInputStream(fis);
            ois = new ObjectInputStream(bis);
            actoresCarga = (ArrayList<Actores>) ois.readObject();
            this.actores.addAll(actoresCarga);
            
            if(null!=ois){
                try{
                    ois.close(); 
                }
                catch(IOException e){
                    System.err.printf("Error al intentar cerrar el archivo: "+e.toString());
                    System.exit(-1);
                }
            }
        }
        catch(IOException e){
            System.err.println("Error de lectura: "+e.toString());
            System.exit(-1);
        }
        catch(ClassNotFoundException e){
            System.err.println("Error de interpretación de clases: "+e.toString());
            System.exit(-1);
        }
    }
    
    public void addActores(String[] aux){
        Actores actor = new Actores();
        List<String> auxFilms = new ArrayList<>();
        
        actor.setNombre(aux[0]);
                    
        if(aux[1].compareTo("")!=0){
            actor.setFechaNac(LocalDate.parse(aux[1]));
        }
        else{
            actor.setFechaNac(LocalDate.of(1, Month.JANUARY, 1));
        }
                    
        actor.setNacionalidad(aux[2]);
                    
        if(aux[3].compareTo("")!=0){
            actor.setAnioDebut(Integer.parseInt(aux[3]));
        }
        else{
            actor.setAnioDebut(0);
        }
                    
        String[] temp = aux[4].split("\t");
        auxFilms.addAll(Arrays.asList(temp)); 
        actor.setPeliculas(auxFilms);
        
        this.actores.add(actor);
    }
    
    public void addPeliculasBin(Path pathPeliculas){
        List<Peliculas> peliculasCarga = new ArrayList<>();
        FileInputStream fis;
        BufferedInputStream bis;
        ObjectInputStream ois = null;
        
        try{
            fis = new FileInputStream(pathPeliculas.toFile());
            bis = new BufferedInputStream(fis);
            ois = new ObjectInputStream(bis);
            peliculasCarga = (ArrayList<Peliculas>) ois.readObject();
            this.peliculas.addAll(peliculasCarga);
            
            if(null!=ois){
                try{
                    ois.close(); 
                }
                catch(IOException e){
                    System.err.printf("Error al intentar cerrar el archivo: "+e.toString());
                    System.exit(-1);
                }
            }
        }
        catch(IOException e){
            System.err.println("Error de lectura: "+e.toString());
            System.exit(-1);
        }
        catch(ClassNotFoundException e){
            System.err.println("Error de interpretación de clases: "+e.toString());
            System.exit(-1);
        }
    }
    
    public void addPeliculas(String[] aux){
        Peliculas film = new Peliculas();
        List<String> auxDirAct = new ArrayList<>();
        
        film.setTitulo(aux[0]);
                    
        if(aux[1].compareTo("")!=0){
            film.setAnio(Integer.parseInt(aux[1]));
        }
        else{
            film.setAnio(-1);
        }
                    
        if(aux[2].compareTo("")!=0){
            film.setDuracion(Integer.parseInt(aux[2]));
        }
        else{
            film.setDuracion(-1);
        }
                    
        film.setPais(aux[3]);
        film.setGuion(aux[5]);
        film.setMusica(aux[6]);
        film.setFotografia(aux[7]);
        film.setProductora(aux[9]);
        film.setGenero(aux[10]);
        film.setSinopsis(aux[11]);
                    
        String[] temp = aux[4].split("\t");          
        auxDirAct.addAll(Arrays.asList(temp)); 
        film.setDireccion(auxDirAct);
                    
        temp = aux[8].split("\t");          
        auxDirAct.addAll(Arrays.asList(temp)); 
        film.setReparto(auxDirAct);
        
        this.peliculas.add(film);
    }
    
    public void addDirectoresBin(Path pathDirectores){
        List<Directores> directoresCarga = new ArrayList<>();
        FileInputStream fis;
        BufferedInputStream bis;
        ObjectInputStream ois = null;
        
        try{
            fis = new FileInputStream(pathDirectores.toFile());
            bis = new BufferedInputStream(fis);
            ois = new ObjectInputStream(bis);
            directoresCarga = (ArrayList<Directores>) ois.readObject();
            this.directores.addAll(directoresCarga);
            
            if(null!=ois){
                try{
                    ois.close(); 
                }
                catch(IOException e){
                    System.err.printf("Error al intentar cerrar el archivo: "+e.toString());
                    System.exit(-1);
                }
            }
        }
        catch(IOException e){
            System.err.println("Error de lectura: "+e.toString());
            System.exit(-1);
        }
        catch(ClassNotFoundException e){
            System.err.println("Error de interpretación de clases: "+e.toString());
            System.exit(-1);
        }
    }
    
    public void addDirectores(String[] aux){
        Directores director = new Directores();
        List<String> auxFilms = new ArrayList<>();
        
        director.setNombre(aux[0]);
                    
        if(aux[1].compareTo("")!=0){
            director.setFechaNac(LocalDate.parse(aux[1]));
        }
        else{
            director.setFechaNac(LocalDate.of(1, Month.JANUARY, 1));
        }
                    
        director.setNacionalidad(aux[2]);
        director.setOcupacion(aux[3]);
                    
        String[] temp = aux[4].split("\t");
        auxFilms.addAll(Arrays.asList(temp)); 
        director.setPeliculas(auxFilms);
        
        this.directores.add(director);
    }
    
    public void exportActorsToBinaryFiles(){
        Path p = Rutas.pathToFileInFolderOnDesktop(this.foldername, this.filenameAB);
        FileOutputStream fos;
        BufferedOutputStream bos;
        ObjectOutputStream oos = null;
        
        try{
            fos = new FileOutputStream(p.toFile());
            bos = new BufferedOutputStream(fos);
            oos = new ObjectOutputStream(bos);
            oos.writeObject(this.actores);
            
            if(null!=oos){
                try{
                    oos.close();
                }
                catch(IOException e){
                    System.out.println("Error al intentar cerrar el fichero: "+e.toString());
                    System.exit(-1);
                }
            }
        }
        catch(IOException e){
            System.out.println("No fue posible guardar el archivo: "+e.toString());
            System.exit(-1);
        }
    }
    
    public void exportDirectorsToBinaryFiles(){
        Path p = Rutas.pathToFileInFolderOnDesktop(this.foldername, this.filenameDB);
        FileOutputStream fos;
        BufferedOutputStream bos;
        ObjectOutputStream oos = null;
        
        try{
            fos = new FileOutputStream(p.toFile());
            bos = new BufferedOutputStream(fos);
            oos = new ObjectOutputStream(bos);
            oos.writeObject(this.directores);
            
            if(null!=oos){
                try{
                    oos.close();
                }
                catch(IOException e){
                    System.out.println("Error al intentar cerrar el fichero: "+e.toString());
                    System.exit(-1);
                }
            }
        }
        catch(IOException e){
            System.out.println("No fue posible guardar el archivo: "+e.toString());
            System.exit(-1);
        }
    }
    
    public void exportFilmsToBinaryFiles(){
        Path p = Rutas.pathToFileInFolderOnDesktop(this.foldername, this.filenamePB);
        FileOutputStream fos;
        BufferedOutputStream bos;
        ObjectOutputStream oos = null;
        
        try{
            fos = new FileOutputStream(p.toFile());
            bos = new BufferedOutputStream(fos);
            oos = new ObjectOutputStream(bos);
            oos.writeObject(this.peliculas);
            
            if(null!=oos){
                try{
                    oos.close();
                }
                catch(IOException e){
                    System.out.println("Error al intentar cerrar el fichero: "+e.toString());
                    System.exit(-1);
                }
            }
        }
        catch(IOException e){
            System.out.println("No fue posible guardar el archivo: "+e.toString());
            System.exit(-1);
        }
    }
    
    public String getFilenameA(){
        return this.filenameA;
    }
    
    public String getFilenameD(){
        return this.filenameD;
    }
    
    public String getFilenameP(){
        return this.filenameP;
    }
    
    public String getFilenameAB(){
        return this.filenameAB;
    }
    
    public String getFilenameDB(){
        return this.filenameDB;
    }
    
    public String getFilenamePB(){
        return this.filenamePB;
    }
    
    public String getFoldername(){
        return this.foldername;
    }
    
    public List<Peliculas> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(List<Peliculas> peliculas) {
        this.peliculas = peliculas;
    }

    public List<Directores> getDirectores() {
        return directores;
    }

    public void setDirectores(List<Directores> directores) {
        this.directores = directores;
    }

    public List<Actores> getActores() {
        return actores;
    }

    public void setActores(List<Actores> actores) {
        this.actores = actores;
    }
    
}