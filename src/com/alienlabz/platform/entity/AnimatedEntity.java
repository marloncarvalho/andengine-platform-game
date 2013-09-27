package com.alienlabz.platform.entity;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.util.debug.Debug;
import org.andengine.util.texturepack.TexturePack;
import org.andengine.util.texturepack.TexturePackLoader;
import org.andengine.util.texturepack.TexturePackTextureRegion;
import org.andengine.util.texturepack.TexturePackTextureRegionLibrary;
import org.andengine.util.texturepack.exception.TexturePackParseException;

import com.alienlabz.platform.GlobalState;

/**
 * Uma entidade que Ž "animada" ou possui movimentos.
 * 
 * @author Marlon Silva Carvalho
 * @since 0.0.1
 */
abstract public class AnimatedEntity extends Entity {
	protected ITiledTextureRegion mTextureRegion;
	private TexturePack mTexturePack;
	private TexturePackTextureRegionLibrary mTexturePackLibrary;

	public AnimatedEntity(float pX, float pY, String pImage) {
		super(pX, pY, pImage);
	}

	protected void loadResources(String pXML) {
		try {
			mTexturePack = new TexturePackLoader(GlobalState.getActivity().getAssets(), GlobalState.getActivity().getTextureManager()).loadFromAsset(pXML, "");
			mTexturePack.loadTexture();
			mTexturePackLibrary = mTexturePack.getTexturePackTextureRegionLibrary();
		} catch (final TexturePackParseException e) {
			Debug.e(e);
		}

		TexturePackTextureRegion[] obj = new TexturePackTextureRegion[mTexturePackLibrary.getIDMapping().size()];
		for (int i = 0; i < mTexturePackLibrary.getIDMapping().size(); i++) {
			obj[i] = mTexturePackLibrary.get(i);
		}

		mTextureRegion = new TiledTextureRegion(mTexturePack.getTexture(), obj);
	}

	public void createSprite() {
		mSprite = new AnimatedSprite(mX, mY, mTextureRegion, GlobalState.getVertexBufferObjectManager()) {

			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}

		};
		((AnimatedSprite) mSprite).setCurrentTileIndex(17);
	}

}
