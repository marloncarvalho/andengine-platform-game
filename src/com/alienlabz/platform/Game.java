package com.alienlabz.platform;

import org.andengine.entity.scene.Scene;

import com.alienlabz.platform.resources.ResourcesPool;

abstract public class Game {
	public Scene mCurrentScene;

	protected abstract BaseScene createSplashScene();

	protected abstract BaseScene createMenuScene();

	protected abstract BaseScene createGameScene();

	private static Game instance;

	public Game() {
		if (instance == null) {
			Game.instance = this;
		} else {
			throw new RuntimeException("Game already started. Can't assign a new instance.");
		}
	}

	public static Scene getCurrentScene() {
		return instance.mCurrentScene;
	}

	public static void start() {
		GlobalState.getEngine().setScene(instance.getGameScene());
	}

	public static void pause() {

	}

	public static void showMenu() {
		GlobalState.getEngine().setScene(instance.getMenuScene());
	}

	public static void over() {

	}

	public Scene getMenuScene() {
		return createScene(createMenuScene());
	}

	public Scene getSplashScene() {
		return createScene(createSplashScene());
	}

	public Scene getGameScene() {
		return createScene(createGameScene());
	}

	private Scene createScene(BaseScene pScene) {
		if (pScene == null) {
			throw new RuntimeException();
		}

		mCurrentScene = pScene;

		ResourcesPool.initialize();
		pScene.onCreateResources();
		ResourcesPool.allocate();
		pScene.onCreateSprites();
		pScene.onCreate();

		return pScene;
	}

}
