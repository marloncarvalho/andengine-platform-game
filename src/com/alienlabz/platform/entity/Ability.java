package com.alienlabz.platform.entity;

/**
 * Habilidade que pode ser aplicada a um personagem.
 * 
 * @author Marlon Silva Carvalho
 * @since 0.0.1
 */
abstract public class Ability {

	/**
	 * Personagem que ter‡ a habilidade.
	 */
	protected Character mCharacter;

	/**
	 * Executar a habilidade.
	 * 
	 * @param pParams Par‰metros.
	 */
	public abstract void execute(Param[] pParams);

	public abstract boolean isExecuting();

	/**
	 * Definir o personagem que receber‡ a habilidade.
	 * 
	 * @param pCharacter Personagem.
	 */
	public void setCharacter(Character pCharacter) {
		if (pCharacter == null) {
			throw new RuntimeException("Ability: Character cant't be null.");
		}

		mCharacter = pCharacter;
	}

	/**
	 * Criar um par‰metro.
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static Param p(String key, Object value) {
		Param p = new Param();
		p.setKey(key);
		p.setValue(value);

		return p;
	}

	/**
	 * Par‰metro de uma habilidade.
	 * 
	 * @author Marlon Silva Carvalho
	 * @since 0.0.1
	 */
	public static class Param {
		private String key;
		private Object value;

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}

	}

}
