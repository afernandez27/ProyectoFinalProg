package prog.proyectofinalprog;

import java.sql.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.util.Objects;

public class Coche {
    private String matricula;
    private int kilometraje;
    private int potencia;
    private String color;
    private int precio;
    private Modelo modelo;

    public Coche() {
    }

    public Coche(String matricula, int kilometraje, int potencia, String color, int precio, Modelo modelo) {
        this.matricula = matricula;
        this.kilometraje = kilometraje;
        this.potencia = potencia;
        this.color = color;
        this.precio = precio;
        this.modelo = modelo;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public int getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(int kilometraje) {
        this.kilometraje = kilometraje;
    }

    public int getPotencia() {
        return potencia;
    }

    public void setPotencia(int potencia) {
        this.potencia = potencia;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coche coche = (Coche) o;
        return kilometraje == coche.kilometraje && potencia == coche.potencia && precio == coche.precio && Objects.equals(matricula, coche.matricula) && Objects.equals(color, coche.color) && Objects.equals(modelo, coche.modelo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matricula, kilometraje, potencia, color, precio, modelo);
    }

    public ObservableList<CocheTabla> getCoches(){
        ObservableList<CocheTabla> arrCoches = FXCollections.observableArrayList();
        Connection conexion;
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/concesionario","root","root");
            Statement statement = conexion.createStatement();
            ResultSet rs = statement.executeQuery("select * from coche");

            while (rs.next()){
                int precio = rs.getInt("precio");
                int idModelo = rs.getInt("id_modelo");
                Modelo modelo = getModelo(idModelo);

                CocheTabla ct = new CocheTabla(modelo.getMarca(), modelo.getModelo(), precio);
                arrCoches.add(ct);
            }
            conexion.close();
        } catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        return arrCoches;
    }
    public static Modelo getModelo(int id){
        Connection conexion;
        Modelo m=null;
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/concesionario","root","root");
            Statement statement = conexion.createStatement();
            ResultSet rs = statement.executeQuery("select * from modelo where id=" + id);

            while (rs.next()){
                String marca = rs.getString("nombre_marca");
                String modelo = rs.getString("nombre_modelo");
                m = new Modelo(marca,modelo);
            }
            conexion.close();
        }catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        return m;
    }
}
