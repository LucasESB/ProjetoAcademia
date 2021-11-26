package academia.utilitarios;

import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.text.NumberFormat;
import java.util.Locale;

public class Mascaras {



    public static void mascararCPF(TextField componente) {
        if (componente == null) {
            throw new RuntimeException("Camponente não pode ser nulo");
        }

        componente.setOnKeyTyped((KeyEvent event) -> {
            if (!("0123456789".contains(event.getCharacter()))) {
                event.consume();
            }

            if (event.getCharacter().trim().length() == 0) { // Apagando

                if (componente.getText().length() == 4) {
                    componente.setText(componente.getText().substring(0, 3));
                    componente.positionCaret(componente.getText().length());
                }
                if (componente.getText().length() == 8) {
                    componente.setText(componente.getText().substring(0, 7));
                    componente.positionCaret(componente.getText().length());
                }
                if (componente.getText().length() == 12) {
                    componente.setText(componente.getText().substring(0, 11));
                    componente.positionCaret(componente.getText().length());
                }

            } else { // Escrevendo

                if (componente.getText().length() == 14) event.consume();

                if (componente.getText().length() == 3) {
                    componente.setText(componente.getText() + ".");
                    componente.positionCaret(componente.getText().length());
                }
                if (componente.getText().length() == 7) {
                    componente.setText(componente.getText() + ".");
                    componente.positionCaret(componente.getText().length());
                }
                if (componente.getText().length() == 11) {
                    componente.setText(componente.getText() + "-");
                    componente.positionCaret(componente.getText().length());
                }

                maxField(componente, 14);
            }
        });

        componente.setOnKeyReleased((KeyEvent evt) -> {

            if (!componente.getText().matches("\\d.-*")) {
                componente.setText(componente.getText().replaceAll("[^\\d.-]", ""));
                componente.positionCaret(componente.getText().length());
            }
        });
    }

