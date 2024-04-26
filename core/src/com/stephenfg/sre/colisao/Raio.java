package com.stephenfg.sre.colisao;

import com.badlogic.gdx.math.Vector2;

public class Raio {
    public Vector2 origem;
    public Vector2 direcao;

    public Raio(Vector2 origem, Vector2 direcao){
        this.origem = origem;
        this.direcao = direcao;
    }

    public boolean raioAtingeCaixa(Vector2 posicaoCaixa, Vector2 tamanhoCaixa){
        Vector2 reciprocoDirecao = new Vector2(1.0f / this.direcao.x, 1.0f / this.direcao.y);

        float pontoEntradaX, pontoSaidaX, pontoEntradaY, pontoSaidaY;

        if (this.direcao.x >= 0) {
            pontoEntradaX = (posicaoCaixa.x - this.origem.x) * reciprocoDirecao.x;
            pontoSaidaX = (posicaoCaixa.x + tamanhoCaixa.x - this.origem.x) * reciprocoDirecao.x;
        } else {
            pontoEntradaX = (posicaoCaixa.x + tamanhoCaixa.x - this.origem.x) * reciprocoDirecao.x;
            pontoSaidaX = (posicaoCaixa.x - this.origem.x) * reciprocoDirecao.x;
        }

        if (this.direcao.y >= 0) {
            pontoEntradaY = (posicaoCaixa.y - this.origem.y) * reciprocoDirecao.y;
            pontoSaidaY = (posicaoCaixa.y + tamanhoCaixa.y - this.origem.y) * reciprocoDirecao.y;
        } else {
            pontoEntradaY = (posicaoCaixa.y + tamanhoCaixa.y - this.origem.y) * reciprocoDirecao.y;
            pontoSaidaY = (posicaoCaixa.y - this.origem.y) * reciprocoDirecao.y;
        }

        if ((pontoEntradaX > pontoSaidaY) || (pontoEntradaY > pontoSaidaX))
            return false;

        pontoEntradaX = Math.max(pontoEntradaX, pontoEntradaY); //
        pontoSaidaX = Math.min(pontoSaidaX, pontoSaidaY);

        return pontoSaidaX > 0;
    }
}
