package com.stephenfg.sre;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.stephenfg.sre.componentes.ComponenteAnimacao;
import com.stephenfg.sre.componentes.ComponenteColisorCaixa;
import com.stephenfg.sre.componentes.ComponenteDebug;
import com.stephenfg.sre.componentes.ComponenteEstado;
import com.stephenfg.sre.componentes.ComponenteOrientacao;
import com.stephenfg.sre.componentes.ComponenteComando;
import com.stephenfg.sre.componentes.ComponenteCorpoRigido;
import com.stephenfg.sre.componentes.ComponenteSpritesheet;
import com.stephenfg.sre.componentes.ComponenteTransformacao;
import com.stephenfg.sre.data.EstadoDoPersonagem;
import com.stephenfg.sre.data.hero.HeroData;
import com.stephenfg.sre.eventos.BarramentoDeEventos;
import com.stephenfg.sre.eventos.EventoMudancaDeEstado;
import com.stephenfg.sre.sistemas.SistemaDeAnimacao;
import com.stephenfg.sre.sistemas.SistemaDeOrientacao;
import com.stephenfg.sre.sistemas.SistemaDesenhoDebug;
import com.stephenfg.sre.sistemas.SistemaEstadoDePersonagem;
import com.stephenfg.sre.sistemas.SistemaComando;
import com.stephenfg.sre.sistemas.SistemaMovimento;
import com.stephenfg.sre.sistemas.SistemaDesenho;
import com.stephenfg.sre.utilidades.GerenciadorDeRecursos;

public class SREGame extends ApplicationAdapter {
	PooledEngine engine;
	GerenciadorDeRecursos recursos = new GerenciadorDeRecursos();

	@Override
	public void create () {
		OrthographicCamera camera = new OrthographicCamera();
		camera.setToOrtho(false, 640, 480);
		BitmapFont font = new BitmapFont();
		BarramentoDeEventos barramentoDeEventos = new BarramentoDeEventos();

		carregarRecursos();
		engine = inicializarEngine(barramentoDeEventos, camera, font);
		inicializarEntidades();

	}

	private void carregarRecursos() {
		recursos.adicionarTextura(HeroData.id, HeroData.caminhoSprite);
		recursos.adicionarTextura("tree", "tree/tree.png");
	}

	public PooledEngine inicializarEngine(BarramentoDeEventos barramento, OrthographicCamera camera, BitmapFont font) {
		engine = new PooledEngine();
		engine.addSystem(new SistemaComando(0));
		engine.addSystem(new SistemaEstadoDePersonagem(barramento));
		engine.addSystem(new SistemaMovimento());
		engine.addSystem((EntitySystem) new SistemaDeAnimacao(barramento).assinarEvento(EventoMudancaDeEstado.class));
		engine.addSystem(new SistemaDeOrientacao());
		engine.addSystem(new SistemaDesenhoDebug(camera, font));
		engine.addSystem(new SistemaDesenho(camera, font, recursos));

		return engine;
	}

	public void inicializarEntidades(){
		Entity hero = engine.createEntity();
		hero.add(new ComponenteComando());
		hero.add(new ComponenteEstado(EstadoDoPersonagem.NENHUM));
		hero.add(new ComponenteOrientacao());
		hero.add(new ComponenteTransformacao(new Vector2(50, 50), new Vector2(3, 3)));
		hero.add(new ComponenteCorpoRigido());
		hero.add(new ComponenteSpritesheet(HeroData.id, HeroData.larguraQuadro, HeroData.alturaQuadro, HeroData.numeroLinhas, HeroData.numeroColunas));
		hero.add(new ComponenteAnimacao(HeroData.quadrosPorSegundo));
		hero.add(new ComponenteDebug());
		engine.addEntity(hero);

		Entity arve =  engine.createEntity();
		arve.add(new ComponenteTransformacao(new Vector2(200,50), new Vector2(3, 3)));
		arve.add(new ComponenteSpritesheet("tree",16, 32));
		arve.add(new ComponenteColisorCaixa(16,32));
		arve.add(new ComponenteDebug());
		engine.addEntity(arve);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		engine.update(Gdx.graphics.getDeltaTime());
	}
	
	@Override
	public void dispose () {
		recursos.dispose();
	}
}
