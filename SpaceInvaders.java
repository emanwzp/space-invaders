import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.sound.sampled.*;


public class SpaceInvaders extends Canvas implements Stage, KeyListener {
	private BufferStrategy strategy;
	private long usedTime;
	private ArrayList actors;
	public static ArrayList bulletList;
	private Player player;
	private int score = 0;

	// variables for animation and movement aspects
	public static boolean animation = true;
	public static boolean shipAnimation = true;
	private long SPEED = 2;
	// currently not used
	private SpriteCache spriteCache;
	
	
	private Font bItalicBold, bSize;
	private static boolean Start=false;
	
	
	public SpaceInvaders() {
	

		JFrame ventana = new JFrame("Space Invaders"); // Creates the program
														// frame
		final JPanel panel = (JPanel) ventana.getContentPane();
		setBounds(0, 0, Stage.WIDTH, Stage.HEIGHT);

		final Button StartB = new Button("Start");
		final Button ExitB = new Button("Exit");
		StartB.setBounds(Stage.WIDTH / 2 - 200, Stage.HEIGHT / 2 - 350, 400,
				200);
		
		ExitB.setBounds(Stage.WIDTH / 2 - 200, Stage.HEIGHT / 2 + 150, 400, 200);
		StartB.setBackground(Color.BLACK);
		StartB.setForeground(Color.WHITE);
		ExitB.setBackground(Color.BLACK);
		ExitB.setForeground(Color.WHITE);
		bItalicBold = new Font("Arial", Font.BOLD + Font.ITALIC, 14);
		bSize = new Font("Arial", Font.PLAIN, 18);
		StartB.setFont(bItalicBold);
		StartB.setFont(bSize);
		ExitB.setFont(bItalicBold);
		ExitB.setFont(bSize);
		StartB.setActionCommand("enable");
		StartB.setEnabled(true);
		ExitB.setActionCommand("enable");
		ExitB.setEnabled(true);
		panel.add(StartB);
		panel.add(ExitB);
		panel.setPreferredSize(new Dimension(Stage.WIDTH, Stage.HEIGHT)); // size
																			// is
																			// defined
																			// by
																			// stage
																			// parameters
		panel.setBackground(Color.black);
		panel.setLayout(null);
		panel.add(this);
		ventana.setBounds(0, 0, Stage.WIDTH, Stage.HEIGHT);
		ventana.setVisible(true);
		ventana.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0); // exits the program if the user closes the
								// Jframe window
			}
		});
		ventana.setResizable(false);
		createBufferStrategy(2);
		strategy = getBufferStrategy();
		requestFocus();
		addKeyListener(this);
		
		StartB.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				panel.remove(StartB);
				
				panel.remove(ExitB);
				StartB.setVisible(false);
				StartB.setVisible(false);
				Start=true;
			}
		});
		
		ExitB.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});	

	}

	
	
	public void initWorld() {
		int row = Stage.positionIncrement / 2; // variables to be used in the
												// position of monsters
		int column = 0;
		actors = new ArrayList(); // Assigns an array to the actors
									// variable,this array stores all entities
									// within the game
		bulletList = new ArrayList();

		for (int i = 0; i < Stage.monsterNumber; i++) { // Creates 10 monsters
														// with a loop and
														// places them
			column = column + 1; // according to the resolution/size of the
									// screen
			Monster m = new Monster(this); // until it gets to a border, then go
											// down an increment to fill another
											// row of monsters
			m.setX((int) (column * Stage.positionIncrement));
			if (m.getX() < Stage.WIDTH - Stage.positionIncrement) {
				m.setY(row);

			} else {
				column = 1;
				m.setX(column * Stage.positionIncrement);
				row = row + Stage.positionIncrement;
				m.setY(row);
			}

			actors.add(m);
		}

		player = new Player(this); // Creates a player Object
		player.shipInit();
		player.currentHealth = Stage.maxHealth;
		
		//Starts the background Music at the start of the game
		try {  
	          File backgroundMusic = new File("music.wav"); 
	          AudioInputStream audioIn = AudioSystem.getAudioInputStream(backgroundMusic);              
	         Clip music = AudioSystem.getClip();
	         music.open(audioIn);
	         music.start();
	      } catch (UnsupportedAudioFileException e) {	//Catches different exceptions that can occur with sound files	
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      } catch (LineUnavailableException e) {
	         e.printStackTrace();
	      }
		
		
	   }
	

	public void addActor(Actor a) { // ??? not sure what this is for
		actors.add(a);
	}

	public void updateWorld() { // This method checks if a monster is supposed
								// to die (got hit by a bullet) and also runs
		// Monsters Update // the methods for the entities to do something which
		// is not implemented yet
		int i = 0;
		while (i < actors.size()) {
			Monster m = (Monster) actors.get(i);
			if (m.isMarkedForRemoval()) { // checks the boolean to see if the
											// monster should be removed
				actors.remove(i); // if it returns true, execute the remove
									// method
				score = score + 1;
				playExplosion();
			} else {
				m.setSprite(SpriteCache.getMonsterSprite());
				m.monsterMove(); // makes the monsters move
				i++;
			}
		}
		// Bullets update
		if (bulletList.size() > 0) { // Bullet will continuously go up once
										// fired(needs improvement,
			for (int i2 = 0; i2 < bulletList.size(); i2++) {
				Bullets b = (Bullets) bulletList.get(i2);
				if (b.isMarkedForRemoval()) {
					bulletList.remove(i2);
				} else if (b.getY() > 0) {
					b.setY(b.getY() - bulletSpeed);
					b.setSprite(SpriteCache.getBulletSprite());
				} else {
					bulletList.remove(i2);
				}
			}
		}
		// Player update
		if (player.currentHealth==0) {	//if the players current health is 0 then remove the player object
			player = null;
		}
		player.setSprite(SpriteCache.getPlayerSprite()); // the sprite needs to
															// be constantly
															// assigned because
															// it will shift
															// between 2 sprites
															// depending on
															// movement
		player.move(); // executes the move method for the ship, which is
						// basically shifting its x and y variables according to
						// key events

		checkCollisions();
		animation(); // executes the animation method, which shifts a boolean to
						// alternate the sprites of the monsters and bullets
	}

	public void checkCollisions() { // Collision checker method, first gets each
									// entity and checks if it is intersected
									// with the
		Rectangle playerBounds = player.getBounds(); // player, if it does...
		for (int i = 0; i < actors.size(); i++) {
			Actor a1 = (Actor) actors.get(i);
			Rectangle actorBounds = a1.getBounds();
			if (actorBounds.intersects(playerBounds)) {
				player.collision(a1); // runs the collision method within the
										// actor class, for both player and the
										// corresponding entity
				player.shipInit();
				a1.collision(player);
				playExplosion();
				
			}

			for (int j = 0; j < actors.size(); j++) { // this loop runs inside
														// the first loop. Its
														// purpose is to run a
														// collision checker
				Actor a2 = (Actor) actors.get(j); // between the entity actor[i]
													// against the next entity:
													// bullet[i2]
				Rectangle actor2Bounds = a2.getBounds();
				for (int i2 = 0; i2 < bulletList.size(); i2++) {
					Bullets b1 = (Bullets) bulletList.get(i2);
					Rectangle bulletBounds = b1.getBounds();
					if (actor2Bounds.intersects(bulletBounds)) {
						a2.collision(b1);
						b1.collision(a2);
					}
				}
			}

		}
	}

	public void animation() { // Makes the sprites of the monsters and bullets
								// shift to create an animation(needs
								// improvement)
		if (animation == true) {
			animation = false;
		} else if (animation == false) {
			animation = true;
		}
	}
	
	public static void playExplosion(){						//this method is used to play the explosion sound when necessary (whenever player collides with a monster or the monster is removed)
		try {    		//This codes the shooting sound
	         File explosionSound = new File("explosion.wav"); 
	         AudioInputStream audioIn = AudioSystem.getAudioInputStream(explosionSound);              
	         Clip explosion = AudioSystem.getClip();
	  
	         explosion.open(audioIn);
	         explosion.start();
	      } catch (UnsupportedAudioFileException e) {	//Catches different exceptions that can occur with sound files	
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      } catch (LineUnavailableException e) {
	         e.printStackTrace();
	      }
	}
	
	public void paintWorld() throws IOException { // This method aims to set the background to
								// black
		Graphics2D g = (Graphics2D) strategy.getDrawGraphics(); // and paint the
																// entities
		Image img = ImageIO.read(new File("Star.jpg"));
		// (monsters and
																// player)
		g.setColor(Color.black); // using the spriteCache class images
		g.fillRect(0, 0, Stage.WIDTH, Stage.HEIGHT);
		g.drawImage(img,0,0,Stage.WIDTH,Stage.HEIGHT,null);
		// painting the monsters by going through the actors array
		for (int i = 0; i < actors.size(); i++) {
			Actor m = (Actor) actors.get(i);
			m.paint(g);
		}
		// painting the player ship
		
		player.paint(g);
		
		
		// painting the bullets by going through the bulletsList array
		if (bulletList.size() > 0) {
			for (int i = 0; i < bulletList.size(); i++) {
				Bullets b = (Bullets) bulletList.get(i);
				b.paint(g);
			}
		}
		
		
		g.setColor(Color.white);
		int currentShots = Stage.maxShots - bulletList.size();
		
		// This draws the simple UI at the bottom right of the page
		g.drawImage(SpriteCache.getMonsterIcon(), Stage.WIDTH
				- Stage.positionIncrement, Stage.HEIGHT
				- Stage.positionIncrement, Stage.resizeX / 3,
				Stage.resizeY / 3, null);
		g.drawString(" = " + score,
				Stage.WIDTH - (int) Math.round(Stage.positionIncrement / 1.4),
				Stage.HEIGHT - (int) Math.round(Stage.positionIncrement / 1.2));
		g.drawImage(
				SpriteCache.getShipIcon(),
				Stage.WIDTH - Stage.positionIncrement,
				Stage.HEIGHT - (int) Math.round(Stage.positionIncrement / 1.25),
				Stage.resizeX / 3, Stage.resizeY / 3, null);
		g.drawString(" = " + player.currentHealth,
				Stage.WIDTH - (int) Math.round(Stage.positionIncrement / 1.4),
				Stage.HEIGHT - (int) Math.round(Stage.positionIncrement / 1.5));
		g.drawImage(
				SpriteCache.getBulletIcon(),
				Stage.WIDTH - Stage.positionIncrement,
				Stage.HEIGHT - (int) Math.round(Stage.positionIncrement / 1.65),
				Stage.resizeX / 3, Stage.resizeY / 3, null);
		g.drawString(" = " + currentShots,
				Stage.WIDTH - (int) Math.round(Stage.positionIncrement / 1.4),
				Stage.HEIGHT - Stage.positionIncrement / 2);

		
		
		
		if (usedTime > 0) {	//shows fps at the bottom left corner
			g.drawString(
					String.valueOf(1000 / usedTime) + " fps",
					10,
					Stage.HEIGHT
							- (int) Math.round(Stage.positionIncrement / 2));
		} else {
			g.drawString(
					"--- fps",
					0,
					Stage.HEIGHT
							- (int) Math.round(Stage.positionIncrement / 2));
		}
		
		if (score == Stage.monsterNumber){						//This sets up the different endings, either the player wins and it shows the message on screen or it loses and a different message appears
			g.setFont(new Font("TimesRoman", Font.PLAIN, 100));
			g.drawString("You've Saved The World!",400,Stage.HEIGHT/2);
		}else if (player.currentHealth <= 0){
			g.setFont(new Font("TimesRoman", Font.PLAIN, 100));
			g.drawString("They Have Invaded Earth!",400,Stage.HEIGHT/2);
		}
		for (int i = 0; i < actors.size(); i++) {
			Actor m = (Actor) actors.get(i);
			if (m.getY()>=Stage.HEIGHT){
				g.setFont(new Font("TimesRoman", Font.PLAIN, 100));
				g.drawString("They Have Invaded Earth!",400,Stage.HEIGHT/2);
			}
		}
		
		strategy.show();
	}

	public SpriteCache getSpriteCache() { // not used so far
		return spriteCache;
	}

	public void keyPressed(KeyEvent e) { // listens for key events and executes
											// the methods inside the player
											// class
		player.keyPressed(e);
	}

	public void keyReleased(KeyEvent e) {	
		player.keyReleased(e);
	}

	public void keyTyped(KeyEvent e) {
	}


	public void game() throws IOException { // starts the game by looping through the required
							// methods that need to be updated constantly such
							// as collision, painting and updating the world
		usedTime = 1000;
		initWorld();

		while (isVisible()) {
			long startTime = System.currentTimeMillis();
			paintWorld();
			updateWorld();
			checkCollisions();

			usedTime = System.currentTimeMillis() - startTime;

			try { // This is what influences how fast the game runs/is updated
				Thread.sleep(SPEED); // tweak the SPEED variable to get a
										// faster,slower gaming experience
			} catch (InterruptedException e) {
			}
		}
	}

	public static void main(String[] args) throws AWTException, IOException {
		SpaceInvaders inv = new SpaceInvaders();		//creates the SpaceInvaders instance
		while (Start == false){							//Keeps the user stuck here until he presses start (Start Screen)
			Robot bot = new Robot();
			int mask = InputEvent.BUTTON1_DOWN_MASK;
			bot.mousePress(mask); 
		}
		inv.game();	//if this plays it means the user clicked start and so the game can be initiated
	}
}
