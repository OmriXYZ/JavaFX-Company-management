package Model;

public interface Messageable {
	void showMsg(String msg);
	void showErrMsg(String msg);
	String getMsg(String msg);
	String getErrMsg(String msg);
	void cleanBuffer();
	int getInt(String msg);
	int getInt();

}
