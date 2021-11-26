package academia.utilitarios;

import academia.entidades.Usuario;

public class InstanciaSistema {
    private static Usuario usuarioLogado;

    public static Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public static void setUsuarioLogado(Usuario usuarioLogado) {
        InstanciaSistema.usuarioLogado = usuarioLogado;
    }
}
