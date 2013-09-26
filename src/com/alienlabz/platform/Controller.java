package com.alienlabz.platform;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl.IAnalogOnScreenControlListener;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;

public class Controller implements IAnalogOnScreenControlListener {
	private BitmapTextureAtlas mOnScreenControlTextureAtlas;
	private BitmapTextureAtlas mOnScreenControlTextureAtlasButtons;
	private TextureRegion mOnScreenControlBaseTextureRegion;
	private TextureRegion mOnScreenControlKnobTextureRegion;
	private TextureRegion mButtonATextureRegion;
	private AnalogOnScreenControl mAnalogOnScreenControl;
	private Scene mScene;
	private IControllerListener mControllerListener;

	public Controller(Scene pScene) {
		mScene = pScene;
		loadResources();
		loadControllers();
		loadButtons();
	}

	private void loadButtons() {

		Sprite sprite = new Sprite(710, 90, mButtonATextureRegion, GlobalState.getVertexBufferObjectManager()) {

			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()) {
					mControllerListener.onButtonAPressed();
				}
				return true;
			}

		};
		mAnalogOnScreenControl.attachChild(sprite);
		mAnalogOnScreenControl.registerTouchArea(sprite);
	}

	public void free() {
		mOnScreenControlTextureAtlas.unload();
	}

	private void loadResources() {
		mOnScreenControlTextureAtlas = new BitmapTextureAtlas(GlobalState.getActivity().getTextureManager(), 256, 128, TextureOptions.BILINEAR);

		mOnScreenControlBaseTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mOnScreenControlTextureAtlas, GlobalState.getActivity(), "gfx/game/onscreen_control_base.png", 0, 0);
		mOnScreenControlKnobTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mOnScreenControlTextureAtlas, GlobalState.getActivity(), "gfx/game/onscreen_control_knob.png", 128,
				0);
		mOnScreenControlTextureAtlas.load();

		mOnScreenControlTextureAtlasButtons = new BitmapTextureAtlas(GlobalState.getActivity().getTextureManager(), 128, 128, TextureOptions.BILINEAR);
		mButtonATextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mOnScreenControlTextureAtlasButtons, GlobalState.getActivity(), "gfx/buttonA.png", 0, 0);
		mOnScreenControlTextureAtlasButtons.load();
	}

	public void loadControllers() {
		mAnalogOnScreenControl = new AnalogOnScreenControl(70, 80, GlobalState.getCamera(), mOnScreenControlBaseTextureRegion, mOnScreenControlKnobTextureRegion, 0.1f, 200,
				GlobalState.getVertexBufferObjectManager(), this);
		mAnalogOnScreenControl.getControlBase().setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		mAnalogOnScreenControl.getControlBase().setAlpha(0.5f);
		mAnalogOnScreenControl.refreshControlKnobPosition();

		mScene.setChildScene(mAnalogOnScreenControl);
	}

	public void setListener(IControllerListener pListener) {
		mControllerListener = pListener;
	}

	@Override
	public void onControlChange(BaseOnScreenControl pBaseOnScreenControl, float pValueX, float pValueY) {
		mControllerListener.onMove(pValueX, pValueY);
	}

	@Override
	public void onControlClick(AnalogOnScreenControl pAnalogOnScreenControl) {
	}

	public interface IControllerListener {
		void onButtonAPressed();

		void onButtonBPressed();

		void onMove(float pX, float pY);

	}

}
