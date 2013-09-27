package com.alienlabz.platform.entity;

import org.andengine.entity.sprite.AnimatedSprite;

import com.badlogic.gdx.math.Vector2;

/**
 * Respons�vel por fazer um jogador "andar" dentro da fase no jogo.
 * 
 * @author Marlon Silva Carvalho 
 * @since 0.0.1
 */
public class Walk extends Ability {
	public static final String PARAM_VELOCITY = "VELOCITY";

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
	 * �ltima dire��o que o personagem estava andando.
	 */
	private Direction mDirection;

	/**
	 * Informa se o personagem se encontra andando ou n�o.
	 */
	private boolean isWalking = false;

	/**
	 * Enumera��o que define as poss�veis dire��es que o personagem pode andar.
	 * 
	 * @author Marlon Silva Carvalho
	 * @since 0.0.1
	 */
	public enum Direction {
		LEFT, RIGHT, STOPPED;
	}

	/**
	 * Obter a dire��o conforme a velocidade informada.
	 * 
	 * Velocidades com valor negativo significa uma dire��o para a esquerda.
	 * Velocidades com valor positivo significa uma dire��o para a direita.
	 * 
	 * @param pVelocity Velocidade.
	 * @return Dire��o.
	 */
	public Direction getDirection(float pVelocity) {
		Direction result = Direction.STOPPED;

		if (pVelocity < 0) {
			result = Direction.LEFT;
		} else if (pVelocity > 0) {
			result = Direction.RIGHT;
		}

		return result;
	}

	@Override
	public void execute(Param[] pParams) {
		walk((Float) pParams[0].getValue());
	}

	@Override
	public boolean isExecuting() {
		return isWalking;
	}

	private boolean wasJumping = false;

	/**
	 * Fazer o Personagem se deslocar em uma dire��o e com uma determinada velocidade.
	 * 
	 * @param pVelocity Velocidade do movimento.
	 */
	private void walk(float pVelocity) {
		boolean isJumper = mCharacter.isAbleTo(Jump.class);
		boolean isJumping = false;

		// Baseado na velocidade, obter a dire��o.
		mDirection = getDirection(pVelocity);

		// Verificar se o personagem est� de fato andando ou  "sem dire��o", que significa parado.
		if (!mDirection.equals(Direction.STOPPED)) {

			// Verificar se o personagem pode pular tamb�m.
			if (isJumper) {
				Jump lJump = (Jump) mCharacter.getAbility(Jump.class);
				isJumping = lJump.isExecuting();
			}

			// Se n�o est� pulando, vamos animar o personagem.
			// Mas antes precisa verificar se tamb�m j� n�o est� andando, para n�o repetir a anima��o.
			if (!isJumping) {
				if (!isWalking) {
					doAnimation();
					isWalking = true;
				} else if (wasJumping) {
					wasJumping = false;
					doAnimation();
				}
			} else {
				wasJumping = true;
			}

		} else {
			isWalking = false;
			mCharacter.idle();
		}

		// Mudar a orienta��o do Sprite conforme a dire��o que est� andando.
		if (mDirection.equals(Direction.LEFT)) {
			mCharacter.getSprite().setScaleX(-1.0f);
		} else if (mDirection.equals(Direction.RIGHT)) {
			mCharacter.getSprite().setScaleX(1f);
		}

		// Realizar o movimento do personagem na tela.
		mCharacter.getBody().setLinearVelocity(new Vector2(pVelocity * 6, mCharacter.getBody().getLinearVelocity().y));
	}

	/**
	 * Realizar a anima��o do Sprite.
	 */
	private void doAnimation() {
		AnimatedSprite lAnimatedSprite = (AnimatedSprite) mCharacter.getSprite();
		lAnimatedSprite.animate(animationDuration, animationStartIndex, animationEndIndex, true);
	}

}