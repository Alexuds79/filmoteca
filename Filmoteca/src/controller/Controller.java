package controller;

import com.coti.tools.Rutas;
import data.Filmoteca;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Controller implements Serializable{
    Filmoteca f = new Filmoteca();
    
    public void exportFilmsToHTML(){
        f.exportFilmsToHTML();
    }
    
    public void exportDirToCol(){
        f.exportDirToCol(";");
    }
    
    public String[] obtenerListadoPeliculasAutor(String nombre){
        return f.getListOfFilmsAsColumnsAutor(nombre);
    }
    
    public void modificaActor(String nombre, String menu){
        f.modificaActor(nombre, menu);
    }
    
    public void removeUnActor(String nombre){
        f.removeUnActor(nombre);
    }
    
    public void addUnActor(String nombre, LocalDate fechaNac, String nacionalidad, int anioDebut, List<String> peliculas){
        f.addUnActor(nombre, fechaNac, nacionalidad, anioDebut, peliculas);
    }
    
    public void modificaDirector(String nombre, String menu){
        f.modificaDirector(nombre, menu);
    }
    
    public void removeUnDirector(String nombre){
        f.removeUnDirector(nombre);
    }
    
    public void addUnDirector(String nombre, LocalDate fechaNac, String nacionalidad, String ocupacion, List<String> peliculas){
        f.addUnDirector(nombre, fechaNac, nacionalidad, ocupacion, peliculas);
    }
    
    public void consultaPelicula(String titulo){
        f.consultaPelicula(titulo);
    }
    
    public void modificaPeliculas(String titulo, String menu){
        f.modificaPeliculas(titulo, menu);
    }
    
    public void removeUnaPelicula(String titulo){
        f.removeUnaPelicula(titulo);
    }
    
    public void addUnaPelicula(String titulo, int anio, int duracion, String pais, List<String> direccion, String guion, String musica, String foto, List<String> reparto, String productora, String genero, String sinopsis){
        f.addUnaPelicula(titulo, anio, duracion, pais, direccion, guion, musica, foto, reparto, productora, genero, sinopsis);
    }
    
    public void sortByFilms(){
        f.sortByFilms();
    }
    
    public void sortByFilmsAnio(){
        f.sortByFilmsAnio();
    }
    
    public void sortByDirectors(){
        f.sortByDirectors();
    }
    
    public void sortByActors(){
        f.sortByActors();
    }
    
    public String[] obtenerListadoFilms(){
        return f.getListOfFilmsAsColumns();
    }
    
    public String[] obtenerListadoDirectores(boolean flag){
        return f.getListOfDirectorsAsColumns(flag);
    }
    
    public String[] obtenerListadoActores(boolean flag){
        return f.getListOfActorsAsColumns(flag);
    }
    
    public void compruebaFicheros(){
        //Rutas de posibles ficheros binarios
        Path pathActores = Rutas.pathToFileInFolderOnDesktop(f.getFoldername(), f.getFilenameAB());
        Path pathDirectores = Rutas.pathToFileInFolderOnDesktop(f.getFoldername(), f.getFilenameDB());
        Path pathPeliculas = Rutas.pathToFileInFolderOnDesktop(f.getFoldername(), f.getFilenamePB());
        
        if(!Files.exists(pathActores)){ //Apertura Actores tipo texto si no existe el binario
            pathActores = Rutas.pathToFileInFolderOnDesktop(f.getFoldername(), f.getFilenameA());
            List<String> actoresCarga = new ArrayList<>();
            
            try{
                actoresCarga = Files.readAllLines(pathActores);
            }
            catch(IOException e){
                System.err.println("Error de apertura de fichero: "+e.toString());
                System.exit(-1);
            }
            finally{
                for(String s : actoresCarga){
                    String[] aux;
                    aux = s.split("#");
                    f.addActores(aux);
                }
            }
        }
        else{ //Apertura actores como binarios
            f.addActoresBin(pathActores);
        }
        
        if(!Files.exists(pathDirectores)){ //Apertura Directores tipo texto
            pathDirectores = Rutas.pathToFileInFolderOnDesktop(f.getFoldername(), f.getFilenameD());
            List<String> directoresCarga = new ArrayList<>();
            
            try{
                directoresCarga = Files.readAllLines(pathDirectores);
            }
            catch(IOException e){
                System.err.println("Error de apertura de fichero: "+e.toString());
                System.exit(-1);
            }
            finally{
                for(String s : directoresCarga){
                    String[] aux;
                    aux = s.split("#");
                    f.addDirectores(aux);
                }
            }
        }
        else{
            f.addDirectoresBin(pathDirectores);
        }
        
        if(!Files.exists(pathPeliculas)){ //Apertura Peliculas tipo texto
            pathPeliculas = Rutas.pathToFileInFolderOnDesktop(f.getFoldername(), f.getFilenameP());
            List<String> peliculasCarga = new ArrayList<>();
            
            try{
                peliculasCarga = Files.readAllLines(pathPeliculas);
            }
            catch(IOException e){
                System.err.println("Error de apertura de fichero: "+e.toString());
                System.exit(-1);
            }
            finally{
                for(String s : peliculasCarga){
                    String[] aux;
                    aux = s.split("#");
                    f.addPeliculas(aux);
                }
            }
        }
        else{
            f.addPeliculasBin(pathPeliculas);
        }
    }
    
    public void exportToBinaryFiles(){
        f.exportActorsToBinaryFiles();
        f.exportDirectorsToBinaryFiles();
        f.exportFilmsToBinaryFiles();
    }
}
