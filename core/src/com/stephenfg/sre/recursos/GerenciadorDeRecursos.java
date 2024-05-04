package com.stephenfg.sre.recursos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.stephenfg.sre.data.Estado;
import com.stephenfg.sre.utilidades.Intervalo;

import java.util.HashMap;
import java.util.Map;

public class GerenciadorDeRecursos {
    Map<String, Textura> texturas = new HashMap<>();

    public GerenciadorDeRecursos() {};

    public void adicionarTextura(
            String id,
            String caminho,
            int larguraQuadro,
            int alturaQuadro,
            int numeroLinhas,
            int numeroColunas,
            Map<Estado, Intervalo> animacoes,
            int quadrosPorSegundo) {
        Texture t = new Texture(caminho);
        Textura textura = new Textura(id, t, larguraQuadro, alturaQuadro,numeroLinhas, numeroColunas, animacoes, quadrosPorSegundo);
        texturas.put(id, textura);
    }

    public void adicionarTextura(String id, String caminho, int larguraQuadro, int alturaQuadro) {
        Texture t = new Texture(caminho);
        Textura textura = new Textura(id, t, larguraQuadro, alturaQuadro);
        texturas.put(id, textura);
    }

    public Textura obterTextura(String id) {
        return texturas.get(id);
    }


    public void dispose(){
        for (Textura textura : texturas.values()) {
            textura.t.dispose();
        }
    }
}
