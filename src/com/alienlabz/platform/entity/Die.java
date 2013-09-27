package com.alienlabz.platform.entity;

import org.andengine.entity.sprite.AnimatedSprite;

/**
 * Habilidade de "Morrer".
 * 
 * @author Marlon Silva Carvalho
 * @since 0.0.1
 */
public class Die extends Ability {

	/**
	 * Ínidice inicial da animação.
	 */
	public int animationStartIndex;

	/**
	 * Índice final da animação.
	 */
	public int animationEndIndex;

	/**
	 * Duração de cada frame da animação.
	 */
	public long[] animationDuration;

	/**
	 * Se está morto ou não.
	 */
	private boolean isDead = false;

	@Override
	public void execute(Param[] pParams) {
		if (!isDead) {
			((AnimatedSprite) mCharacter.getSprite()).animate(animationDuration, animationStartIndex, animationEndIndex, false);
			isDead = true;
		}
	}

	@Override
	public boolean isExecuting() {
		return isDead;
	}

}
