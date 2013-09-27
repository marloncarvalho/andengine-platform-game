package com.alienlabz.platform.world;

import com.alienlabz.platform.entity.Enemy;
import com.alienlabz.platform.entity.Character;
import com.alienlabz.platform.map.MapLoader;

/**
 * Representa��o de um mundo.
 * 
 * Um mundo cont�m todos os objetos de um jogo e mais propriedades que definem como estes objetos
 * se comportar�o dentro dele. Por exemplo, um mundo pode ter gravidade e leis da f�sica que afetam
 * como um objeto se movimenta dentro dele.
 * 
 * @author Marlon Silva Carvalho
 * @since 0.0.1
 */
abstract public class World {
	private Character mPlayer;
	protected MapLoader mMapLoader;

	/**
	 * Definir a gravidade existente neste mundo.
	 * 
	 * @param pXGravity Gravidade no eixo X.
	 * @param pYGravity Gravidade no eixo Y.
	 */
	public abstract void setDefaultGravity(float pXGravity, float pYGravity);

	/**
	 * Criar o mundo de fato. Iniciar os recursos e etc.
	 */
	public abstract void create();

	/**
	 * Liberar os recursos que este mundo usa e liberar mem�ria.
	 */
	public abstract void free();

	public void initialize() {
		create();
	}

	/**
	 * Definir o jogador que interage nesse mundo.
	 * Representa o personagem do usu�rio.
	 * 
	 * @param pPlayer Jogador.
	 */
	public void setPlayer(Character pPlayer) {
		mPlayer = pPlayer;
	}

	/**
	 * Adicionar um inimigo no mundo.
	 * 
	 * @param enemy Inimigo que ser� adicionado ao mundo.
	 */
	public void addEnemy(Enemy enemy) {
	}

	/**
	 * Setar qual o mapa deste mundo.
	 * 
	 * @param pMapLoader 
	 * 
	 */
	public void setMapLoader(MapLoader pMapLoader) {
		mMapLoader = pMapLoader;
	}

	/**
	 * Obter o jogador que est� inserido no jogo.
	 * 
	 * @return Jogador.
	 */
	public Character getPlayer() {
		return mPlayer;
	}

}
