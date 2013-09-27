package com.alienlabz.platform.entity;

import org.andengine.entity.sprite.AnimatedSprite;

import com.badlogic.gdx.math.Vector2;

/**
 * Respons�vel por fazer um jogador "pular" dentro do jogo.
 * 
 * @author Marlon Silva Carvalho 
 * @since 0.0.1
 */
public class Jump extends Ability {

	public static final String PARAM_HEIGHT = "JUMP";

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
	 * Verificar se o jogador est� pulando ou n�o.
	 * 
	 * @return True se est�. False caso contr�rio.
	 */
	private boolean isJumping() {
		boolean result = false;

		if (mCharacter.getFootContacts() <= 0) {
			result = true;
		}

		return result;
	}

	@Override
	public boolean isExecuting() {
		return isJumping();
	}

	@Override
	public void execute(Param[] pParams) {
		jump((Float) pParams[0].getValue());
	}

	@Override
	public void setCharacter(Character pCharacter) {
		super.setCharacter(pCharacter);
	}

	/**
	 * Realizar a a��o de pular do personagem.
	 * 
	 * @param pHeight Altura do pulo.
	 */
	private void jump(float pHeight) {
		boolean isDeath = mCharacter.isAbleTo(Die.class);

		// Verificar se o personagem � "morr�vel".
		if (isDeath) {
			Die lDie = (Die) mCharacter.getAbility(Die.class);

			// Se est� morto, cancela tudo.
			if (lDie.isExecuting()) {
				mCharacter.getBody().setLinearVelocity(new Vector2(0, 0));
				return;
			}

		}

		// Verificar primeiro se j� n�o est� pulando.
		// � necess�rio primeiro concluir o pulo j� em andamento.
		if (!isJumping()) {

			// Realizar o pulo.
			mCharacter.getBody().setLinearVelocity(new Vector2(mCharacter.getBody().getLinearVelocity().x, pHeight));

			// Realizar a anima��o de pulo.
			((AnimatedSprite) mCharacter.getSprite()).animate(animationDuration, animationStartIndex, animationEndIndex, true);
		}

	}

}
