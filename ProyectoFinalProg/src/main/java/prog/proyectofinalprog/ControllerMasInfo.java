package prog.proyectofinalprog;

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

public class ControllerMasInfo implements Initializable {

    @FXML
    private Button bttnCerrar;

    @FXML
    private TextField txtMatricula;

    @FXML
    private TextField txtColor;

    @FXML
    private TextField txtKm;

    @FXML
    private TextField txtMarca;

    @FXML
    private TextField txtModelo;

    @FXML
    private TextField txtPotencia;

    @FXML
    private TextField txtPrecio;
    CocheTabla ct;
    @FXML
    void cerrar(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    public void setCocheTabla(CocheTabla ct){
        this.ct=ct;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Coche c = getCoche(ct);
        this.txtColor.setText(c.getColor());
        this.txtKm.setText(String.valueOf(c.getKilometraje()));
        this.txtMatricula.setText(c.getMatricula());
        this.txtMarca.setText(c.getModelo().getMarca());
        this.txtModelo.setText(c.getModelo().getModelo());
        this.txtPotencia.setText(String.valueOf(c.getPotencia()));
        this.txtPrecio.setText(String.valueOf(c.getPrecio()));
    }

    private Coche getCoche(CocheTabla ct) {
        Connection conexion;
        Coche c=null;
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/concesionario","root","root");
            Statement statement = conexion.createStatement();
            ResultSet rs = statement.executeQuery("select * from coche");

            while (rs.next()){
                String matricula = rs.getString("matricula");
                int kilometraje = rs.getInt("kilometraje");
                int potencia = rs.getInt("potencia");
                String color = rs.getString("color");
                int precio = rs.getInt("precio");
                int idModelo = rs.getInt("id_modelo");
                Modelo m = Coche.getModelo(idModelo);
                c = new Coche(matricula,kilometraje,potencia,color,precio,m);
            }
            conexion.close();
        } catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        return c;
    }
}
