package com.stephenfg.sre.data.hero;

import com.stephenfg.sre.data.Estado;
import com.stephenfg.sre.utilidades.Intervalo;

import java.util.HashMap;
import java.util.Map;

public class AdventurerData {
    public static String id = "adventurer";
    public static float velocidadeHorizontal = 200;
    public static String caminhoSprite = "adventurer/adventurer.png";
    public static int alturaQuadro = 37;
    public static int larguraQuadro = 50;
    public static int numeroColunas = 7;
    public static int numeroLinhas = 16;
    public static Map<Estado, Intervalo> adventurerAnims = new HashMap<Estado, Intervalo>(){{
        put(Estado.PARADO, new Intervalo(0, 3));
        put(Estado.CORRENDO, new Intervalo(8, 13));
    }};
    public static int quadrosPorSegundo = 8;
}
