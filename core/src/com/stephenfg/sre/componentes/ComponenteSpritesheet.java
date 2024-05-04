package com.stephenfg.sre.componentes;

import com.badlogic.ashley.core.Component;

public class ComponenteSpritesheet implements Component {
    public String id;
    public int regiaoAtual = 0;
    public boolean espelharX = false;
    public boolean espelharY = false;
    public int origemX = 0;
    public int origemY = 0;

    public ComponenteSpritesheet(String id) {
        this.id = id;
    }
}
