package com.alienlabz.platform;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;

/**
 * Mantém estados globais que podem ser usados em diversos locais do jogo.
 * 
 * @author Marlon Silva Carvalho
 * @since 0.0.1
 */
final public class GlobalState {
	private static Engine engine;
	private static BaseGameActivity activity;
	private static BoundCamera camera;
	private static VertexBufferObjectManager vertexBufferObjectManager;

	public static void extract(BaseGameActivity pActivity, BoundCamera pCamera) {
		setEngine(pActivity.getEngine());
		setActivity(pActivity);
		setCamera(pCamera);
		setVertexBufferObjectManager(pActivity.getVertexBufferObjectManager());
	}

	public static Engine getEngine() {
		return engine;
	}

	public static void setEngine(Engine engine) {
		GlobalState.engine = engine;
	}

	public static BaseGameActivity getActivity() {
		return activity;
	}

	public static void setActivity(BaseGameActivity activity) {
		GlobalState.activity = activity;
	}

	public static BoundCamera getCamera() {
		return camera;
	}

	public static void setCamera(BoundCamera camera) {
		GlobalState.camera = camera;
	}

	public static VertexBufferObjectManager getVertexBufferObjectManager() {
		return vertexBufferObjectManager;
	}

	public static void setVertexBufferObjectManager(VertexBufferObjectManager vertexBufferObjectManager) {
		GlobalState.vertexBufferObjectManager = vertexBufferObjectManager;
	}

}
