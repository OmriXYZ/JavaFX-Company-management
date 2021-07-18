package helpingmethods;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.StageStyle;
import javafx.util.Callback;


public class CustomDialog extends Dialog<String>
{
    String result = "";

    public CustomDialog()
    {
        super();
        initStyle(StageStyle.DECORATED);

        setContentText(null);
        setHeaderText(null);
        
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));
        
        Label lblCompName = new Label("Enter your company name:");
        TextField nameField = new TextField();
        ButtonType buttonOne = new ButtonType("Confirm");
        
        gridPane.add(lblCompName, 0, 0);
        gridPane.add(nameField, 0, 1);

        getDialogPane().getButtonTypes().addAll(buttonOne);
        getDialogPane().setContent(gridPane);

        setResultConverter(new Callback<ButtonType, String>()
        {
            @Override
            public String call(ButtonType param)
            {
                if (param == buttonOne && !nameField.getText().isEmpty()) {
                    return nameField.getText();
                }
                
                return "";
            }
        });
    }
}
