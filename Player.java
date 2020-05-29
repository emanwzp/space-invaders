import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

public class Player extends Actor  {
	public static int currentHealth;


	public Player(SpaceInvaders spaceInvaders) {
		
	}
	public void shipInit() {		//When ship is initiated has this default location
		x = (Stage.WIDTH / 2);
		y = (Stage.HEIGHT - Stage.positionIncrement);
	}
	


	public void keyPressed(KeyEvent e) {	//This controls the ship based on which key is pressed and released
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {
			dx = -Stage.shipSpeed;
			SpaceInvaders.shipAnimation = false;
		}

		if (key == KeyEvent.VK_RIGHT) {	dx = Stage.shipSpeed;
			SpaceInvaders.shipAnimation = false;
		}

		if (key == KeyEvent.VK_UP) {
			dy = -Stage.shipSpeed;
			SpaceInvaders.shipAnimation = false;
			
		}

		if (key == KeyEvent.VK_DOWN) {
			dy = Stage.shipSpeed;
			SpaceInvaders.shipAnimation = false;

		}
		if (key == KeyEvent.VK_SPACE) {
			shoot();				//when space is pressed runs the shoot method which essentially adds a Bullets object to the burretList array
		}
	}
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_LEFT){
			dx = 0;
			SpaceInvaders.shipAnimation = true;
		}
		if (key == KeyEvent.VK_RIGHT) {
			dx = 0;
			SpaceInvaders.shipAnimation = true;
		}
		if (key == KeyEvent.VK_UP) {
			dy = 0;
			SpaceInvaders.shipAnimation = true; 
		}
		if (key == KeyEvent.VK_DOWN) {
			dy = 0;
			SpaceInvaders.shipAnimation = true; 
		}
		if (key == KeyEvent.VK_SPACE) {
		 
		}
	}
	public void shoot() {		//method that handles the shooting when the user presses the spacebar, creates a new bullet object stores it in the array and assigns its x and y depending on the ship location
		if(SpaceInvaders.bulletList.size() < Stage.maxShots) {
		Bullets bullet = new Bullets(null);
		bullet.setX(x);
		bullet.setY(y);
		SpaceInvaders.bulletList.add(bullet);
		
		
		try {    		//This codes the shooting sound
	         File laserSound = new File("laser.wav"); 
	         AudioInputStream audioIn = AudioSystem.getAudioInputStream(laserSound);              
	         Clip sound = AudioSystem.getClip();
	  
	         sound.open(audioIn);
	         sound.start();
	      } catch (UnsupportedAudioFileException e) {	//Catches different exceptions that can occur with sound files	
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      } catch (LineUnavailableException e) {
	         e.printStackTrace();
	      }
	   }
	}
	





}