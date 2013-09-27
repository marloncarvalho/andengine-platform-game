package com.alienlabz.platform.entity;

import org.andengine.entity.sprite.AnimatedSprite;

import com.badlogic.gdx.math.Vector2;

/**
 * Responsável por fazer um jogador "pular" dentro do jogo.
 * 
 * @author Marlon Silva Carvalho 
 * @since 0.0.1
 */
public class Jump extends Ability {

	public static final String PARAM_HEIGHT = "JUMP";

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
	 * Verificar se o jogador está pulando ou não.
	 * 
	 * @return True se está. False caso contrário.
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
	 * Realizar a ação de pular do personagem.
	 * 
	 * @param pHeight Altura do pulo.
	 */
	private void jump(float pHeight) {

		// Verificar primeiro se já não está pulando.
		// É necessário primeiro concluir o pulo já em andamento.
		if (!isJumping()) {

			// Realizar o pulo.
			mCharacter.getBody().setLinearVelocity(new Vector2(mCharacter.getBody().getLinearVelocity().x, pHeight));

			// Realizar a animação de pulo.
			((AnimatedSprite) mCharacter.getSprite()).animate(animationDuration, animationStartIndex, animationEndIndex, true);
		}

	}

}
