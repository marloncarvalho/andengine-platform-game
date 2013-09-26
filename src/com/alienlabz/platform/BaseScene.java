package com.alienlabz.platform;

import org.andengine.entity.scene.Scene;

abstract public class BaseScene extends Scene {

	public abstract void onCreate();

	public abstract void onCreateResources();
	
	public void onCreateSprites() {
	}

}
