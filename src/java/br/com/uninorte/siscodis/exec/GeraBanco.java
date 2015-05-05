package br.com.uninorte.siscodis.exec;

import br.com.uninorte.siscodis.dao.UsersDAO;
import br.com.uninorte.siscodis.entidades.Usuarios;
import br.com.uninorte.siscodis.util.GeraSenha;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class GeraBanco {

    private static UsersDAO ud = new UsersDAO();
    private static Usuarios u = new Usuarios();

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        AnnotationConfiguration configuration = new AnnotationConfiguration();
        configuration.configure();
        SchemaExport se = new SchemaExport(configuration);
        se.create(true, true);
        u.setAutorizacao("ROLE_GER");
        u.setLogin("admsiscodis");
        u.setSenha(new GeraSenha().ecripta("admin"));
        try {
            ud.salvar(u);
        } catch (Exception ex) {
            System.out.println("O administrador nao foi criado com sucesso!!!");
        }
    }
}
