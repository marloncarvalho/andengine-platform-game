package com.alienlabz.platform.game.scene;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.util.adt.color.Color;

import com.alienlabz.platform.BaseScene;
import com.alienlabz.platform.GlobalState;
import com.alienlabz.platform.resources.ResourcesPool;

public class SplashScene extends BaseScene {
	private Sprite splash;
	private ITextureRegion splashRegion;

	@Override
	public void onCreate() {
		createBackground();
		createSprites();
	}

	@Override
	public void onCreateResources() {
		splashRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(ResourcesPool.getBitmapTextureAtlas(), GlobalState.getActivity(), "gfx/splash.png", 0, 0);
	}

	private void createSprites() {
		splash = new Sprite(0, 0, splashRegion, GlobalState.getVertexBufferObjectManager()) {

			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}

		};

		splash.setScale(1.5f);
		splash.setPosition(400, 240);

		attachChild(splash);
	}

	private void createBackground() {
		setBackground(new Background(Color.BLACK));
	}

}
