module prog.proyectofinalprog {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens prog.proyectofinalprog to javafx.fxml;
    exports prog.proyectofinalprog;
}