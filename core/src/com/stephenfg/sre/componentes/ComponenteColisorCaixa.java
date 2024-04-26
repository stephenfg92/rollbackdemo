package com.stephenfg.sre.componentes;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class ComponenteColisorCaixa implements Component {
    public Vector2 dimensoes;
    public Vector2 deslocamento;
    public boolean colidindo = false;
    public ComponenteColisorCaixa(int largura, int altura){
        this.dimensoes = new Vector2(largura, altura);
        this.deslocamento = new Vector2();
    }

    public ComponenteColisorCaixa(int largura, int altura, Vector2 deslocamento){
        this.dimensoes = new Vector2(largura, altura);
        this.deslocamento = deslocamento;
    }
}
