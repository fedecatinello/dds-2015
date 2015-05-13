package dds.javatar.app.dto.sistema;

import java.util.ArrayList;
import java.util.List;

import dds.javatar.app.dto.grupodeusuarios.GrupoDeUsuarios;
import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.receta.busqueda.Busqueda;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.util.BusinessException;
import dds.javatar.app.util.exception.FilterException;

public class Sistema implements RepositorioRecetas {


    private List<Receta> recetaConocidas;

    protected Sistema() {
        this.recetaConocidas = new ArrayList<Receta>();
    }

    private static Sistema instance;
    public static Sistema getInstance() {
        if (instance == null) {
            instance = new Sistema();
        }
        return instance;
    }

    @Override
    public void agregar(Receta receta) {

        this.recetaConocidas.add(receta);
        this.purificarLista();
    }

    @Override
    public void quitar(Receta receta) throws BusinessException {
        if (this.encontre(receta)) {
            this.recetaConocidas.remove(receta);
        }
    }

    private boolean encontre(Receta receta) {
        this.purificarLista();
        for (int i = 0; i < this.recetaConocidas.size(); i++) {
            if ((this.recetaConocidas.get(i).getNombre().equals(receta.getNombre()))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Receta> listarTodas() {
        this.purificarLista();
        return this.recetaConocidas;
    }

    public void sugerir(Receta receta, Usuario usuario) throws BusinessException {
        this.purificarLista();
        for (String ingrediente : receta.getIngredientes().keySet()) {
            if (!usuario.validarSiAceptaReceta(receta) || usuario.tieneAlimentoQueLeDisguste(ingrediente)) {
                throw new BusinessException("la receta: " + receta.getNombre() + " no puede ser sugerida al usuario" + usuario.getNombre());
            }
        }
    }

    public void sugerir(Receta receta, GrupoDeUsuarios grupo) throws BusinessException {
        this.purificarLista();
        for (String preferencia : grupo.getPreferenciasAlimenticias().keySet()) {

            if (!receta.contieneCondimento(preferencia) || !receta.contieneIngrediente(preferencia) || !(receta.getNombre() == preferencia)) {
                throw new BusinessException("La receta:" + receta.getNombre() + " no contiene palabra clave del grupo:" + grupo.getNombre());
            }
            for (Usuario integrante : grupo.getUsuarios()) {
                integrante.validarSiAceptaReceta(receta);
            }
        }
    }

    private void purificarLista() {
        List<Receta> copia = this.recetaConocidas;
        for (int j = 0; j < copia.size(); j++) {
            Boolean flag = false;
            for (int i = 0; i < this.recetaConocidas.size(); i++) {
                if(i!=j){
                if ((this.recetaConocidas.get(i).getNombre().equals(copia.get(j).getNombre()))) {
                   
                    flag = true;
                   
                }
                }
            }
            if (flag == true) {
                this.recetaConocidas.remove(this.recetaConocidas.get(j));
            }
        }

    }

    public List<Receta> recetasQueConoceEl(Usuario usuario) {

        this.purificarLista();
        List<Receta> recetasQueConoce = this.recetaConocidas;

        List<Receta> recetasQueConocePorLosMiembrosDelGrupo = new ArrayList<Receta>();
        if (usuario.getGruposAlQuePertenece().isEmpty() || usuario.getGruposAlQuePertenece() == null) {
            recetasQueConoce.addAll(usuario.getRecetas());
        } else {
            for (GrupoDeUsuarios grupo : usuario.getGruposAlQuePertenece()) {
                for (Usuario miembroDelGrupo : grupo.getUsuarios()) {
                    for (Receta recetasDelMiembro : miembroDelGrupo.getRecetas()) {

                        Boolean flag = false;
                        for (int i = 0; i < recetasQueConoce.size(); i++) {
                            if ((recetasQueConoce.get(i).getNombre().equals(recetasDelMiembro.getNombre()))) {
                                flag = true;
                            }
                        }
                        if (flag == false) {
                            recetasQueConocePorLosMiembrosDelGrupo.add(recetasDelMiembro);
                        }

                    }
                }
            }
        }
        recetasQueConoce.addAll(recetasQueConocePorLosMiembrosDelGrupo);
        this.purificar(recetasQueConoce);
        return recetasQueConoce;
    }

    private void purificar(List<Receta> recetasQueConoce) {
        List<Receta> copia = recetasQueConoce;
        for (int j = 0; j < copia.size(); j++) {
            Boolean flag = false;
            for (int i = 0; i < recetasQueConoce.size(); i++) {
                if(i!=j){
                if ((recetasQueConoce.get(i).getNombre().equals(copia.get(j).getNombre()))) {
                    flag = true;
                    }
                }
            }
            if (flag == true) {
                recetasQueConoce.remove(recetasQueConoce.get(j));
            }
        }
       
    }

    public List<Receta> realizarBusquedaPara(Busqueda busqueda, Usuario usuario) throws FilterException{
       
        List<Receta> recetasXusuario = recetasQueConoceEl(usuario);
        busqueda.filtrar(usuario,recetasXusuario);
        busqueda.postProcesar(recetasXusuario);
        return recetasXusuario;
       
    }
   
   
}