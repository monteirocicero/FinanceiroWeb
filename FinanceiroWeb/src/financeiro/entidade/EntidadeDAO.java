package financeiro.entidade;

import java.util.List;

public interface EntidadeDAO {
	void salvar(Entidade entidade);
	Entidade carregar(Integer id);
	List<Entidade> buscaPorNome(String nome);
	Entidade findOneByName(String nome);
}
