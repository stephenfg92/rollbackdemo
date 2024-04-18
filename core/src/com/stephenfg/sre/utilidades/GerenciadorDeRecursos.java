package com.stephenfg.sre.utilidades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.stephenfg.sre.componentes.ComponenteSpritesheet;

import java.util.HashMap;
import java.util.Map;

public class GerenciadorDeRecursos {
    Map<String, Texture> texturas = new HashMap<>();
    Map<String, TextureRegion[]> texRegionAr = new HashMap<>();

    public GerenciadorDeRecursos() {};

    public void adicionarTextura(String id, String caminho) {
        Texture t = new Texture(caminho);
        texturas.put(id, t);
    }

    public Texture obterTextura(String id) {
        return texturas.get(id);
    }

    public void adicionarTexRegionAr(String id, TextureRegion[] tra) {
        texRegionAr.put(id, tra);
    }

    public TextureRegion[] obterTextureRegionAr(ComponenteSpritesheet sprite) {
        String id = sprite.id;
        TextureRegion[] tr = texRegionAr.get(id);

        if (tr == null){
            Texture t = obterTextura(id);
            tr = CriarArrayTextureRegion.criar(t, sprite);
            adicionarTexRegionAr(id, tr);
        }

        return tr;
    }

    public void dispose(){
        for (Texture t : texturas.values()) {
            t.dispose();
        }
    }
}
