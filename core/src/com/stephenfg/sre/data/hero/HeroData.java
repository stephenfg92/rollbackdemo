package com.stephenfg.sre.data.hero;

import com.stephenfg.sre.data.EstadoDoPersonagem;
import com.stephenfg.sre.utilidades.Intervalo;

import java.util.HashMap;
import java.util.Map;

public class HeroData {
    public static float velocidadeHorizontal = 200;
    public static String caminhoSprite = "warrior/sheet/warrior.png";
    public static int alturaQuadro = 69;
    public static int larguraQuadro = 44;
    public static int numeroColunas = 6;
    public static int numeroLinhas = 17;
    public static Map<EstadoDoPersonagem, Intervalo> heroAnims = new HashMap<EstadoDoPersonagem, Intervalo>(){{
        put(EstadoDoPersonagem.PARADO, new Intervalo(0, 5));
        put(EstadoDoPersonagem.CORRENDO, new Intervalo(6, 13));
    }};
    public static int quadrosPorSegundo = 10;
}
