import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

public class Actor implements Stage {

	
	int x;
	int y;
	int vx;
	int dx;
	int dy;
	int i = 0;
	boolean markedForRemoval = false;
	Image sprite = null;

	
	public void setX(int i) {	//Sets the x variable of the actor object in question
		x = i;
	}
	public void setY(int i) {	//Sets the y variable of the actor object in question
		y = i;
	}
	public int getX() {			//Returns the x variable of the actor object in question
		return x;
	}
	public int getY() {			//Returns the y variable of the actor object in question
		return y;
	}
	public void setVx(int i) {

	}
	public void setSprite(Image i) { //getters and setters for the sprite of the ship(changes if the ship is moving)
		sprite = i;
	}
	public Image getSprite() {
		return sprite;
	}
	public int getHeight() {	//not used so far
		return 0;
	}

	public Rectangle getBounds() {	//returns the bounds for each entity for collision purposes
		Rectangle bounds = new Rectangle(x , y, Stage.resizeX/2 , Stage.resizeY/2);
		return bounds;
	}

	public boolean isMarkedForRemoval() {	//checks if an entity is marked for removal, if it is, it is removed from the array
		if(markedForRemoval == false) {
			return false;
		}
		else {
			return true;
		}
	}



	public void collision(Actor entity) { //this method will run if a collision happens, and then check the entity that collided, 
										//if its the player its current health goes down by 1, if its a monster it gets marked for removal
		boolean isInstance = entity instanceof Player;
		if(isInstance == true) {
			Player.currentHealth = Player.currentHealth - 1;
		}
		else {
			entity.markedForRemoval = true;
		}
		
		
		
	}

	public void paint(Graphics2D g) {	//paints the entities on the screen using the variables of sprite, its location on the x and y axis and resizes them according to the resolution of the screen
		g.drawImage(sprite, x, y,Stage.resizeX,Stage.resizeY, null);	
	}
	public void move() {	//Standard method for when the entities move
		if (x < 0) {
			x = 1;
		}
		else if (x >= Stage.WIDTH - Stage.resizeX) {
			x = Stage.WIDTH - Stage.resizeX - 1;
		}
		else if (y < 0) {
			y = 1;
		}
		else if (y >= Stage.HEIGHT - Stage.resizeY) {
			y = Stage.HEIGHT - Stage.resizeY - 1;
		}
		else {
			x = x + dx;
			y = y + dy;
		}
	}

	

}