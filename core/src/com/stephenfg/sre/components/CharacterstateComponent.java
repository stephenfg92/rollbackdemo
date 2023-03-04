package com.stephenfg.sre.components;

import com.badlogic.ashley.core.Component;
import com.stephenfg.sre.data.CharacterState;

public class CharacterstateComponent implements Component{
    public CharacterState state;

    public CharacterstateComponent(CharacterState state){
        this.state = state;
    }
}
