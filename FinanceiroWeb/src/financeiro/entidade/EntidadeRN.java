package financeiro.entidade;

import java.util.List;

public class EntidadeRN {
	
	private EntidadeDAO dao;
	
	public void salvar(Entidade entidade) {
		dao.salvar(entidade);
	}
	
	public List<Entidade> listaPorNome(String nome) {
		return dao.buscaPorNome(nome);
	}
	
	public Entidade carregar(Integer id) {
		return dao.carregar(id);
	}
	
	public Entidade buscaPorNome(String nome) {
		return dao.findOneByName(nome);
	}
	
}
