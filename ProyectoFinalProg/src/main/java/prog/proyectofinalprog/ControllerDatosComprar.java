package prog.proyectofinalprog;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Conexión con la base de datos
        Connection dbConnection = null;
        try {
            dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "root");
            Statement stmt = dbConnection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }


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
//            Coche c = new Coche(/*PARÁMETROS DEL COCHE (MARCA, MODELO, PRECIO)*/);

//            if (!this.coches.contains(c)) {
//                try {
                    // ELIMINAR EL REGISTRO SELECCIONADO DE LA BBDD
//                    String query = // Query para borrar el registro
//                }
//            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
        } finally {
            Stage stage = (Stage) this.bttnConfirmarCompra.getScene().getWindow();
            stage.close();
        }

    }

}

