package com.stephenfg.sre.utilidades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

public class DespachanteDeTexturas {
    Array<Texture> texturas = new Array<Texture>();

    public void adicionarTextura(Texture textura){
        texturas.add(textura);
    }

    public void despacharTexturas(){
        for (Texture t : texturas){
            t.dispose();
        }
    }
}
