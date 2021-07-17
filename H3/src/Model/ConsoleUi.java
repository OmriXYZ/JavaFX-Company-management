package Model;

import java.util.Scanner;

public class ConsoleUi implements Messageable {
	private Scanner s = new Scanner(System.in);

	@Override
	public void showMsg(String msg) {
		System.out.println(msg);
	}

	@Override
	public String getMsg(String msg) {
		System.out.println(msg);
		return s.nextLine();
	}

	@Override
	public void cleanBuffer() {
		s.nextLine();
	}

	@Override
	public int getInt(String msg) {
		System.out.println(msg);
		return s.nextInt();
	}

	@Override
	public int getInt() {
		return Integer.parseInt(s.nextLine());
	}

	@Override
	public String getErrMsg(String msg) {
		System.err.println(msg);
		return s.nextLine();
	}

	@Override
	public void showErrMsg(String msg) {
		System.err.println(msg);		
	}

}
