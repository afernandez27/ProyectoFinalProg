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

import java.io.IOException;

public class ControllerBienvenida {

    @FXML
    private Button bttnComprar;

    @FXML
    private Button bttnVender;

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
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("VistaDatosVender.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Compra");
        stage.showAndWait();

    } catch (IOException e){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }
    }

}
