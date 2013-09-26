package com.alienlabz.platform.map;

import org.andengine.entity.scene.Scene;
import org.andengine.extension.physics.box2d.PhysicsWorld;

/**
 * Comportamento de Carregadores de Mapa.
 * 
 * @author Marlon Silva Carvalho
 * @since 0.0.1
 */
public interface MapLoader {

	void load(String levelFile, Scene scene, PhysicsWorld pPhysicsWorld);

	void unload();

}
