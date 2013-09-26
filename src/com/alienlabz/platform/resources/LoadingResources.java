package com.alienlabz.platform.resources;

import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;

import android.graphics.Color;

import com.alienlabz.platform.GlobalState;

/**
 * Respons‡vel por carregar os recursos da cena de Loading.
 * 
 * @author Marlon Silva Carvalho
 * @since 0.0.1
 */
public class LoadingResources {
	public static Font font;

	public void load() {
		FontFactory.setAssetBasePath("fonts/");

		ITexture mainFontTexture = new BitmapTextureAtlas(GlobalState.getActivity().getTextureManager(), 256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		font = FontFactory
				.createStrokeFromAsset(GlobalState.getActivity().getFontManager(), mainFontTexture, GlobalState.getActivity().getAssets(),
						"Abadi MT Condensed Extra Bold.ttf", 50, true, Color.WHITE, 2, Color.BLACK);

		font.load();
	}

}