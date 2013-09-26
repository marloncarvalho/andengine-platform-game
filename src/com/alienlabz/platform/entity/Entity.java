package com.alienlabz.platform.entity;

import org.andengine.entity.sprite.Sprite;

/**
 * Representa uma entidade no jogo.
 * 
 * @author Marlon Silva Carvalho
 * @since 0.0.1
 */
abstract public class Entity {
	protected String mImage;
	protected float mX;
	protected float mY;
	protected Sprite mSprite;

	public Entity(float pX, float pY, String pImage) {
		mX = pX;
		mY = pY;
		mImage = pImage;
	}

	/**
	 * Obter a imagem associada a esta entidade.
	 * 
	 * @return Imagem.
	 */
	public String getImage() {
		return mImage;
	}

	/**
	 * Obter o Sprite associado a esta entidade.
	 * 
	 * @return Sprite.
	 */
	public Sprite getSprite() {
		return mSprite;
	}

}
