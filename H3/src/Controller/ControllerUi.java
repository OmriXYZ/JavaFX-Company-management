package Controller;

import Model.Messageable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class ControllerUi implements Messageable {

	@Override
	public void showMsg(String msg) {
		Alert alert = new Alert(AlertType.NONE, msg, ButtonType.CLOSE);
		alert.show();
	}

	@Override
	public void showErrMsg(String msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getMsg(String msg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getErrMsg(String msg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void cleanBuffer() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getInt(String msg) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getInt() {
		// TODO Auto-generated method stub
		return 0;
	}

}
