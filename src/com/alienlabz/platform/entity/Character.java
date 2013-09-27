package com.alienlabz.platform.entity;

import java.util.HashMap;
import java.util.Map;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.util.debug.Debug;

import com.alienlabz.platform.entity.Ability.Param;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;

/**
 * Representa um Personagem do Jogo.
 * 
 * @author Marlon Silva Carvalho
 * @since 0.0.1
 */
abstract public class Character extends AnimatedEntity {
	private Map<String, Ability> mAbilities = new HashMap<String, Ability>();
	protected Body mBody;
	protected PhysicsWorld mPhysicsWorld;
	protected static int IDLE_INDEX = 17;

	/**
	 * Fixture dos pés do personagem.
	 */
	private Fixture mFixtureFeet;

	/**
	 * Contador da quantidade de toques dos pés em chãos.
	 */
	private int mFootContacts = 0;

	/**
	 * Construtor do Personagem.
	 * 
	 * @param pX Posição no eixo X.
	 * @param pY Posição no eixo Y.
	 * @param pImage Imagem.
	 */
	public Character(float pX, float pY, String pImage) {
		super(pX, pY, pImage);
		loadResources("Hero1.xml");
	}

	/**
	 * Tornar este personagem hábil para executar algo.
	 * 
	 * @param pAbility Habilidade que o personagem terá.
	 */
	public void ableTo(Ability pAbility) {
		pAbility.setCharacter(this);
		mAbilities.put(pAbility.getClass().getSimpleName(), pAbility);
	}

	/**
	 * Obter uma determinada habilidade.
	 * 
	 * @param pClass Classe da habilidade.
	 * @return Habilidade.
	 */
	public Ability getAbility(Class<? extends Ability> pClass) {
		Ability result = null;

		if (isAbleTo(pClass)) {
			result = mAbilities.get(pClass.getSimpleName());
		}

		return result;
	}

	/**
	 * Verificar se o personagem tem uma determinada habilidade.
	 * 
	 * @param pClass Classe da habilidade.
	 * @return True se é hábil.
	 */
	public boolean isAbleTo(Class<? extends Ability> pClass) {
		return mAbilities.containsKey(pClass.getSimpleName());
	}

	/**
	 * Executar uma determinada habilidade neste personagem.
	 * 
	 * @param pClass Habilidade a ser executada.
	 * @param pParams Parâmetros para a habilidade.
	 */
	public void perform(Class<? extends Ability> pClass, Param... pParams) {
		if (mAbilities.containsKey(pClass.getSimpleName())) {
			Ability lAbility = mAbilities.get(pClass.getSimpleName());
			lAbility.execute(pParams);
		} else {
			Debug.i("Character " + getClass().getSimpleName() + " doesn't have ability " + pClass.getSimpleName() + ". Won't be executed.");
		}
	}

	/**
	 * Definir as características da física do mundo em que o jogador está inserido.
	 * 
	 * @param pPhysicsWorld
	 */
	public void setPhysicsWorld(PhysicsWorld pPhysicsWorld) {
		mPhysicsWorld = pPhysicsWorld;
		createPhysics();
	}

	/**
	 * Criar a parte de física do jogador.
	 */
	private void createPhysics() {
		mBody = PhysicsFactory.createBoxBody(mPhysicsWorld, getSprite(), BodyType.DynamicBody, PhysicsFactory.createFixtureDef(0, 0, 0));

		mBody.setUserData("Player");
		mBody.setFixedRotation(true);
		createFeet();

		mPhysicsWorld.setContactListener(getContactListener());
		mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(getSprite(), mBody, true, false) {

			@Override
			public void onUpdate(float pSecondsElapsed) {
				super.onUpdate(pSecondsElapsed);
			}

		});
	}

	public void idle() {
		((AnimatedSprite) mSprite).stopAnimation();
		((AnimatedSprite) mSprite).setCurrentTileIndex(IDLE_INDEX);
	}

	public Body getBody() {
		return mBody;
	}

	public int getFootContacts() {
		return mFootContacts;
	}

	/**
	 * Criar a superfície do pé no personagem.
	 */
	private void createFeet() {
		PolygonShape lPoly = new PolygonShape();
		lPoly.setAsBox(1f, 0.7f, new Vector2(0, -1), 0f);

		FixtureDef pFixtureDef = PhysicsFactory.createFixtureDef(0f, 0f, 0f, true);
		pFixtureDef.shape = lPoly;
		mFixtureFeet = mBody.createFixture(pFixtureDef);
		mFixtureFeet.setSensor(true);
		mFixtureFeet.setUserData("Feet");

		lPoly.dispose();

		mPhysicsWorld.setContactListener(getContactListener());
	}

	/**
	 * Colocar o Listener de contato para detectar toques dos pés, morte e etc.
	 * 
	 * @return Listener de contatos.
	 */
	private ContactListener getContactListener() {
		ContactListener contactListener = new ContactListener() {

			public void beginContact(Contact contact) {
				final Fixture x1 = contact.getFixtureA();
				final Fixture x2 = contact.getFixtureB();

				if (x1.getBody().getUserData() != null && x2.getBody().getUserData() != null) {
					if (x2.getBody().getUserData().equals("Player") || x1.getBody().getUserData().equals("Player")) {

						// Detectando o toque do personagem em lugares que causam "morte".
						if (x2.getBody().getUserData().equals("Death") || x1.getBody().getUserData().equals("Death")) {
							Character.this.perform(Die.class);
						}

						// Detectando o toque dos pés no chão.
						if (x2.getBody().getUserData().equals("Ground") || x1.getBody().getUserData().equals("Ground")) {
							if (x1.getUserData() != null && x1.getUserData().equals("Feet")) {
								mFootContacts++;
							}
						}

					}
				}
			}

			public void endContact(Contact contact) {
				final Fixture x1 = contact.getFixtureA();
				final Fixture x2 = contact.getFixtureB();

				if (x1.getBody().getUserData() != null && x2.getBody().getUserData() != null) {
					if (x2.getBody().getUserData().equals("Player") || x1.getBody().getUserData().equals("Player")) {

						// Detectando que o personagem tirou os "pés do chão".
						if (x2.getBody().getUserData().equals("Ground") || x1.getBody().getUserData().equals("Ground")) {
							if (x1.getUserData() != null && x1.getUserData().equals("Feet")) {
								mFootContacts--;
							}
						}

					}
				}
			}

			public void preSolve(Contact contact, Manifold oldManifold) {

			}

			public void postSolve(Contact contact, ContactImpulse impulse) {

			}

		};

		return contactListener;
	}

}