package com.stephenfg.sre.data.hero;

import com.stephenfg.sre.data.CharacterState;
import com.stephenfg.sre.util.Range;

import java.util.HashMap;
import java.util.Map;

public class HeroData {
    public static float hSpeed = 200;
    public static String spritePath = "warrior/sheet/warrior.png";
    public static int spriteFrameH = 69;
    public static int spriteFrameW = 44;
    public static Map<CharacterState, Range> heroAnims = new HashMap<CharacterState, Range>(){{
        put(CharacterState.IDLE, new Range(0, 5));
    }};
    public static int animFps = 10;
}