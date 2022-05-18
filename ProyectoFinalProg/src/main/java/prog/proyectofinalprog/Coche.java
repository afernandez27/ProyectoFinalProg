package prog.proyectofinalprog;

import java.sql.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.util.Objects;

// Constructores de la clase Coche
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
    //Getters y setters
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
    //Hashcode y equals para diferenciar los objetos
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

    //Metodo para llenar la tabla del controller comprar
    public ObservableList<CocheTabla> getCoches(){
        ObservableList<CocheTabla> arrCoches = FXCollections.observableArrayList();
        Connection conexion;
        //Hacemos la conexion, creamos el objeto y añadimos el objeto a la tabla
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/concesionario","root","root");
            Statement statement = conexion.createStatement();
            ResultSet rs = statement.executeQuery("select precio, matricula, id_modelo from coche where estado='d'");

            while (rs.next()){
                int precio = rs.getInt("precio");
                String matricula = rs.getString("matricula");
                int idModelo = rs.getInt("id_modelo");
                Modelo modelo = getModelo(idModelo);

                CocheTabla ct = new CocheTabla(matricula,modelo.getMarca(), modelo.getModelo(), precio);
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
    //recuperamos la marca y el modelo a partir del id
    public static Modelo getModelo(int id){
        Connection conexion;
        Modelo m=null;
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/concesionario","root","root");
            Statement statement = conexion.createStatement();
            ResultSet rs = statement.executeQuery("select nombre_marca, nombre_modelo from modelo where id=" + id);

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
