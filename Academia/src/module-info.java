module Academia {
    requires javafx.fxml;
    requires javafx.controls;

    opens academia;
    opens academia.controle;

    exports academia.controle;
}