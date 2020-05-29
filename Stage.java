	import java.awt.Dimension;
import java.awt.Toolkit;


public interface Stage {
	
	Dimension Screen = Toolkit.getDefaultToolkit().getScreenSize(); //Screen dimensions will depend on the computerScreenSize(influences the size of everything)
	//Screen based constants
	int WIDTH = (int)Math.round(Screen.getWidth());	//sets the window size for the game
	int HEIGHT = (int)Math.round(Screen.getHeight());
	int positionIncrement = WIDTH / 15;
	int resizeX = WIDTH / 25;
	int resizeY = HEIGHT / 15;
	
	//Game features constants
	int monsterNumber = 52;
	int shipSpeed = WIDTH/400;
	int bulletSpeed = HEIGHT/200;
	int maxHealth = 3;
	
	int maxShots = 3; // limits how many bullets the player can shoot at a time
	
	


}
