package com.alienlabz.platform.resources;

import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.util.debug.Debug;

import com.alienlabz.platform.GlobalState;

/**
 * Responsável por fazer o carregamento de recursos de todo o jogo.
 * Serve como um Pool e concentrador do carregamento de recursos.
 * 
 * Tenta otimizar o carregamento, reutilizando atlas quando necessário.
 * 
 * @author Marlon Silva Carvalho
 * @since 0.0.1
 */
final public class ResourcesPool {
	private static BitmapTextureAtlas mBitmapTextureAtlas;
	private static BuildableBitmapTextureAtlas mBuildableBitmapTextureAtlas;

	/**
	 * Obter um BitmapTextureAtlas do Pool de Recursos.
	 * 
	 * @return BitmapTextureAtlas do Pool
	 */
	public static BitmapTextureAtlas getBitmapTextureAtlas() {
		return mBitmapTextureAtlas;
	}

	public static BuildableBitmapTextureAtlas getBuildableBitmapTextureAtlas() {
		return mBuildableBitmapTextureAtlas;
	}

	/**
	 * Liberar os recursos alocados até o momento.
	 */
	public static void free() {
		mBitmapTextureAtlas.unload();
		mBuildableBitmapTextureAtlas.unload();
	}

	/**
	 * Realizar o alocamento de recursos.
	 */
	public static void allocate() {
		try {
			mBuildableBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			mBuildableBitmapTextureAtlas.load();
			mBitmapTextureAtlas.load();
		} catch (final TextureAtlasBuilderException e) {
			Debug.e(e);
		}
	}

	/**
	 * Inicializar o Pool, reiniciando os atlas quando necessário.
	 * Caso já existam atlas com recursos, descarrega eles.
	 */
	public static void initialize() {
		if (mBitmapTextureAtlas != null) {
			mBitmapTextureAtlas.unload();
			mBitmapTextureAtlas.clearTextureAtlasSources();
		} else {
			mBitmapTextureAtlas = new BitmapTextureAtlas(GlobalState.getActivity().getTextureManager(), 2000, 2000, TextureOptions.BILINEAR);
		}

		if (mBuildableBitmapTextureAtlas != null) {
			mBuildableBitmapTextureAtlas.unload();
			mBuildableBitmapTextureAtlas.clearTextureAtlasSources();
		} else {
			mBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(GlobalState.getActivity().getTextureManager(), 2000, 2000, TextureOptions.BILINEAR);
		}

	}

}
