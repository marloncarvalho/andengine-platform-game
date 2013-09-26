package com.alienlabz.platform.entity;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;

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
 * Representação do Jogador.
 * 
 * @author Marlon Silva Carvalho
 * @since 0.0.1
 */
public class Player extends AnimatedEntity {

	/**
	 * Estados que o jogador pode se encontrar.
	 *  
	 * @author Marlon Silva Carvalho
	 * @since 0.0.1
	 */
	public enum State {
		WALKING_LEFT, WALKING_RIGHT, JUMPING, DYING, CRUNCHING, IDLE;
	}

	private State state = State.IDLE;
	private static final float JUMP_HEIGHT = 27;
	private Body mBody;
	private int mFootContacts = 0;
	private PhysicsWorld mPhysicsWorld;
	private long[] PLAYER_WALK_DURATION = new long[] { 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200 };
	private long[] PLAYER_JUMP_DURATION = new long[] { 200, 200, 200 };
	private static int JUMP_START_INDEX = 18;
	private static int JUMP_END_INDEX = 20;
	private static int IDLE_INDEX = 17;
	private Fixture mFixtureFeet;

	public Player(float pX, float pY, String pImage) {
		super(pX, pY, pImage);
		loadResources();
	}

	private void createFeet() {
		PolygonShape lPoly = new PolygonShape();
		lPoly.setAsBox(1f, 0.7f, new Vector2(0, -1), 0f);

		FixtureDef pFixtureDef = PhysicsFactory.createFixtureDef(0f, 0f, 0f, true);
		pFixtureDef.shape = lPoly;
		mFixtureFeet = mBody.createFixture(pFixtureDef);
		mFixtureFeet.setSensor(true);
		mFixtureFeet.setUserData("Feet");

		lPoly.dispose();
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

	public void die() {
	}

	public void fire() {
	}

	/**
	 * Verificar se o jogador está pulando ou não.
	 * 
	 * @return True se está. False caso contrário.
	 */
	public boolean isJumping() {
		boolean result = false;

		if (mFootContacts < 1) {
			result = true;
		}

		return result;
	}

	/**
	 * Botar o jogador para Pular.
	 */
	public void jump() {
		if (!isJumping()) {
			state = State.JUMPING;
			mBody.setLinearVelocity(new Vector2(mBody.getLinearVelocity().x, JUMP_HEIGHT));
			((AnimatedSprite) mSprite).animate(PLAYER_JUMP_DURATION, JUMP_START_INDEX, JUMP_END_INDEX, true);
		}
	}

	/**
	 * Parar o jogador na tela.
	 */
	public void stop() {
		((AnimatedSprite) mSprite).stopAnimation();
		state = State.IDLE;
	}

	/**
	 * Mover o jogador em uma direção.
	 * 
	 * @param pX
	 */
	public void walk(float pX) {
		if (pX == 0.0F) {
			if (!isJumping()) {
				((AnimatedSprite) mSprite).setCurrentTileIndex(IDLE_INDEX);
				stop();
			}
		} else {
			if (!isJumping()) {
				if (!((AnimatedSprite) mSprite).isAnimationRunning()) {
					((AnimatedSprite) mSprite).animate(PLAYER_WALK_DURATION, 25, 36, true);
				}
			}
		}

		if (!isJumping() && state.equals(State.JUMPING)) {
			idle();
		}

		if (!isJumping()) {
			if (pX < 0) {
				state = State.WALKING_LEFT;
				mSprite.setScaleX(-1.0f);
			} else {
				state = State.WALKING_RIGHT;
				mSprite.setScaleX(1f);
			}
		}

		mBody.setLinearVelocity(new Vector2(pX * 6, mBody.getLinearVelocity().y));
	}

	/**
	 * Criar a parte de física do jogador.
	 */
	private void createPhysics() {
		mBody = PhysicsFactory.createBoxBody(mPhysicsWorld, getSprite(), BodyType.DynamicBody, PhysicsFactory.createFixtureDef(0, 0, 0));

		mBody.setUserData("Player");
		mBody.setFixedRotation(true);
		createFeet();

		mPhysicsWorld.setContactListener(getFootContactListener());
		mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(getSprite(), mBody, true, false) {

			@Override
			public void onUpdate(float pSecondsElapsed) {
				super.onUpdate(pSecondsElapsed);
			}

		});
	}

	public void idle() {
		state = State.IDLE;
		((AnimatedSprite) mSprite).stopAnimation();
		((AnimatedSprite) mSprite).setCurrentTileIndex(IDLE_INDEX);
	}

	private ContactListener getFootContactListener() {
		ContactListener contactListener = new ContactListener() {

			public void beginContact(Contact contact) {
				final Fixture x1 = contact.getFixtureA();
				final Fixture x2 = contact.getFixtureB();

				if (x1.getBody().getUserData() != null && x2.getBody().getUserData() != null) {
					if (x2.getBody().getUserData().equals("Player") || x1.getBody().getUserData().equals("Player")) {
						if (x1.getUserData() != null && x1.getUserData().equals("Feet")) {
							mFootContacts++;
						}
					}
				}
			}

			public void endContact(Contact contact) {
				final Fixture x1 = contact.getFixtureA();
				final Fixture x2 = contact.getFixtureB();

				if (x1.getBody().getUserData() != null && x2.getBody().getUserData() != null) {
					if (x2.getBody().getUserData().equals("Player") || x1.getBody().getUserData().equals("Player")) {
						if (x1.getUserData() != null && x1.getUserData().equals("Feet")) {
							mFootContacts--;
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
