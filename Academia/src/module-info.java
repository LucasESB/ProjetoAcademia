module Academia {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;

    opens academia;
    opens academia.controle;
    opens academia.entidades;

    exports academia.controle;
}