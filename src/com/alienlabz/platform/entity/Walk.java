package com.alienlabz.platform.entity;

import org.andengine.entity.sprite.AnimatedSprite;

import com.badlogic.gdx.math.Vector2;

/**
 * Responsável por fazer um jogador "andar" dentro da fase no jogo.
 * 
 * @author Marlon Silva Carvalho 
 * @since 0.0.1
 */
public class Walk extends Ability {
	public static final String PARAM_VELOCITY = "VELOCITY";

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
	 * Última direção que o personagem estava andando.
	 */
	private Direction mDirection;

	/**
	 * Informa se o personagem se encontra andando ou não.
	 */
	private boolean isWalking = false;

	/**
	 * Enumeração que define as possíveis direções que o personagem pode andar.
	 * 
	 * @author Marlon Silva Carvalho
	 * @since 0.0.1
	 */
	public enum Direction {
		LEFT, RIGHT, STOPPED;
	}

	/**
	 * Obter a direção conforme a velocidade informada.
	 * 
	 * Velocidades com valor negativo significa uma direção para a esquerda.
	 * Velocidades com valor positivo significa uma direção para a direita.
	 * 
	 * @param pVelocity Velocidade.
	 * @return Direção.
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
	 * Fazer o Personagem se deslocar em uma direção e com uma determinada velocidade.
	 * 
	 * @param pVelocity Velocidade do movimento.
	 */
	private void walk(float pVelocity) {
		boolean isJumper = mCharacter.isAbleTo(Jump.class);
		boolean isJumping = false;

		// Baseado na velocidade, obter a direção.
		mDirection = getDirection(pVelocity);

		// Verificar se o personagem está de fato andando ou  "sem direção", que significa parado.
		if (!mDirection.equals(Direction.STOPPED)) {

			// Verificar se o personagem pode pular também.
			if (isJumper) {
				Jump lJump = (Jump) mCharacter.getAbility(Jump.class);
				isJumping = lJump.isExecuting();
			}

			// Se não está pulando, vamos animar o personagem.
			// Mas antes precisa verificar se também já não está andando, para não repetir a animação.
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

		// Mudar a orientação do Sprite conforme a direção que está andando.
		if (mDirection.equals(Direction.LEFT)) {
			mCharacter.getSprite().setScaleX(-1.0f);
		} else if (mDirection.equals(Direction.RIGHT)) {
			mCharacter.getSprite().setScaleX(1f);
		}

		// Realizar o movimento do personagem na tela.
		mCharacter.getBody().setLinearVelocity(new Vector2(pVelocity * 6, mCharacter.getBody().getLinearVelocity().y));
	}

	/**
	 * Realizar a animação do Sprite.
	 */
	private void doAnimation() {
		AnimatedSprite lAnimatedSprite = (AnimatedSprite) mCharacter.getSprite();
		lAnimatedSprite.animate(animationDuration, animationStartIndex, animationEndIndex, true);
	}

}