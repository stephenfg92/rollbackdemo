package com.stephenfg.sre.componentes;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class ComponenteColisorCaixa implements Component {
    public int largura;
    public int altura;
    public Vector2 deslocamento;

    public ComponenteColisorCaixa(int largura, int altura){
        this.largura = largura;
        this.altura = altura;
        this.deslocamento = new Vector2();
    }

    public ComponenteColisorCaixa(int largura, int altura, Vector2 deslocamento){
        this.largura = largura;
        this.altura = altura;
        this.deslocamento = deslocamento;
    }
}
