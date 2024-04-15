package com.stephenfg.sre.estados;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.stephenfg.sre.componentes.ComponenteCorpoRigido;
import com.stephenfg.sre.componentes.ComponenteEstado;
import com.stephenfg.sre.componentes.ComponenteOrientacao;

public class ArgumentosDeTratativa {
    public Entity entidade;
    public ComponenteEstado estado;
    public ComponentMapper<ComponenteEstado> mapeadorEstado;
    public ComponentMapper<ComponenteCorpoRigido> mapeadorCorpoRigido;
    public ComponentMapper<ComponenteOrientacao> mapeadorOrientacao;
}
