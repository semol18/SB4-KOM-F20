/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.enemy;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

/**
 *
 * @author sebastian
 */
public class EnemyPlugin implements IGamePluginService {

    private Entity enemy;

    public EnemyPlugin() {

    }

    @Override
    public void start(GameData gd, World world) {
        enemy = createEnemyShip(gd);
        world.addEntity(enemy);
    }

        private Entity createEnemyShip(GameData gameData) {

        float deacceleration = 10;
        float acceleration = 100;
        float maxSpeed = 200;
        float rotationSpeed = 5;
        float x = gameData.getDisplayWidth() / 3;
        float y = gameData.getDisplayHeight() / 3;
        float radians = 3.1415f / 2;
        
        Entity enemyShip = new Enemy();
        enemyShip.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        enemyShip.add(new PositionPart(x, y, radians));
        enemyShip.setRadius(3);
        
        return enemyShip;
    }
    
    @Override
    public void stop(GameData gd, World world) {
        world.removeEntity(enemy);
    }

}
