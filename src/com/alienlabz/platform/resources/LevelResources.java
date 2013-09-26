package com.alienlabz.platform.resources;

import java.io.IOException;

import org.andengine.entity.IEntity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.SAXUtils;
import org.andengine.util.debug.Debug;
import org.andengine.util.level.EntityLoader;
import org.andengine.util.level.constants.LevelConstants;
import org.andengine.util.level.simple.SimpleLevelEntityLoaderData;
import org.andengine.util.level.simple.SimpleLevelLoader;
import org.xml.sax.Attributes;

import com.alienlabz.platform.GlobalState;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class LevelResources {
	private static final String TAG_ENTITY = "entity";
	private static final String TAG_ENTITY_ATTRIBUTE_X = "x";
	private static final String TAG_ENTITY_ATTRIBUTE_Y = "y";
	private static final String TAG_ENTITY_ATTRIBUTE_TYPE = "type";

	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_GRASS = "Grass";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_GRASS_MID = "GrassMid";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_GRASS_LEFT = "GrassLeft";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_GRASS_RIGHT = "GrassRight";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_GRASS_CENTER_ROUND = "GrassCenterRound";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_GRASSCENTER = "GrassCenter";

	public BuildableBitmapTextureAtlas gameTextureAtlas;

	public ITextureRegion grassRegion;
	public ITextureRegion grassCenterRoundRegion;
	public ITextureRegion grassCenterRegion;
	public ITextureRegion grassLeftRegion;
	public ITextureRegion grassRightRegion;
	public ITextureRegion grassMidRegion;

	public void createLevel(int levelID, final Scene scene, final PhysicsWorld physicsWorld) {
		final SimpleLevelLoader levelLoader = new SimpleLevelLoader(GlobalState.getVertexBufferObjectManager());

		final FixtureDef FIXTURE_DEF = PhysicsFactory.createFixtureDef(0, 0.01f, 0.5f);

		levelLoader.registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>(LevelConstants.TAG_LEVEL) {

			public IEntity onLoadEntity(final String pEntityName, final IEntity pParent, final Attributes pAttributes, final SimpleLevelEntityLoaderData pSimpleLevelEntityLoaderData)
					throws IOException {

				//				final int width = SAXUtils
				//						.getIntAttributeOrThrow(pAttributes, LevelConstants.TAG_LEVEL_ATTRIBUTE_WIDTH);
				//				final int height = SAXUtils.getIntAttributeOrThrow(pAttributes,
				//						LevelConstants.TAG_LEVEL_ATTRIBUTE_HEIGHT);

				return scene;
			}

		});

		levelLoader.registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>(TAG_ENTITY) {

			public IEntity onLoadEntity(final String pEntityName, final IEntity pParent, final Attributes pAttributes, final SimpleLevelEntityLoaderData pSimpleLevelEntityLoaderData)
					throws IOException {

				final int x = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_ENTITY_ATTRIBUTE_X);
				final int y = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_ENTITY_ATTRIBUTE_Y);
				final String type = SAXUtils.getAttributeOrThrow(pAttributes, TAG_ENTITY_ATTRIBUTE_TYPE);

				final Sprite levelObject;

				if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_GRASS)) {
					levelObject = new Sprite(x, y, grassRegion, GlobalState.getVertexBufferObjectManager());
					PhysicsFactory.createBoxBody(physicsWorld, levelObject, BodyType.StaticBody, FIXTURE_DEF).setUserData("grass");
				} else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_GRASS_CENTER_ROUND)) {
					levelObject = new Sprite(x, y, grassCenterRoundRegion, GlobalState.getVertexBufferObjectManager());
					Body body = PhysicsFactory.createBoxBody(physicsWorld, levelObject, BodyType.StaticBody, FIXTURE_DEF);
					body.setUserData("grassCenterRound");
					physicsWorld.registerPhysicsConnector(new PhysicsConnector(levelObject, body, true, false));
				} else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_GRASSCENTER)) {
					levelObject = new Sprite(x, y, grassCenterRegion, GlobalState.getVertexBufferObjectManager());
					Body body = PhysicsFactory.createBoxBody(physicsWorld, levelObject, BodyType.StaticBody, FIXTURE_DEF);
					body.setUserData("grassCenter");
					physicsWorld.registerPhysicsConnector(new PhysicsConnector(levelObject, body, true, false));
				} else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_GRASS_MID)) {
					levelObject = new Sprite(x, y, grassMidRegion, GlobalState.getVertexBufferObjectManager());
					Body body = PhysicsFactory.createBoxBody(physicsWorld, levelObject, BodyType.StaticBody, FIXTURE_DEF);
					body.setUserData("grassMid");
					physicsWorld.registerPhysicsConnector(new PhysicsConnector(levelObject, body, true, false));
				} else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_GRASS_LEFT)) {
					levelObject = new Sprite(x, y, grassLeftRegion, GlobalState.getVertexBufferObjectManager());
					Body body = PhysicsFactory.createBoxBody(physicsWorld, levelObject, BodyType.StaticBody, FIXTURE_DEF);
					body.setUserData("grassLeft");
					physicsWorld.registerPhysicsConnector(new PhysicsConnector(levelObject, body, true, false));
				} else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_GRASS_RIGHT)) {
					levelObject = new Sprite(x, y, grassRightRegion, GlobalState.getVertexBufferObjectManager());
					Body body = PhysicsFactory.createBoxBody(physicsWorld, levelObject, BodyType.StaticBody, FIXTURE_DEF);
					body.setUserData("grassRight");
					physicsWorld.registerPhysicsConnector(new PhysicsConnector(levelObject, body, true, false));
				} else {
					throw new IllegalArgumentException();
				}

				levelObject.setCullingEnabled(true);
				return levelObject;
			}
		});

		levelLoader.loadLevelFromAsset(GlobalState.getActivity().getAssets(), "level/" + levelID + ".lvl");
	}

	public void load() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/game/");
		gameTextureAtlas = new BuildableBitmapTextureAtlas(GlobalState.getActivity().getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);

		grassRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, GlobalState.getActivity(), "grass.png");
		grassCenterRoundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, GlobalState.getActivity(), "grassCenter_rounded.png");
		grassCenterRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, GlobalState.getActivity(), "grassCenter.png");

		grassMidRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, GlobalState.getActivity(), "grassMid.png");
		grassRightRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, GlobalState.getActivity(), "grassRight.png");
		grassLeftRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, GlobalState.getActivity(), "grassLeft.png");

		try {
			this.gameTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.gameTextureAtlas.load();
		} catch (final TextureAtlasBuilderException e) {
			Debug.e(e);
		}
	}
}
