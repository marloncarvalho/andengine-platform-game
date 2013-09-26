package com.alienlabz.platform.world;

import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsWorld;

import com.alienlabz.platform.Game;
import com.alienlabz.platform.GlobalState;
import com.alienlabz.platform.entity.Player;
import com.alienlabz.platform.game.scene.GameScene;
import com.badlogic.gdx.math.Vector2;

/**
 * Um mundo baseado na f’sica e caracter’sticas da engine do Box2D e AndEngine.
 * 
 * @author Marlon Silva Carvalho
 * @since 0.0.1
 */
public class Box2DWorld extends World {
	private PhysicsWorld mPhysicsWorld;

	public Box2DWorld(GameScene pGameScene) {
		createPhysics();
		pGameScene.registerUpdateHandler(mPhysicsWorld);
//		DebugRenderer debug = new DebugRenderer(mPhysicsWorld, GlobalState.getVertexBufferObjectManager());
//		pGameScene.attachChild(debug);
	}

	@Override
	public void setPlayer(Player pPlayer) {
		super.setPlayer(pPlayer);
		pPlayer.setPhysicsWorld(mPhysicsWorld);
		GlobalState.getCamera().setChaseEntity(pPlayer.getSprite());
	}

	@Override
	public void create() {
		mMapLoader.load("level/lvl1.tmx", Game.getCurrentScene(), mPhysicsWorld);
	}

	@Override
	public void setDefaultGravity(float pXGravity, float pYGravity) {
		mPhysicsWorld.setGravity(new Vector2(pXGravity, pYGravity));
	}

	@Override
	public void free() {
		// TODO Auto-generated method stub

	}

	/**
	 * Criar o objeto que define as propriedades f’sicas deste mundo.
	 */
	private void createPhysics() {
		mPhysicsWorld = new FixedStepPhysicsWorld(60, new Vector2(0, -75), true);
	}

}