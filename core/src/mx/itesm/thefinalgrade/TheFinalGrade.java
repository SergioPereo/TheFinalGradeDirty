package mx.itesm.thefinalgrade;

import com.badlogic.gdx.Game;
import mx.itesm.thefinalgrade.menus.StartMenu;

public class TheFinalGrade extends Game {

	@Override
	public void create() {
		setScreen(new StartMenu(this,"Fondo_StartMenu.png"));
	}

	@Override
	public void dispose() {
		super.dispose();
	}
}
