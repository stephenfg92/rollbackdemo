package com.stephenfg.sre.componentes;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ComponenteSpritesheet implements Component {
    public TextureRegion[] regioes;
    public int regiaoAtual = 0;
    public boolean espelharX = false;
    public boolean espelharY = false;

    public ComponenteSpritesheet(TextureRegion[] regioes) {
        this.regioes = regioes;
    }
}
