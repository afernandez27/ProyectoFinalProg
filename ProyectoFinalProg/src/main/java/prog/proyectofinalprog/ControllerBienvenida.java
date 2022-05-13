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
            d = new File("C:\\Users\\"+ System.getProperty("user") +"\\Pedidos");
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
            txt = new File("C:\\Users\\" + System.getProperty("user")+"\\Pedidos\\Pedidos" + dtf.format(LocalDateTime.now()) + ".txt");
            csv = new File("C:\\Users\\" + System.getProperty("user")+"\\Pedidos\\Pedidos" + dtf.format(LocalDateTime.now()) + ".csv");
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
        FileWriter fw = null;
        try {
            fw = new FileWriter(csv);
            
        }catch (IOException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void escribirTXT(File txt) {
    }

}
