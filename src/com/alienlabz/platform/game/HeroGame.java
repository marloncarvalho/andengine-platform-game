package com.alienlabz.platform.game;

import com.alienlabz.platform.BaseScene;
import com.alienlabz.platform.Game;
import com.alienlabz.platform.game.scene.GameScene;
import com.alienlabz.platform.game.scene.MenuScene;
import com.alienlabz.platform.game.scene.SplashScene;

public class HeroGame extends Game {

	@Override
	public BaseScene createSplashScene() {
		return new SplashScene();
	}

	@Override
	public BaseScene createMenuScene() {
		return new MenuScene();
	}

	@Override
	public BaseScene createGameScene() {
		return new GameScene();
	}

}
