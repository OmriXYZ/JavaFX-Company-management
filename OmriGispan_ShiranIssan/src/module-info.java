module OmriGispan_ShiranIssan {
	requires javafx.controls;
	requires java.desktop;
	requires javafx.graphics;
	requires javafx.base;
	

	opens application to javafx.graphics, javafx.fxml;
	opens Model;
}
