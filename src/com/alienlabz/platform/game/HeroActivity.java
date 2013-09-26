package com.alienlabz.platform.game;

import com.alienlabz.platform.Game;
import com.alienlabz.platform.SimpleGameActivity;

public class HeroActivity extends SimpleGameActivity {

	@Override
	public Game onGameCreate() {
		setCameraHeight(480);
		setCameraWidth(800);

		return new HeroGame();
	}

}
