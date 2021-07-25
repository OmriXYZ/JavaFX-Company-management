package helpingmethods;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
        
        Label lbl = new Label("Would you like to load recent data?");
        ButtonType btnYes = new ButtonType("Yes");
        ButtonType btnNo = new ButtonType("No");

        
        gridPane.add(lbl, 0, 0);

        getDialogPane().getButtonTypes().addAll(btnNo, btnYes);
        getDialogPane().setContent(gridPane);

        setResultConverter(new Callback<ButtonType, String>() {
            @Override
			public String call(ButtonType param) {
				if (param == btnYes) 
					return "yes";
				else
					return "no";

			}
        });
        
    }
}
