package prog.proyectofinalprog;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerComprar implements Initializable {

    @FXML
    private Button bttnCerrar;

    @FXML
    private Button bttnComp;

    @FXML
    private Button bttnInfo;

    @FXML
    private TableView<CocheTabla> tblComprar;

    @FXML
    private TableColumn<CocheTabla, String> tblcMarca;

    @FXML
    private TableColumn<CocheTabla, String> tblcModelo;

    @FXML
    private TableColumn<CocheTabla, Integer> tblcPrecio;

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
        CocheTabla ct = this.tblComprar.getSelectionModel().getSelectedItem();

        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("VistaMasInfo.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Compra");
            ControllerMasInfo controllerMasInfo = new ControllerMasInfo();
            controllerMasInfo.setCocheTabla(ct);
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
    void cerrar(ActionEvent event) {
        try {
            Stage viejaVentana = (Stage) bttnCerrar.getScene().getWindow();
            viejaVentana.close();
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("VistaBienvenida.fxml"));
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.tblcMarca.setCellValueFactory(new PropertyValueFactory("marca"));
        this.tblcModelo.setCellValueFactory(new PropertyValueFactory("modelo"));
        this.tblcPrecio.setCellValueFactory(new PropertyValueFactory("precio"));
        Coche c = new Coche();
        ObservableList<CocheTabla> obs = c.getCoches();
        this.tblComprar.setItems(obs);
    }
}
