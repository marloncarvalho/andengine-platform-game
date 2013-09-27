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
	 * �nidice inicial da anima��o.
	 */
	public int animationStartIndex;

	/**
	 * �ndice final da anima��o.
	 */
	public int animationEndIndex;

	/**
	 * Dura��o de cada frame da anima��o.
	 */
	public long[] animationDuration;

	/**
	 * Se est� morto ou n�o.
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
