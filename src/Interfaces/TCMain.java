package Interfaces;
import totalcross.sys.Settings;
import totalcross.ui.MainWindow;
import totalcross.ui.UIColors;
import totalcross.ui.gfx.Color;

public class TCMain extends MainWindow {
	public static final int addButId = 1;
	public static final int deleteButId = 2;
	public static final int updateButId = 3;
	public static final int detailsButId = 4;
	
	public TCMain() {
		super("CRUD", VERTICAL_GRADIENT);
	}

	// Initialize the user interface
	public void initUI() {

		UIColors.controlsBack = Color.BRIGHT;
		setBackColor(Color.BRIGHT);
		setUIStyle(Settings.Android);
		swap(new MainInterface());

	}

}
