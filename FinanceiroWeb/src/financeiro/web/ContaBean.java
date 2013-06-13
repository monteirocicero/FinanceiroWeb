package financeiro.web;

import java.util.List;

import javax.faces.bean.ManagedBean;

import financeiro.conta.Conta;
import financeiro.conta.ContaRN;
import financeiro.web.util.ContextoUtil;

@ManagedBean
public class ContaBean {
	
	private Conta selecionada = new Conta();
	private List<Conta> lista = null;
	
	public void salvar() {
		ContextoBean contextoBean = ContextoUtil.getContextoBean();
		selecionada.setUsuario(contextoBean.getUsuarioLogado());
		ContaRN contaRN = new ContaRN();
		contaRN.salvar(selecionada);
		selecionada = new Conta();
		lista = null;
	}
	
	public void editar() {
		//TODO
	}
	
	public void excluir() {
		ContaRN contaRN = new ContaRN();
		contaRN.excluir(selecionada);
		selecionada = new Conta();
		lista = null;
	}
	
	public List<Conta> getLista() {
		if (lista == null) {
			ContextoBean contextoBean = ContextoUtil.getContextoBean();
			ContaRN contaRN = new ContaRN();
			lista = contaRN.listar(contextoBean.getUsuarioLogado());
		}
		return lista;
	}
	
	public void tornarFavorita() {
		ContaRN contaRN = new ContaRN();
		contaRN.tornarFavorita(selecionada);
		selecionada = new Conta();
	}
	
	public Conta getSelecionada() {
		return selecionada;
	}
	
	public void setSelecionada(Conta selecionada) {
		this.selecionada = selecionada;
	}
	
	
	public void setLista(List<Conta> lista) {
		this.lista = lista;
	}
	
}
