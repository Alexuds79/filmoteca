package data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class Directores implements Serializable{
    //Atributos
    private String nombre, nacionalidad, ocupacion;
    private LocalDate fechaNac;
    private List<String> peliculas = new ArrayList<>();
    
    //Contructor
    public Directores(){
        this.nombre="";
        this.nacionalidad="";
        this.ocupacion="";
        this.fechaNac=LocalDate.of(1, Month.JANUARY, 1);
    }
    
    //Métodos
    public String exportStateWithDelimiters(String delim){
        String resultado;
        
        
        
        resultado = String.format("%s" //Nombre
                + "%s"
                + "%s" //Nacionalidad
                + "%s"
                + "%s" //Ocupacion
                + "%s"
                + "%s" //FechaNac
                + "%s"
                , this.nombre, delim, this.nacionalidad, delim, this.ocupacion, delim, this.fechaNac.toString(), delim);
        
        for(String s : peliculas){
            resultado = resultado.concat(s);
            resultado = resultado.concat("\t");
        }
        
        return resultado;
    }
    
    public String exportStateAsColumns(boolean flag){
        if (flag==false){
            return String.format("| %-25s | %-15s | %-20s | %-70s |",
                this.nombre, this.fechaNac.toString(), this.nacionalidad, this.ocupacion);
        }
        else{
            return String.format("| %-25s | %-15s | %-20s | %-70s | %-100s |",
                this.nombre, this.fechaNac.toString(), this.nacionalidad, this.ocupacion, this.peliculas.toString());
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

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public LocalDate getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(LocalDate fechaNac) {
        this.fechaNac = fechaNac;
    }

    public List<String> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(List<String> peliculas) {
        this.peliculas = peliculas;
    }
    
}
