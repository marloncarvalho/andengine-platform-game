package com.alienlabz.platform.map;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.tmx.TMXLoader;
import org.andengine.extension.tmx.TMXObject;
import org.andengine.extension.tmx.TMXObjectGroup;
import org.andengine.extension.tmx.TMXTiledMap;
import org.andengine.extension.tmx.util.exception.TMXLoadException;
import org.andengine.opengl.texture.TextureOptions;

import com.alienlabz.platform.GlobalState;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class TMXMapLoader implements MapLoader {
	private TMXTiledMap mTMXTiledMap;
	private Scene mScene;
	private PhysicsWorld mPhysicsWorld;

	@Override
	public void load(String levelFile, Scene pScene, PhysicsWorld pPhysicsWorld) {
		mScene = pScene;
		mPhysicsWorld = pPhysicsWorld;
		createTMXMap();
	}

	@Override
	public void unload() {
		// TODO Auto-generated method stub

	}

	private void createTMXMap() {
		TMXLoader tmxLoader = new TMXLoader(GlobalState.getActivity().getAssets(), GlobalState.getEngine().getTextureManager(), TextureOptions.BILINEAR_PREMULTIPLYALPHA,
				GlobalState.getVertexBufferObjectManager(), null);

		try {
			mTMXTiledMap = tmxLoader.loadFromAsset("level/lvl2.tmx");
			GlobalState.getCamera().offsetCenter(0, 0);
			this.mTMXTiledMap.setOffsetCenter(0, 0);
			mScene.attachChild(mTMXTiledMap);

			GlobalState.getCamera().setBounds(0, 0, this.mTMXTiledMap.getWidth(), this.mTMXTiledMap.getHeight());
			GlobalState.getCamera().setBoundsEnabled(true);

			for (final TMXObjectGroup group : this.mTMXTiledMap.getTMXObjectGroups()) {
				for (final TMXObject object : group.getTMXObjects()) {
					final FixtureDef boxFixtureDef = PhysicsFactory.createFixtureDef(0, 0, 1f);
					final Rectangle rect = new Rectangle(object.getX(), (mTMXTiledMap.getHeight() - object.getY() - object.getHeight()), object.getWidth(), object.getHeight(),
							GlobalState.getVertexBufferObjectManager());
					rect.setOffsetCenter(0, 0);
					Body body = PhysicsFactory.createBoxBody(mPhysicsWorld, rect, BodyType.StaticBody, boxFixtureDef);

					body.setUserData(object.getType());
					rect.setVisible(false);
					mScene.attachChild(rect);
				}
			}

		} catch (TMXLoadException e) {
			e.printStackTrace();
		}
	}

}
