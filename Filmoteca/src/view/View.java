package view;

import com.coti.tools.Esdia;
import controller.Controller;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class View implements Serializable{
    Controller c = new Controller();
    Scanner sc = new Scanner(System.in);
    
    public void runMenu(String menu){
        String opcion;
        boolean salir = false;
        String[] availableOp = {"1", "2", "3", "4", "5", "q"};
        
        do{
            opcion = Esdia.readString(menu, availableOp);
            
            switch(opcion){
                case "1": subMenuArchivos("1. Exportar Directores a Encolumnado\n2. Exportar Películas a HTML\n"); break;
                case "2": subMenuPeliculas("1. Altas\n2. Bajas\n3. Modificaciones\n4. Consulta\n"); break;
                case "3": subMenuDirectores("1. Altas\n2. Bajas\n3. Modificaciones\n"); break;
                case "4": subMenuActores("1. Altas\n2. Bajas\n3. Modificaciones\n4. Películas\n"); break;
                case "5": subMenuListados("1. Peliculas\n2. Directores\n3. Actores\n"); break;
                case "q": salir = Esdia.yesOrNo("¿Seguro que quieres salir de la aplicación?"); break;
            }
        }while(!salir);
    }
    
    public void compruebaFicheros(){
        c.compruebaFicheros();
    }
    
    public void exportToBinaryFiles(){
        c.exportToBinaryFiles();
    }
    
    public void subMenuArchivos(String menu){
        String opcion;
        boolean salir = false;
        String[] availableOp = {"1", "2", "q"};
        
        do{
            opcion = Esdia.readString(menu, availableOp);
            
            switch(opcion){
                case "1": exportDirToCol(); break;
                case "2": exportFilmsToHTML(); break;
                case "q": salir = Esdia.yesOrNo("¿Seguro que quieres salir al menú principal?"); break;
            }
        }while(!salir);
    }
    
    public void exportDirToCol(){
        c.exportDirToCol();
        System.out.println("\nArchivo directores.col creado...\n\n");
    }
    
    public void exportFilmsToHTML(){
        c.exportFilmsToHTML();
        System.out.println("\nArchivo peliculas.html creado...\n\n");
    }
    
    public void subMenuActores(String menu){
        String opcion;
        boolean salir = false;
        String[] availableOp = {"1", "2", "3", "4", "q"};
        
        do{
            opcion = Esdia.readString(menu, availableOp);
            
            switch(opcion){
                case "1": altasActores(); break;
                case "2": bajasActores(); break;
                case "3": modificaActores("1. Fecha de Nacimiento\n2. Nacionalidad\n3. Año de Debut\n"); break;
                case "4": consultaPeliculasActores(); break;
                case "q": salir = Esdia.yesOrNo("¿Seguro que quieres salir al menú principal?"); break;
            }
        }while(!salir);
    }
    
    public void altasActores(){
        String nombre, nacionalidad;
        int anioNac, mesNac, diaNac, anioDebut;
        LocalDate fechaNac;
        List<String> peliculas = new ArrayList<>();
        String[] auxPel;
        
        System.out.print("Nombre: ");
        nombre = sc.nextLine();
        
        anioNac = Esdia.readInt("Año de Nacimiento: ", 0, 2019);
        mesNac = Esdia.readInt("Mes de Nacimiento: ", 1, 12);
        diaNac = Esdia.readInt("Día de Nacimiento: ", 1, 31);
        fechaNac = LocalDate.of(anioNac, mesNac, diaNac);
        
        System.out.print("Nacionalidad: ");
        nacionalidad = sc.nextLine();
        
        anioDebut = Esdia.readInt("Año de Debut: ", 0, 2019);
        
        System.out.print("Películas (separe los películas por coma+espacio): ");
        String s = sc.nextLine();
        auxPel = s.split(", ");
        peliculas.addAll(Arrays.asList(auxPel));
        
        c.addUnActor(nombre, fechaNac, nacionalidad, anioDebut, peliculas);
    }
    
    public void bajasActores(){
        System.out.print("Indique el nombre del Actor a suprimir: ");
        String nombre = sc.nextLine();
        
        c.removeUnActor(nombre);
    }
    
    public void modificaActores(String menu){
        System.out.print("Seleccione el nombre del Actor a modificar: ");
        String nombre = sc.nextLine();
        
        c.modificaActor(nombre, menu);
    }
    
    public void consultaPeliculasActores(){
        c.sortByFilmsAnio(); //Ordenamos todas las peliculas
        
        System.out.print("Seleccione el nombre del Actor a consultar: ");
        String nombre = sc.nextLine();
        
        String[] listadoFilms = c.obtenerListadoPeliculasAutor(nombre);
        
        
        if(listadoFilms == null){
            System.err.println("\nEl listado está vacío");
        }
        
        else{
            System.out.println("\n\nListado de Películas\n");
            System.out.printf("| %-50s | %6s | %10s | %-30s | %-20s |\n", "TÍTULO", "AÑO", "DURACIÓN", "PAÍS", "GÉNERO");
        
            for(String s : listadoFilms){
                System.out.println(s);
            }
        
            System.out.println();
        }
        
    }
    
    public void subMenuDirectores(String menu){
        String opcion;
        boolean salir = false;
        String[] availableOp = {"1", "2", "3", "q"};
        
        do{
            opcion = Esdia.readString(menu, availableOp);
            
            switch(opcion){
                case "1": altasDirectores(); break;
                case "2": bajasDirectores(); break;
                case "3": modificaDirectores("1. Fecha de Nacimiento\n2. Nacionalidad\n3. Ocupación\n"); break;
                case "q": salir = Esdia.yesOrNo("¿Seguro que quieres salir al menú principal?"); break;
            }
        }while(!salir);
    }
    
    public void altasDirectores(){
        String nombre, nacionalidad, ocupacion;
        int anioNac, mesNac, diaNac;
        LocalDate fechaNac;
        List<String> peliculas = new ArrayList<>();
        String[] auxPel;
        boolean flag=false;
        
        System.out.print("Nombre: ");
        nombre = sc.nextLine();
        
        if(flag==false){
            anioNac = Esdia.readInt("Año de Nacimiento: ", 0, 2019);
            mesNac = Esdia.readInt("Mes de Nacimiento: ", 1, 12);
            diaNac = Esdia.readInt("Día de Nacimiento: ", 1, 31);
            fechaNac = LocalDate.of(anioNac, mesNac, diaNac);
        
            System.out.print("Nacionalidad: ");
            nacionalidad = sc.nextLine();
        
            System.out.print("Ocupación: ");
            ocupacion = sc.nextLine();
        
            System.out.print("Películas (separe los películas por coma+espacio): ");
            String s = sc.nextLine();
            auxPel = s.split(", ");
            peliculas.addAll(Arrays.asList(auxPel));
        
            c.addUnDirector(nombre, fechaNac, nacionalidad, ocupacion, peliculas);
        }
        
    }
    
    public void bajasDirectores(){
        System.out.print("Indique el nombre del Director a suprimir: ");
        String nombre = sc.nextLine();
        
        c.removeUnDirector(nombre);
    }
    
    public void modificaDirectores(String menu){
        System.out.print("Seleccione el nombre del Director a modificar: ");
        String nombre = sc.nextLine();
        
        c.modificaDirector(nombre, menu);
    }
    
    public void subMenuPeliculas(String menu){
        String opcion;
        boolean salir = false;
        String[] availableOp = {"1", "2", "3", "4", "q"};
        
        do{
            opcion = Esdia.readString(menu, availableOp);
            
            switch(opcion){
                case "1": altasPeliculas(); break;
                case "2": bajasPeliculas(); break;
                case "3": modificaPeliculas("\n1. Año\n2. Duración\n3. País\n4. Guion\n5. Música\n6. Fotografía\n7. Productora\n8. Género\n9. Sinopsis\n"); break;
                case "4": consultaPeliculas(); break;
                case "q": salir = Esdia.yesOrNo("¿Seguro que quieres salir al menú principal?"); break;
            }
        }while(!salir);
    }
    
    public void altasPeliculas(){
        String titulo, pais, guion, musica, foto, productora, genero, sinopsis;
        int anio, duracion;
        List<String> direccion = new ArrayList<>();
        List<String> reparto = new ArrayList<>();
        String[] auxDir, auxAct;
        
        System.out.print("Titulo: ");
        titulo = sc.nextLine();
        anio = Esdia.readInt("Año: ");
        duracion = Esdia.readInt("Duración: ");
        System.out.print("Pais: ");
        pais = sc.nextLine();
        
        System.out.print("Direccion (separe los directores por coma+espacio): ");
        String s = sc.nextLine();
        auxDir = s.split(", ");
        direccion.addAll(Arrays.asList(auxDir));
        
        System.out.print("Guion: ");
        guion = sc.nextLine();
        System.out.print("Música: ");
        musica = sc.nextLine();
        System.out.print("Fotografía: ");
        foto = sc.nextLine();
        
        System.out.print("Reparto (separe los actores por coma+espacio): ");
        s = sc.nextLine();
        auxAct = s.split(", ");
        reparto.addAll(Arrays.asList(auxAct));
        
        System.out.print("Productora: ");
        productora = sc.nextLine();
        System.out.print("Género: ");
        genero = sc.nextLine();
        System.out.print("Sinopsis: ");
        sinopsis = sc.nextLine();
        
        c.addUnaPelicula(titulo, anio, duracion, pais, direccion, guion, musica, foto, reparto, productora, genero, sinopsis);
    }
    
    public void bajasPeliculas(){
        String titulo;
        
        System.out.print("Indique el titulo de la película a borrar: ");
        titulo = sc.nextLine();
        
        c.removeUnaPelicula(titulo);
    }
    
    public void modificaPeliculas(String menu){
        System.out.print("Seleccione el título de la película a modificar: ");
        String titulo = sc.nextLine();
        
        c.modificaPeliculas(titulo, menu);
    }
    
    public void consultaPeliculas(){
        System.out.print("Ingrese el título de la película a consultar: ");
        String titulo = sc.nextLine();
        
        c.consultaPelicula(titulo);
    }
    
    public void subMenuListados(String menu){
        String opcion;
        boolean salir = false;
        String[] availableOp = {"1", "2", "3", "q"};
        
        do{
            opcion = Esdia.readString(menu, availableOp);
            
            switch(opcion){
                case "1": muestraPeliculas(); break;
                case "2": muestraDirectores(); break;
                case "3": muestraActores(); break;
                case "q": salir = Esdia.yesOrNo("¿Seguro que quieres salir al menú principal?"); break;
            }
        }while(!salir);
    }
    
    public void muestraActores(){
        c.sortByActors();
        
        boolean flag;
        flag = Esdia.yesOrNo("¿Desea ver las películas de cada actor?");
        
        String[] listadoActores = c.obtenerListadoActores(flag);
        
        if(listadoActores == null){
            System.err.println("\n\nError: el listado está vacío\n\n");
        }
        
        else{
            System.out.println("\n\nListado de Actores\n");
            if(flag==false) System.out.printf("| %-25s | %-15s | %-20s | %12s |\n", "NOMBRE", "F.NACIMIENTO", "NACIONALIDAD", "AÑO DEBUT");
            else System.out.printf("| %-25s | %-15s | %-20s | %12s | %-100s |\n", "NOMBRE", "F.NACIMIENTO", "NACIONALIDAD", "AÑO DEBUT", "PELÍCULAS");

            for(String s : listadoActores){
                System.out.println(s);
            }

            System.out.println();
        }
    }
    
    public void muestraDirectores(){
        c.sortByDirectors();
        
        boolean flag;
        flag = Esdia.yesOrNo("¿Desea ver las películas de cada director?");
        
        String[] listadoDirectores = c.obtenerListadoDirectores(flag);
        
        if(listadoDirectores == null){
            System.err.println("\n\nError: el listado está vacío\n\n");
        }
        
        else{
            System.out.println("\n\nListado de Directores\n");
            if(flag==false) System.out.printf("| %-25s | %-15s | %-20s | %-70s |\n", "NOMBRE", "F.NACIMIENTO", "NACIONALIDAD", "OCUPACIÓN");
            else System.out.printf("| %-25s | %-15s | %-20s | %-70s | %-100s |\n", "NOMBRE", "F.NACIMIENTO", "NACIONALIDAD", "OCUPACIÓN", "PELÍCULAS");

            for(String s : listadoDirectores){
                System.out.println(s);
            }

            System.out.println();
        }
    }
    
    public void muestraPeliculas(){
        c.sortByFilms();
        
        String[] listadoFilms = c.obtenerListadoFilms();
        
        if(listadoFilms == null){
            System.err.println("\n\nError: el listado está vacío\n\n");
        }
        
        else{
            System.out.println("\n\nListado de Películas\n");
            System.out.printf("| %-50s | %6s | %10s | %-30s | %-20s |\n", "TÍTULO", "AÑO", "DURACIÓN", "PAÍS", "GÉNERO");

            for(String s : listadoFilms){
                System.out.println(s);
            }

            System.out.println();
        }
    }
}
