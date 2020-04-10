package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Peliculas implements Serializable{
    //Atributos
    private String titulo, pais, guion, musica, fotografia, productora, genero, sinopsis;
    private int anio, duracion;
    private List<String> direccion = new ArrayList<>();
    private List<String> reparto = new ArrayList<>();
    
    //Constrcutor
    public Peliculas(){
        this.anio=0;
        this.duracion=0;
        this.fotografia="";
        this.genero="";
        this.guion="";
        this.musica="";
        this.pais="";
        this.productora="";
        this.sinopsis="";
        this.titulo="";
    }

    //Métodos
    public String asTableRow(){
        String resultado;
        resultado = String.format("<table width=3000>" +
                                  "<tr>" +
                                  "<td width=150 align=\"center\"><font size=3>%s</font></td>" + //Titulo
                                  "<td width=50 align=\"center\"><font size=3>%d</font></td>" + //Año
                                  "<td width=50 align=\"center\"><font size=3>%s</font></td>" + //Duración
                                  "<td width=100 align=\"center\"><font size=3>%s</font></td>" + //País
                                  "<td width=300 align=\"center\"><font size=3>%s</font></td>" + //Direccion
                                  "<td width=250 align=\"center\"><font size=3>%s</font></td>" + //Guion
                                  "<td width=100 align=\"center\"><font size=3>%s</font></td>" + //Música
                                  "<td width=100 align=\"center\"><font size=3>%s</font></td>" + //Fotografía
                                  "<td width=300 align=\"center\"><font size=3>%s</font></td>" + //Reparto
                                  "<td width=300 align=\"center\"><font size=3>%s</font></td>" + //Productora
                                  "<td width=100 align=\"center\"><font size=3>%s</font></td>" + //Género
                                  "<td width=500 align=\"center\"><font size=3>%s</font></td>" + //Sinopsis
                                  "</tr>" +
                                  "</table>"
        ,this.titulo, this.anio, this.duracion, this.pais, this.direccion.toString(), this.guion, this.musica, this.fotografia,
        this.reparto.toString(), this.productora, this.genero, this.sinopsis);
        
        return resultado;
    }
    
    public String exportStateAsColumns(){
        return String.format("| %-50s | %6d | %10d | %-30s | %-20s |",
                this.titulo, this.anio, this.duracion, this.pais, this.genero);
    }
    
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getGuion() {
        return guion;
    }

    public void setGuion(String guion) {
        this.guion = guion;
    }

    public String getMusica() {
        return musica;
    }

    public void setMusica(String musica) {
        this.musica = musica;
    }

    public String getFotografia() {
        return fotografia;
    }

    public void setFotografia(String fotografia) {
        this.fotografia = fotografia;
    }

    public String getProductora() {
        return productora;
    }

    public void setProductora(String productora) {
        this.productora = productora;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public List<String> getDireccion() {
        return direccion;
    }

    public void setDireccion(List<String> direccion) {
        this.direccion = direccion;
    }

    public List<String> getReparto() {
        return reparto;
    }

    public void setReparto(List<String> reparto) {
        this.reparto = reparto;
    }
    
    
}