    /**
     * @param textField TextField.
     * @param length    Tamanho do campo.
     */
    public static void maxField(final TextField textField, final Integer length) {
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (newValue.length() > length)
                    textField.setText(oldValue);
            }
        });
    }

    public static void maxField(final DatePicker datePicker, final Integer length) {
        datePicker.getEditor().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (newValue.length() > length)
                    datePicker.getEditor().setText(oldValue);
            }
        });
    }

    public static void mascararTelefone(TextField componente) {

        componente.setOnKeyTyped((KeyEvent event) -> {
            if (!"0123456789".contains(event.getCharacter())) {
                event.consume();
            }

            if (event.getCharacter().trim().length() == 0) { // Apagando

                if (componente.getText().length() == 10 && componente.getText().substring(9, 10).equals("-")) {
                    componente.setText(componente.getText().substring(0, 9));
                    componente.positionCaret(componente.getText().length());
                }
                if (componente.getText().length() == 9 && componente.getText().substring(8, 9).equals("-")) {
                    componente.setText(componente.getText().substring(0, 8));
                    componente.positionCaret(componente.getText().length());
                }
                if (componente.getText().length() == 4) {
                    componente.setText(componente.getText().substring(0, 3));
                    componente.positionCaret(componente.getText().length());
                }
                if (componente.getText().length() == 1) {
                    componente.setText("");
                }

            } else { //Escrevendo

                if (componente.getText().length() == 14) event.consume();

                if (componente.getText().length() == 1 && !componente.getText().contains("(")) {
                    componente.setText("(" + event.getCharacter());
                    componente.positionCaret(componente.getText().length());
                    event.consume();
                }
                if (componente.getText().length() == 3) {
                    componente.setText(componente.getText() + ") ");
                    componente.positionCaret(componente.getText().length());
                    event.consume();
                }
                if (componente.getText().length() == 9) {
                    componente.setText(componente.getText() + "-");
                    componente.positionCaret(componente.getText().length());
                    event.consume();
                }
                if (componente.getText().length() == 10 && !componente.getText().substring(9, 10).equals("-")) {
                    System.out.println(componente.getText());
                    System.out.println(componente.getText().substring(9, 10).length());
                    componente.setText(componente.getText() + "-" + event.getCharacter());
                    componente.positionCaret(componente.getText().length());
                    event.consume();
                }
                if (componente.getText().length() == 14 && componente.getText().substring(9, 10).equals("-")) {
                    componente.setText(componente.getText().substring(0, 9) + componente.getText().substring(10, 11) + "-" + componente.getText().substring(11, 14));
                    componente.positionCaret(componente.getText().length());
                    event.consume();
                }

                maxField(componente, 15);
            }

        });

        componente.setOnKeyReleased((KeyEvent evt) -> {

            if (!componente.getText().matches("\\d()-*")) {
                componente.setText(componente.getText().replaceAll("[^\\d()-]", " "));
                componente.positionCaret(componente.getText().length());
            }
        });
    }

    public static void mascararData(TextField textField) {

        textField.setOnKeyTyped((KeyEvent event) -> {
            if ("0123456789".contains(event.getCharacter()) == false) {
                event.consume();
            }

            if (event.getCharacter().trim().length() == 0) { // Apagando

                if (textField.getText().length() == 3) {
                    textField.setText(textField.getText().substring(0, 2));
                    textField.positionCaret(textField.getText().length());
                }
                if (textField.getText().length() == 6) {
                    textField.setText(textField.getText().substring(0, 5));
                    textField.positionCaret(textField.getText().length());
                }

            } else { // Escrevendo

                if (textField.getText().length() == 10) event.consume();

                if (textField.getText().length() == 2) {
                    textField.setText(textField.getText() + "/");
                    textField.positionCaret(textField.getText().length());
                }
                if (textField.getText().length() == 5) {
                    textField.setText(textField.getText() + "/");
                    textField.positionCaret(textField.getText().length());
                }

                maxField(textField, 10);
            }
        });

        textField.setOnKeyReleased((KeyEvent evt) -> {

            if (!textField.getText().matches("\\d/*")) {
                textField.setText(textField.getText().replaceAll("[^\\d/]", ""));
                textField.positionCaret(textField.getText().length());
            }
        });

    }

    public static void mascararData(DatePicker datePicker) {

        datePicker.getEditor().setOnKeyTyped((KeyEvent event) -> {
            if ("0123456789".contains(event.getCharacter()) == false) {
                event.consume();
            }

            if (event.getCharacter().trim().length() == 0) { // Apagando
                if (datePicker.getEditor().getText().length() == 3) {
                    datePicker.getEditor().setText(datePicker.getEditor().getText().substring(0, 2));
                    datePicker.getEditor().positionCaret(datePicker.getEditor().getText().length());
                }
                if (datePicker.getEditor().getText().length() == 6) {
                    datePicker.getEditor().setText(datePicker.getEditor().getText().substring(0, 5));
                    datePicker.getEditor().positionCaret(datePicker.getEditor().getText().length());
                }

            } else { // Escrevendo

                if (datePicker.getEditor().getText().length() == 10) event.consume();

                if (datePicker.getEditor().getText().length() == 2) {
                    datePicker.getEditor().setText(datePicker.getEditor().getText() + "/");
                    datePicker.getEditor().positionCaret(datePicker.getEditor().getText().length());
                }
                if (datePicker.getEditor().getText().length() == 5) {
                    datePicker.getEditor().setText(datePicker.getEditor().getText() + "/");
                    datePicker.getEditor().positionCaret(datePicker.getEditor().getText().length());
                }

                maxField(datePicker, 10);
            }
        });

        datePicker.getEditor().setOnKeyReleased((KeyEvent evt) -> {

            if (!datePicker.getEditor().getText().matches("\\d/*")) {
                datePicker.getEditor().setText(datePicker.getEditor().getText().replaceAll("[^\\d/]", ""));
                datePicker.getEditor().positionCaret(datePicker.getEditor().getText().length());
            }
        });
    }

    public static void mascararNumeroInteiro(TextField textField) {

        textField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    public static void mascararDinheiro(TextField textField) {
        textField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            Platform.runLater(() -> {
                int lenght = textField.getText().length();
                textField.selectRange(lenght, lenght);
                textField.positionCaret(lenght);
            });
        });

        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                try {
                    if(newValue != null && !newValue.isEmpty()) {
                        String plainText = newValue.replaceAll("[^0-9]", "");

                        while(plainText.length() < 3) {
                            plainText = "0" + plainText;
                        }

                        StringBuilder builder = new StringBuilder(plainText);
                        builder.insert(plainText.length() - 2, ".");

                        Double valor = Double.parseDouble(builder.toString());

                        NumberFormat formato = NumberFormat.getCurrencyInstance(new Locale("pt","BR"));
                        textField.setText(formato.format(valor));
                    }
                }catch (Exception ex){
                    textField.setText(oldValue);
                }
            }
        });
    }
}
