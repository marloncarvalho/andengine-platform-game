package com.alienlabz.platform.game.scene;

import java.io.IOException;

import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;

import com.alienlabz.platform.BaseScene;
import com.alienlabz.platform.Controller;
import com.alienlabz.platform.Controller.IControllerListener;
import com.alienlabz.platform.GlobalState;
import com.alienlabz.platform.entity.Ability;
import com.alienlabz.platform.entity.Hero;
import com.alienlabz.platform.entity.Jump;
import com.alienlabz.platform.entity.Walk;
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
	private Hero mHero;

	@Override
	public void onCreate() {
		createWorld();
		createDigitalController();
		createBackground();
		attachChild(mHero.getSprite());
	}

	private void createBackground() {
		ParallaxBackground parallaxBackground;
		ITextureRegion mParallaxLayerBackTextureRegion = null;
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		ITexture mParallaxLayerBackTexture;
		try {
			mParallaxLayerBackTexture = new AssetBitmapTexture(GlobalState.getActivity().getTextureManager(), GlobalState.getActivity().getAssets(), "gfx/game/background.jpg");
			mParallaxLayerBackTextureRegion = TextureRegionFactory.extractFromTexture(mParallaxLayerBackTexture);
			mParallaxLayerBackTexture.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Sprite parallaxSprite = new Sprite(0, 0, mParallaxLayerBackTextureRegion, GlobalState.getActivity().getVertexBufferObjectManager());
		parallaxSprite.setOffsetCenter(0, 0);
		parallaxBackground = new ParallaxBackground(0, 0, 0);
		parallaxBackground.attachParallaxEntity(new ParallaxEntity(1, parallaxSprite));
		setBackground(parallaxBackground);
	}

	/**
	 * Criar o mundo.
	 */
	private void createWorld() {
		mWorld = new Box2DWorld(this);
		mWorld.setDefaultGravity(0, -75);
		mWorld.setMapLoader(new TMXMapLoader());
		mWorld.setPlayer(mHero);
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
				mHero.perform(Walk.class, Ability.p(Walk.PARAM_VELOCITY, pX));
			}

			@Override
			public void onButtonBPressed() {
			}

			@Override
			public void onButtonAPressed() {
				mHero.perform(Jump.class, Ability.p(Jump.PARAM_HEIGHT, Hero.JUMP_HEIGHT));
			}

		});
	}

	@Override
	public void onCreateResources() {
		mHero = new Hero(100, 200);
	}

	@Override
	public void onCreateSprites() {
		mHero.createSprite();
	}

}
