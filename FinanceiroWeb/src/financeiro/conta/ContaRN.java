package financeiro.conta;

import java.util.Date;
import java.util.List;

import financeiro.usuario.Usuario;
import financeiro.util.DAOFactory;

public class ContaRN {
		
	private ContaDAO contaDAO;
	
	public ContaRN() {
		contaDAO = DAOFactory.criarContaDAO();
	}
	
	public List<Conta> listar(Usuario usuario) {
		return contaDAO.listar(usuario);
	}
	
	public Conta carregar(Integer conta) {
		return contaDAO.carregar(conta);
	}
	
	public void salvar(Conta conta) {
		conta.setDataCadastro(new Date());
		contaDAO.salvar(conta);
	}
	
	public void tornarFavorita(Conta contaFavorita) {
		Conta conta = buscarFavorita(contaFavorita.getUsuario());
		if (conta != null) {
			conta.setFavorita(false);
			contaDAO.salvar(conta);
		}
		contaFavorita.setFavorita(true);
		contaDAO.salvar(contaFavorita);
	}

	public Conta buscarFavorita(Usuario usuario) {
		return contaDAO.buscarFavorita(usuario);
	}

}
