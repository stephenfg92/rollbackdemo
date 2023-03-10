package com.stephenfg.sre.components;

import com.badlogic.ashley.core.Component;

public class FacingComponent implements Component {
    public boolean facingLeft;

    public FacingComponent(){
        facingLeft = false;
    }

    public FacingComponent(Boolean facingLeft){
        this.facingLeft = facingLeft;
    }
}
