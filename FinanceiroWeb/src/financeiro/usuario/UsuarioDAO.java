package financeiro.usuario;

import java.util.List;

public interface UsuarioDAO {
	void salvar(Usuario usuario);
	void atualizar(Usuario usuario);
	void excluir(Usuario usuario);
	Usuario carregar(Integer codigo);
	Usuario buscarPorLogin(String login);
	List<Usuario> listar();
}
