package com.stephenfg.sre.componentes;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class ComponenteAABB implements Component {
    public Vector2 extensao;
    public Vector2 deslocamento; //Em relação ao centro da transformação.
    public boolean colidindo = false;

    public ComponenteAABB(Vector2 extensao){
        this.extensao = extensao;
        this.deslocamento = new Vector2(0, 0);
    }
}
