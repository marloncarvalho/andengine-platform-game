package com.alienlabz.platform.game.scene;

import com.alienlabz.platform.BaseScene;
import com.alienlabz.platform.Controller;
import com.alienlabz.platform.Controller.IControllerListener;
import com.alienlabz.platform.entity.Player;
import com.alienlabz.platform.map.TMXMapLoader;
import com.alienlabz.platform.world.Box2DWorld;
import com.alienlabz.platform.world.World;

/**
 * Cena principal do jogo.
 * 
 * @author Marlon Silva Carvalho
 * @since 0.0.1
 */
public class GameScene extends BaseScene {
	private World mWorld;
	private Controller mDigitalController;
	private Player mPlayer;

	@Override
	public void onCreate() {
		createWorld();
		createDigitalController();
		attachChild(mPlayer.getSprite());
	}

	/**
	 * Criar o mundo.
	 */
	private void createWorld() {
		mWorld = new Box2DWorld(this);
		mWorld.setDefaultGravity(0, -75);
		mWorld.setMapLoader(new TMXMapLoader());
		mWorld.setPlayer(mPlayer);
		mWorld.initialize();
	}

	/**
	 * Criar o controle digital para o jogo.
	 */
	private void createDigitalController() {
		mDigitalController = new Controller(this);
		mDigitalController.setListener(new IControllerListener() {

			@Override
			public void onMove(float pX, float pY) {
				mPlayer.walk(pX);
			}

			@Override
			public void onButtonBPressed() {
				//				mPlayer.fire();
			}

			@Override
			public void onButtonAPressed() {
				mPlayer.jump();
			}

		});
	}

	@Override
	public void onCreateResources() {
		mPlayer = new Player(100, 200, "gfx/game/Player.png");
	}

	@Override
	public void onCreateSprites() {
		mPlayer.createSprite();
	}

}
