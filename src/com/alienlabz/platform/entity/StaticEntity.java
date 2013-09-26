package com.alienlabz.platform.entity;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;

import com.alienlabz.platform.GlobalState;

public class StaticEntity extends Entity {
	protected ITextureRegion mTextureRegion;

	public StaticEntity(float pX, float pY, String pImage) {
		super(pX, pY, pImage);
		createSprite();
	}

	/**
	 * Instanciar o Sprite.
	 */
	private void createSprite() {
		mSprite = new Sprite(mX, mY, mTextureRegion, GlobalState.getVertexBufferObjectManager());
	}

	/**
	 * Carregar os recursos desta entidade.
	 * 
	 * @param pBuildableBitmapTextureAtlas Atlas respons‡vel pelo carregamento dos recursos.
	 */
	public void load(BuildableBitmapTextureAtlas pBuildableBitmapTextureAtlas) {
		mTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(pBuildableBitmapTextureAtlas, GlobalState.getActivity(), mImage);
	}

}
