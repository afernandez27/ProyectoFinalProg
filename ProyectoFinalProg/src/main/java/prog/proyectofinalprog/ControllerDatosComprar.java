package prog.proyectofinalprog;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ControllerDatosComprar implements Initializable {

    @FXML
    private Button bttnCancelarCompra;

    @FXML
    private Button bttnConfirmarCompra;

    @FXML
    private TextField txtApellido1;

    @FXML
    private TextField txtApellido2;

    @FXML
    private TextField txtDNI;

    @FXML
    private TextField txtMarca;

    @FXML
    private TextField txtModelo;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtPrecio;

    private ObservableList<Coche> coches;

    CocheTabla ct;

    public void setCocheTabla(CocheTabla ct){
        this.ct=ct;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.txtMarca.setText(ct.getMarca());
        this.txtModelo.setText(ct.getModelo());
        this.txtPrecio.setText(String.valueOf(ct.getPrecio()));

    }

    @FXML
    void cancelarCompraCoche(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    void confirmarCompra(ActionEvent event) {
        try {
            // Datos de la persona
            String dni = this.txtDNI.getText();
            String nombre = this.txtNombre.getText();
            String apellido1 = this.txtApellido1.getText();
            String apellido2 = this.txtApellido2.getText();

            // Datos del coche
            String marca = this.txtMarca.getText();
            String modelo = this.txtModelo.getText();
            int precio = Integer.parseInt(this.txtPrecio.getText());

            // Creamos la persona y el coche
            Persona p = new Persona(dni, nombre, apellido1, apellido2, true);
            CocheTabla c = new CocheTabla(marca, modelo, precio);

//            if (!this.coches.contains(c)) {
//                try {
                    // ELIMINAR EL REGISTRO SELECCIONADO DE LA BBDD
//                    String query = // Query para borrar el registro
//                }
//            }

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Error con el formato de los números");
            alert.showAndWait();
        } finally {
            Stage stage = (Stage) this.bttnConfirmarCompra.getScene().getWindow();
            stage.close();
        }

    }

}

