import java.awt.Image;
import javax.swing.ImageIcon;

public class SpriteCache {		//This class is dedicated to getting the sprite for each entity(playerShip, Monsters and bullets and respective icons for the info panel)
	
	public static Image getPlayerSprite(){
		Image sprite = null;
		if(SpaceInvaders.shipAnimation == true) {
			ImageIcon i = new ImageIcon("ship.png");
			sprite= i.getImage();
		}
		else {
			ImageIcon i = new ImageIcon("ship2.png");
			sprite = i.getImage();
		}
		return sprite;
	}
	
	public static Image getMonsterSprite(){
		Image sprite = null;
		if(SpaceInvaders.animation == true) {
			ImageIcon i = new ImageIcon("monster.png");
			sprite = i.getImage();
		}
		else {
			ImageIcon i = new ImageIcon("monster2.png");
			sprite = i.getImage();
		}
		return sprite;
	}
	
	public static Image getBulletSprite() {
		Image sprite = null;
		if(SpaceInvaders.animation == true) {
			ImageIcon i = new ImageIcon("bullet.png");
			sprite = i.getImage();
		}
		else {
			ImageIcon i = new ImageIcon("bullet2.png");
			sprite = i.getImage();
		};
		return sprite;
	}
	
	public static Image getBulletIcon() {
		Image sprite = null;
		ImageIcon i = new ImageIcon("bulletIcon.png");
		sprite = i.getImage();
		return sprite;
	}
	
	public static Image getShipIcon() {
		Image sprite = null;
		ImageIcon i = new ImageIcon("shipIcon.png");
		sprite = i.getImage();
		return sprite;
	}
	
	public static Image getMonsterIcon() {
		Image sprite = null;
		ImageIcon i = new ImageIcon("monsterIcon.png");
		sprite = i.getImage();
		return sprite;
	}
	public static Image getExplosionSprite(){
		Image sprite = null;
		if(SpaceInvaders.animation == true) {
			ImageIcon i = new ImageIcon("explosion.png");
			sprite = i.getImage();
		}
		else {
			ImageIcon i = new ImageIcon("explosion2.png");
			sprite = i.getImage();
		}
		return sprite;
	}

}
