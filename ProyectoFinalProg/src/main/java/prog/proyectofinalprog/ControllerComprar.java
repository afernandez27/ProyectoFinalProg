package prog.proyectofinalprog;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerComprar {


    @FXML
    private Button bttnComp;

    @FXML
    private Button bttnInfo;

    @FXML
    private TableView<?> tblComprar;

    @FXML
    private TableColumn<?, ?> tblcMarca;

    @FXML
    private TableColumn<?, ?> tblcModelo;

    @FXML
    private TableColumn<?, ?> tblcPrecio;

    @FXML
    void compra(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("VistaDatosCompra.fxml"));
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

    @FXML
    void masInfo(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("VistaMasInfo.fxml"));
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
