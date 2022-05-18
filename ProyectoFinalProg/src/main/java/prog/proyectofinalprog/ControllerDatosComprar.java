package prog.proyectofinalprog;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
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

    public Connection dbConnection;

    private ObservableList<Coche> coches;

    CocheTabla ct;

    public void setCocheTabla(CocheTabla ct){
        this.ct=ct;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ct = ControllerComprar.getCocheTabla();
        this.txtMarca.setText(ct.getMarca());
        this.txtModelo.setText(ct.getModelo());
        this.txtPrecio.setText(String.valueOf(ct.getPrecio()));

    }

    @FXML
    void cancelarCompraCoche(ActionEvent event) {
        try{
            Stage viejaVentana = (Stage) this.bttnConfirmarCompra.getScene().getWindow();
            viejaVentana.close();
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("VistaCompra.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Compra");
            stage.show();
        }catch (IOException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void confirmarCompra(ActionEvent event) {
        try {
            // Datos de la persona
            String dni = this.txtDNI.getText();
            String nombre = this.txtNombre.getText();
            String apellido1 = this.txtApellido1.getText();
            String apellido2 = this.txtApellido2.getText();

            // Creamos la persona y el coche
            Persona p = new Persona(dni, nombre, apellido1, apellido2, true);

            if (comprobarDNI(p.getDni())) {
                Statement stmt = null;
                dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "root");

                // Actualiza el registro de la bbdd a vendido e inserta el cliente
                try {
                    stmt = dbConnection.createStatement();
                    actualizarCoche(stmt);
                    insertarCliente(p, stmt);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Error al introducir el DNI");
                alert.showAndWait();
                this.txtDNI.clear();
            }


        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Error con el formato de los números");
            alert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try{
                Stage viejaVentana = (Stage) this.bttnConfirmarCompra.getScene().getWindow();
                viejaVentana.close();
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("VistaCompra.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Compra");
                stage.show();
            }catch (IOException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }

        }

    }

    private void insertarCliente(Persona p, Statement stmt) throws SQLException {
        String seleccionarCliente = "SELECT id FROM concesionario.cliente where dni = '" + p.getDni() + "'";
        ResultSet resultadoIdCliente = stmt.executeQuery(seleccionarCliente);
        int idCliente = 0;

        if (!resultadoIdCliente.next()) {
            String insertarPersona = "INSERT INTO concesionario.cliente(dni, nombre, apellido1, apellido2) VALUES('" + p.getDni() + "', '" + p.getNombre() + "', '" + p.getApellido1() + "', '" + p.getApellido2() + "')";
            stmt.executeUpdate(insertarPersona);
            int id=idNuevoCliente(stmt,seleccionarCliente);
            String insertarPedido = "INSERT INTO concesionario.pedido(numero_pedido,fecha_pedido,id_cliente) values('" + numeroPedido(stmt) + "', now(), " + id +")";
        } else {
            while (resultadoIdCliente.next()) {
                idCliente = resultadoIdCliente.getInt("id");
                System.out.println(idCliente);
            }
        }
    }

    private int numeroPedido(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT count(id) as numero_pedidos from concesionario.pedido");
        int numeroPedidos = 0;
        while (rs.next()){
            numeroPedidos = rs.getInt("numero_pedidos");
        }
        return numeroPedidos+1;
    }

    private int idNuevoCliente(Statement statement,String select) throws SQLException {
        ResultSet rs = statement.executeQuery(select);
        int id = 0;
        while (rs.next()){
            id = rs.getInt("id");
        }
        return id;
    }


    private void actualizarCoche(Statement stmt) throws SQLException {
        String cocheUpdate = "UPDATE concesionario.coche SET estado = 'v' WHERE matricula = '" + ct.getMatricula() + "'";
        stmt.executeUpdate(cocheUpdate);
    }


    private boolean comprobarDNI(String dni) {
        return dni.matches("[0-9]{8}[A-Za-z]");
    }


}

