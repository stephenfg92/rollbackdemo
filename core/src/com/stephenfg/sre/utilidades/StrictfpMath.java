package com.stephenfg.sre.utilidades;

public strictfp class StrictfpMath {
    public static float strictSum(float a, float b) {return a + b;}
    public static float strictSub(float a, float b) {return a - b;}
    public static float strictMult(float a, float b) {return a * b;}
    public static float strictDiv(float a, float b) {return a / b;}
    public static float strictMod(float a, float b) {return a % b;}
    public static int strictSign(float num){return num < .0f ? -1 : 1;}
    public static int strictSign(int num){return num < 0 ? -1 : 1;}
}
