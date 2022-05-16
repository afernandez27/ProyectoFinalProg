package prog.proyectofinalprog;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ControllerBienvenida {

    @FXML
    private Button bttnComprar;

    @FXML
    private Button bttnVender;

    @FXML
    private Button bttnPedidos;

    @FXML
    void comprarCoche(ActionEvent event) {
        try {
            Stage ventanaActual = (Stage) bttnComprar.getScene().getWindow();
            ventanaActual.close();
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("VistaCompra.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Compra");
            stage.show();



        } catch (IOException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void venderCoche(ActionEvent event) {
        try {
            Node source = (Node) event.getSource();
            Stage ventanaVieja = (Stage) source.getScene().getWindow();
            ventanaVieja.close();
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("VistaDatosVender.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Compra");
            stage.show();

        } catch (IOException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    @FXML
    void generarPedidos(ActionEvent event){
        File txt = null;
        File csv = null;
        File d = null;
        try {
            d = new File("Pedidos");
            if (!d.exists()){
                if (!d.mkdirs()){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setTitle("Error");
                    alert.setContentText("Error al crear el directorio");
                    alert.showAndWait();
                }
            }
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
            txt = new File(d.getPath() + "\\" + dtf.format(LocalDateTime.now()) + ".txt");
            csv = new File( d.getPath() + "\\" + dtf.format(LocalDateTime.now()) + ".csv");
            escribirTXT(txt);
            escribirCSV(csv);

        }catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void escribirCSV(File csv) {
        FileWriter fw;
        Connection conexion;
        try {
            if (!csv.exists()){
                fw = new FileWriter(csv,true);
                fw.write("[dni],[nombre],[apellido1],[apellido2],[numero_pedido],[fecha_pedido],[matricula],[nombre_marca],[nombre_modelo],[precio]\n");
            }else {
                fw = new FileWriter(csv,true);
            }

            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/concesionario","root","root");
            Statement statement = conexion.createStatement();
            ResultSet rs = statement.executeQuery("select a.dni,a.nombre,a.apellido1,a.apellido2,b.numero_pedido,b.fecha_pedido,d.matricula,e.nombre_marca,e.nombre_modelo,d.precio " +
                    "from cliente a inner join pedido b inner join linea_pedido c inner join coche d inner join modelo e" +
                    " on a.id=b.id_cliente and b.id=c.id_pedido and c.id_coche=d.id and d.id_modelo=e.id " +
                    "where d.estado='v';");

            while (rs.next()){
                String dni = rs.getString("dni");
                String nombre = rs.getString("nombre");
                String apellido1 = rs.getString("apellido1");
                String apellido2 = rs.getString("apellido2");
                int numeroPedido = rs.getInt("numero_pedido");
                Date fechaPedido = rs.getDate("fecha_pedido");
                String matricula = rs.getString("matricula");
                String nombreMarca = rs.getString("nombre_marca");
                String nombreModelo = rs.getString("nombre_modelo");
                int precio = rs.getInt("precio");
                String pedido = dni + "," + nombre + "," + apellido1 + "," + apellido2 + "," + numeroPedido + "," +
                        fechaPedido + "," + matricula + "," + nombreMarca + "," + nombreModelo + "," + precio + "\n";
                fw.write(pedido);
            }
            fw.flush();
            fw.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("No ha habido ningun error al escribir el archivo");
            alert.setTitle("Information");
            alert.show();
        }catch (IOException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText(e.getSQLState());
            alert.showAndWait();
        }
    }

    private void escribirTXT(File txt) {
        FileWriter fw;
        Connection conexion;
        try {
            fw = new FileWriter(txt,true);

            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/concesionario","root","root");
            Statement statement = conexion.createStatement();
            ResultSet rs = statement.executeQuery("select a.dni,a.nombre,a.apellido1,a.apellido2,b.numero_pedido,b.fecha_pedido,d.matricula,e.nombre_marca,e.nombre_modelo,d.precio " +
                    "from cliente a inner join pedido b inner join linea_pedido c inner join coche d inner join modelo e" +
                    " on a.id=b.id_cliente and b.id=c.id_pedido and c.id_coche=d.id and d.id_modelo=e.id " +
                    "where d.estado='v';");

            while (rs.next()){
                String dni = rs.getString("dni");
                String nombre = rs.getString("nombre");
                String apellido1 = rs.getString("apellido1");
                String apellido2 = rs.getString("apellido2");
                int numeroPedido = rs.getInt("numero_pedido");
                Date fechaPedido = rs.getDate("fecha_pedido");
                String matricula = rs.getString("matricula");
                String nombreMarca = rs.getString("nombre_marca");
                String nombreModelo = rs.getString("nombre_modelo");
                int precio = rs.getInt("precio");
                String linea="-----------------------------------------------------------\n";
                String pedido = linea + "DNI= " + dni + "\n" +
                        "Nombre cliente= " + nombre + "\n" +
                        "Primer Apellido= " + apellido1 + "\n" +
                        "Segundo Apellido= " + apellido2 + "\n" +
                        "NºPedido= " + numeroPedido + "\n" +
                        "Fecha del pedido= " + fechaPedido + "\n" +
                        "Matricula= " + matricula + "\n" +
                        "Nombre de la marca= " + nombreMarca + "\n" +
                        "Nombre del modelo= " + nombreModelo + "\n" +
                        "Precio= " + precio + "\n" + linea;
                fw.write(pedido);
            }
            fw.flush();
            fw.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("No ha habido ningun error al escribir el archivo");
            alert.setTitle("Information");
            alert.show();
        }catch (IOException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText(e.getSQLState());
            alert.showAndWait();
        }
    }

}
