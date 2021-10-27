module Academia {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;

    opens academia;
    opens academia.controle;

    exports academia.controle;
}