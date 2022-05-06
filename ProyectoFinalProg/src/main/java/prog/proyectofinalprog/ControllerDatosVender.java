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
import java.sql.*;
import java.util.ResourceBundle;

public class ControllerDatosVender implements Initializable {

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnConfirmarVender;

    @FXML
    private TextField txtPotencia;

    @FXML
    private TextField txtApellido1;

    @FXML
    private TextField txtApellido2;

    @FXML
    private TextField txtDNI;

    @FXML
    private TextField txtKm;

    @FXML
    private TextField txtMarca;

    @FXML
    private TextField txtMatricula;

    @FXML
    private TextField txtModelo;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtColor;

    @FXML
    private TextField txtPrecio;

    public Connection dbConnection;

    private ObservableList<Coche> coches;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//         Conexión con la base de datos
//        Connection dbConnection = null;
//        try {
//            dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "root");
//            Statement stmt = dbConnection.createStatement();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    @FXML
    void cancelar(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    void vender(ActionEvent event) {
        try {
            // Datos de la persona
            String nombre = this.txtNombre.getText();
            String dni = this.txtDNI.getText();
            String apellido1 = this.txtApellido1.getText();
            String apellido2 = this.txtApellido2.getText();

            // Creamos al vendedor con los datos correspondientes de la persona
            Persona p = new Persona(dni, nombre, apellido1, apellido2, false);

            // Datos del coche
            String matricula = this.txtMatricula.getText();
            String marca = this.txtMarca.getText();
            String modelo = this.txtModelo.getText();
            int kilometraje = Integer.parseInt(this.txtKm.getText());
            int potencia = Integer.parseInt(this.txtPotencia.getText());
            String color = this.txtColor.getText();
            int precio = Integer.parseInt(this.txtPrecio.getText());

            // Creamos el coche a partir de los anteriores datos
            Modelo m = new Modelo(marca, modelo);
            Coche c = new Coche(matricula, kilometraje, potencia, color, precio, m);

            Statement stmt = null;
//             Conexión con la base de datos para insertar el coche
//            Connection dbConnection;
            dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "root");
            try {
                stmt = dbConnection.createStatement();

                // Selecciona el modelo o lo crea si no existe
                String seleccionarModelo = "SELECT id FROM concesionario.modelo WHERE nombre_marca = '" + m.getMarca() + "' AND nombre_modelo = '" + m.getModelo() + "'";
                ResultSet resultadoIdModelo = stmt.executeQuery(seleccionarModelo);
                int idModelo = 0;
                if (!resultadoIdModelo.next()) {
                    String insertarModelo = "INSERT IGNORE INTO concesionario.modelo(nombre_marca, nombre_modelo) VALUES('" + m.getMarca() + "', '" + m.getModelo() + "')";
                    stmt.executeUpdate(insertarModelo);
                }
                while (resultadoIdModelo.next()) {
                    idModelo = resultadoIdModelo.getInt("id");
                    System.out.println(idModelo);
                }

                // Selecciona el vendedor o lo crea si no existe
                String seleccionarVendedor = "SELECT id FROM concesionario.vendedor WHERE dni = '" + p.getDni() + "'";
                ResultSet resultadoIdVendedor = stmt.executeQuery(seleccionarVendedor);
                int idVendedor = 0;
                while (resultadoIdVendedor.next()) {
                    idVendedor = resultadoIdVendedor.getInt("id");
                    System.out.println(idVendedor);
                }


                String insertarCoche = "INSERT IGNORE INTO concesionario.coche(matricula, kilometraje, potencia, color, precio, id_vendedor, id_modelo) VALUES('" + c.getMatricula() + "', " + c.getKilometraje() + ", " + c.getPotencia() + ", '" + c.getColor() + "', " + c.getPrecio() + ", " + idVendedor + ", " + idModelo + ")";
                stmt.executeUpdate(insertarCoche);
                System.out.println("Coche insertado");
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                stmt.close();
            }

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Formato de los números incorrecto");
            alert.showAndWait();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Stage stage = (Stage) this.btnConfirmarVender.getScene().getWindow();
            stage.close();
        }
    }

}
