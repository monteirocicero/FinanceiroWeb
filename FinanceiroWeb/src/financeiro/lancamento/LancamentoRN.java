package financeiro.lancamento;

import java.util.Date;
import java.util.List;

import financeiro.conta.Conta;
import financeiro.util.DAOFactory;

public class LancamentoRN {
	
	private LancamentoDAO lancamentoDAO;
	
	public LancamentoRN() {
		lancamentoDAO = DAOFactory.criarLancamentoDAO();
	}
	
	public void salvar(Lancamento lancamento) {
		lancamentoDAO.salvar(lancamento);
	}
	
	public void excluir(Lancamento lancamento) {
		lancamentoDAO.excluir(lancamento);
	}
	
	public Lancamento carregar(Integer lancamento) {
		return lancamentoDAO.carregar(lancamento);
	}
	
	public float saldo(Conta conta, Date data) {
		float saldoInicial = conta.getSaldoInicial();
		float saldoNaData = lancamentoDAO.saldo(conta, data);
		return saldoInicial + saldoNaData;
	}
	
	public List<Lancamento> listar(Conta conta, Date dataInicio, Date dataFim) {
		return lancamentoDAO.listar(conta, dataInicio, dataFim);
	}

}
