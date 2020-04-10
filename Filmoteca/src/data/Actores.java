package data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class Actores implements Serializable{
    //Atributos
    private String nombre, nacionalidad;
    private LocalDate fechaNac;
    private int anioDebut;
    private List<String> peliculas = new ArrayList<>();
    
    //Constructor
    public Actores(){
        this.nombre="";
        this.fechaNac=LocalDate.of(1, Month.JANUARY, 1);
        this.nacionalidad="";
        this.anioDebut=0;
    }
    
    //Metodos
    public String exportStateAsColumns(boolean flag){
        if (flag==false){
            return String.format("| %-25s | %-15s | %-20s | %12d |",
                this.nombre, this.fechaNac.toString(), this.nacionalidad, this.anioDebut);
        }
        else{
            return String.format("| %-25s | %-15s | %-20s | %12d | %-100s |",
                this.nombre, this.fechaNac.toString(), this.nacionalidad, this.anioDebut, this.peliculas.toString());
        }
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public LocalDate getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(LocalDate fechaNac) {
        this.fechaNac = fechaNac;
    }

    public int getAnioDebut() {
        return anioDebut;
    }

    public void setAnioDebut(int anioDebut) {
        this.anioDebut = anioDebut;
    }

    public List<String> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(List<String> peliculas) {
        this.peliculas = peliculas;
    }
    
}
