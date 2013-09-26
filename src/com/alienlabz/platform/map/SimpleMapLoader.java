package com.alienlabz.platform.map;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

/**
 * Carregar de Mapas no formato mais simples.
 * 
 * @author Marlon Silva Carvalho
 * @since 0.0.1
 */
public class SimpleMapLoader implements MapLoader {
	private SimpleLevelLoader mLevelLoader;
	private Scene mScene;
	private int mTileWidth;
	private int mTileHeight;
	private Map<String, ITextureRegion> mTextures = new HashMap<String, ITextureRegion>();
	private BuildableBitmapTextureAtlas mTextureAtlas;
	private PhysicsWorld mPhysicsWorld;

	public void unload() {
		mTextureAtlas.unload();
	}

	@Override
	public void load(String pLevelFile, final Scene pScene, PhysicsWorld pPhysicsWorld) {
		mLevelLoader = new SimpleLevelLoader(GlobalState.getVertexBufferObjectManager());
		mPhysicsWorld = pPhysicsWorld;
		mScene = pScene;
		handleTagLevel();
		handleImages();
		handleObjects();
		mLevelLoader.loadLevelFromAsset(GlobalState.getActivity().getAssets(), pLevelFile);
	}

	private void handleObjects() {
		final FixtureDef FIXTURE_DEF = PhysicsFactory.createFixtureDef(0, 0.01f, 0.5f);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("");
		mTextureAtlas = new BuildableBitmapTextureAtlas(GlobalState.getActivity().getTextureManager(), 100, 100, TextureOptions.BILINEAR);

		mLevelLoader.registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>("object") {

			public IEntity onLoadEntity(final String pEntityName, final IEntity pParent, final Attributes pAttributes, final SimpleLevelEntityLoaderData pSimpleLevelEntityLoaderData)
					throws IOException {

				int x = SAXUtils.getIntAttributeOrThrow(pAttributes, "x");
				int y = SAXUtils.getIntAttributeOrThrow(pAttributes, "y");
				String image = SAXUtils.getAttributeOrThrow(pAttributes, "image");
				String type = SAXUtils.getAttributeOrThrow(pAttributes, "type");

				Sprite levelObject = new Sprite(x, y, mTextures.get(image), GlobalState.getVertexBufferObjectManager());
				Body body = PhysicsFactory.createBoxBody(mPhysicsWorld, levelObject, BodyType.StaticBody, FIXTURE_DEF);
				body.setUserData(type);
				mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(levelObject, body, true, false));
				levelObject.setCullingEnabled(true);

				return levelObject;
			}

		});
	}

	/**
	 * Tratar os objetos.
	 */
	private void handleImages() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("");
		mTextureAtlas = new BuildableBitmapTextureAtlas(GlobalState.getActivity().getTextureManager(), 100, 100, TextureOptions.BILINEAR);

		mLevelLoader.registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>("image") {

			public IEntity onLoadEntity(final String pEntityName, final IEntity pParent, final Attributes pAttributes, final SimpleLevelEntityLoaderData pSimpleLevelEntityLoaderData)
					throws IOException {

				String id = SAXUtils.getAttributeOrThrow(pAttributes, "id");
				String src = SAXUtils.getAttributeOrThrow(pAttributes, "src");

				ITextureRegion region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mTextureAtlas, GlobalState.getActivity(), src);

					try {
						mTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
						mTextureAtlas.load();
					} catch (final TextureAtlasBuilderException e) {
						Debug.e(e);
					}

				mTextures.put(id, region);

				return null;
			}

		});
	}

	/**
	 * Tratar a tag que define um LEVEL.
	 */
	private void handleTagLevel() {
		mLevelLoader.registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>(LevelConstants.TAG_LEVEL) {

			public IEntity onLoadEntity(final String pEntityName, final IEntity pParent, final Attributes pAttributes, final SimpleLevelEntityLoaderData pSimpleLevelEntityLoaderData)
					throws IOException {

				mTileWidth = SAXUtils.getIntAttributeOrThrow(pAttributes, "tilewidth");
				mTileHeight = SAXUtils.getIntAttributeOrThrow(pAttributes, "tileheight");

				return mScene;
			}

		});
	}

}
