package dk.sdu.mmmi.cbse.gamestates;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.mmmi.cbse.entities.Enemy;
import dk.sdu.mmmi.cbse.entities.Player;
import dk.sdu.mmmi.cbse.managers.GameKeys;
import dk.sdu.mmmi.cbse.managers.GameStateManager;

import java.util.Random;

public class PlayState extends GameState {
	
	private ShapeRenderer sr;
	private Player player;
	private Enemy enemy;
	private Random rd = new Random();
	private int enemyMovement;
	
	public PlayState(GameStateManager gsm) {
		super(gsm);
	}
	
	public void init() {
		
		sr = new ShapeRenderer();
		
		player = new Player();

		enemy = new Enemy();
		
	}
	
	public void update(float dt) {
		
		handleInput();
		
		player.update(dt);

		enemy.update(dt);
		
	}
	
	public void draw() {
		player.draw(sr);

		enemy.draw(sr);
	}
	
	public void handleInput() {

		// Player movement.
		player.setLeft(GameKeys.isDown(GameKeys.LEFT));
		player.setRight(GameKeys.isDown(GameKeys.RIGHT));
		player.setUp(GameKeys.isDown(GameKeys.UP));
		player.setDown(GameKeys.isDown(GameKeys.DOWN));

		// Enemy movement.
		if (enemyMovement == 0) {
			enemy.setRight(rd.nextBoolean());
			enemy.setUp(false);
			enemyMovement = 50;
		}

		if (enemyMovement == 25) {
			enemy.setLeft(rd.nextBoolean());
			enemy.setUp(false);
		}
		enemyMovement --;
		enemy.setUp(rd.nextBoolean());
	}
	
	public void dispose() {}
	
}









