package com.alienlabz.platform.game.scene;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.util.GLState;

import com.alienlabz.platform.BaseScene;
import com.alienlabz.platform.Game;
import com.alienlabz.platform.GlobalState;
import com.alienlabz.platform.resources.ResourcesPool;

public class MenuScene extends BaseScene implements IOnMenuItemClickListener {
	private ITextureRegion menuBackgroundRegion;
	private ITextureRegion playRegion;
	private ITextureRegion optionsRegion;

	private org.andengine.entity.scene.menu.MenuScene menuChildScene;
	private final int MENU_PLAY = 0;
	private final int MENU_OPTIONS = 1;

	@Override
	public void onCreate() {
		createBackground();
		createMenuChildScene();
	}

	@Override
	public void onCreateResources() {
		BuildableBitmapTextureAtlas atlas = ResourcesPool.getBuildableBitmapTextureAtlas();
		menuBackgroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, GlobalState.getActivity(), "gfx/menu/background.png");
		playRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, GlobalState.getActivity(), "gfx/menu/play.png");
		optionsRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, GlobalState.getActivity(), "gfx/menu/options.png");
	}

	private void createBackground() {
		attachChild(new Sprite(400, 240, menuBackgroundRegion, GlobalState.getVertexBufferObjectManager()) {
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		});
	}

	private void createMenuChildScene() {
		menuChildScene = new org.andengine.entity.scene.menu.MenuScene(GlobalState.getCamera());
		menuChildScene.setPosition(400, 240);

		IMenuItem playMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_PLAY, playRegion, GlobalState.getVertexBufferObjectManager()), 1.2f, 1);
		IMenuItem optionsMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_OPTIONS, optionsRegion, GlobalState.getVertexBufferObjectManager()), 1.2f, 1);

		menuChildScene.addMenuItem(playMenuItem);
		menuChildScene.addMenuItem(optionsMenuItem);

		menuChildScene.buildAnimations();
		menuChildScene.setBackgroundEnabled(false);

		playMenuItem.setPosition(0, -110);
		optionsMenuItem.setPosition(0, 0);

		menuChildScene.setOnMenuItemClickListener(this);

		setChildScene(menuChildScene);
	}

	@Override
	public boolean onMenuItemClicked(org.andengine.entity.scene.menu.MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
		switch (pMenuItem.getID()) {
		case MENU_PLAY:
			Game.start();
			return true;
		case MENU_OPTIONS:
			return true;
		default:
			return false;
		}
	}

}
