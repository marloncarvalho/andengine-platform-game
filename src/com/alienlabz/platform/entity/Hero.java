package com.alienlabz.platform.entity;

/**
 * Este Ž o Heroi do nosso jogo!
 * 
 * @author Marlon Silva Carvalho
 * @since 0.0.1
 */
public class Hero extends Character {
	public static final float JUMP_HEIGHT = 32F;

	public Hero(float pX, float pY) {
		super(pX, pY, "gfx/game/Player.png");
		setAbilities();
	}

	private void setAbilities() {
		Jump lJump = new Jump();
		lJump.animationDuration = new long[] { 200, 200, 200 };
		lJump.animationStartIndex = 18;
		lJump.animationEndIndex = 20;
		ableTo(lJump);

		Walk lWalk = new Walk();
		lWalk.animationDuration = new long[] { 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200 };
		lWalk.animationStartIndex = 25;
		lWalk.animationEndIndex = 36;
		ableTo(lWalk);

		Die lDie = new Die();
		lDie.animationDuration = new long[] { 200, 200, 200, 200, 200 };
		lDie.animationStartIndex = 10;
		lDie.animationEndIndex = 14;
		ableTo(lDie);
	}

}
