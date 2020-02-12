package dk.sdu.mmmi.cbse.gamestates;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.mmmi.cbse.entities.Bullet;
import dk.sdu.mmmi.cbse.entities.Enemy;
import dk.sdu.mmmi.cbse.entities.Player;
import dk.sdu.mmmi.cbse.managers.GameKeys;
import dk.sdu.mmmi.cbse.managers.GameStateManager;

import java.util.ArrayList;
import java.util.Random;

public class PlayState extends GameState {
	
	private ShapeRenderer sr;
	private Player player;
	private ArrayList<Bullet> playerBullets;
	private ArrayList<Bullet> enemyBullets;
	private Enemy enemy;
	private Random rd = new Random();
	private int enemyMovementTimer;
	private int enemyShootTimer;
	
	public PlayState(GameStateManager gsm) {
		super(gsm);
	}
	
	public void init() {
		
		sr = new ShapeRenderer();

		playerBullets = new ArrayList<Bullet>();
		player = new Player(playerBullets);

		enemyBullets = new ArrayList<Bullet>();
		enemy = new Enemy(enemyBullets);
		
	}
	
	public void update(float dt) {

		// Get user input.
		handleInput();

		// Update player.
		player.update(dt);

		// Update player bullets.
		for (int i = 0; i < playerBullets.size(); i++) {
			playerBullets.get(i).update(dt);
			if (playerBullets.get(i).shouldRemove()) {
				playerBullets.remove(i);
				i--;
			}
		}

		// Update enemy.
		enemy.update(dt);

		// Update enemy bullets.
		for (int i = 0; i < enemyBullets.size(); i++) {
			enemyBullets.get(i).update(dt);
			if (enemyBullets.get(i).shouldRemove()) {
				enemyBullets.remove(i);
				i--;
			}
		}
	}
	
	public void draw() {

		// Draw player.
		player.draw(sr);

		// Draw player bullets.
		for (int i = 0; i < playerBullets.size(); i++) {
			playerBullets.get(i).draw(sr);
		}

		// Draw enemy.
		enemy.draw(sr);

		for (int i = 0; i < enemyBullets.size(); i++) {
			enemyBullets.get(i).draw(sr);
		}
	}
	
	public void handleInput() {

		// Player movement.
		player.setLeft(GameKeys.isDown(GameKeys.LEFT));
		player.setRight(GameKeys.isDown(GameKeys.RIGHT));
		player.setUp(GameKeys.isDown(GameKeys.UP));
		player.setDown(GameKeys.isDown(GameKeys.DOWN));

		// Player shoot.
		if(GameKeys.isPressed(GameKeys.SPACE)) {
			player.shoot();
		}

		// Enemy movement.
		enemy.move();

		// Enemy shoot.
		if (enemyShootTimer == 0) {
			enemy.shoot();
			enemyShootTimer = 60;
		}
		enemyShootTimer --;
	}
	
	public void dispose() {}
	
}









