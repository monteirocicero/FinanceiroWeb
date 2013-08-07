package financeiro.lancamento;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import financeiro.categoria.Categoria;
import financeiro.conta.Conta;
import financeiro.entidade.Entidade;
import financeiro.entidade.EntidadeRN;
import financeiro.util.RNException;
import financeiro.web.ContextoBean;
import financeiro.web.util.ContextoUtil;

@ManagedBean
@ViewScoped
public class LancamentoBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6756014425147843275L;
	
	private List<Lancamento> lista;
	private List<Double> saldos = new ArrayList<Double>();
	private float saldoGeral;
	private Lancamento editado = new Lancamento();
	private List<Lancamento> listaAteHoje;
	private List<Lancamento> listaFuturos;
	private List<Entidade> entidades;
				
	public LancamentoBean() {
		novo();
	}

	public void novo() {
		editado = new Lancamento();
		editado.setData(new Date(System.currentTimeMillis()));
	}
	
	public void editar() {
		
	}
	
	public void salvar() {
		ContextoBean contextoBean = ContextoUtil.getContextoBean();
		editado.setUsuario(contextoBean.getUsuarioLogado());
		editado.setConta(contextoBean.getContaAtiva());
		
//		EntidadeRN entidadeRN = new EntidadeRN();
//		Entidade tmpEntidade = entidadeRN.buscaPorNome(entidade.getNome());
//		
//		if (tmpEntidade == null) {
//			entidadeRN.salvar(entidade);
//		}
				
		LancamentoRN lancamentoRN = new LancamentoRN();
		//editado.setEntidade(entidade);
		lancamentoRN.salvar(editado);
		
		novo();
		lista = null;
	}
	
	public void excluir() {
		LancamentoRN lancamentoRN = new LancamentoRN();
		editado = lancamentoRN.carregar(editado.getLancamento());
		lancamentoRN.excluir(editado);
		lista = null;
	}
	
	public List<Lancamento> getLista() {
		if (lista == null) {
			ContextoBean contextoBean = ContextoUtil.getContextoBean();
			Conta conta = contextoBean.getContaAtiva();
			
			Calendar dataSaldo = new GregorianCalendar();
			dataSaldo.add(Calendar.MONTH, -1);
			dataSaldo.add(Calendar.DAY_OF_MONTH, -1);
			
			Calendar inicio = new GregorianCalendar();
			inicio.add(Calendar.MONTH, -1);
			
			LancamentoRN lancamentoRN = new LancamentoRN();
			try {
				saldoGeral = lancamentoRN.saldo(conta, dataSaldo.getTime());
				lista = lancamentoRN.listar(conta, inicio.getTime(), null);
				
				Categoria categoria = null;
				double saldo = saldoGeral;
				for (Lancamento lancamento : lista) {
					categoria = lancamento.getCategoria();
					saldo += lancamento.getValor().floatValue() * categoria.getFator();
					saldos.add(saldo);
				}
			} catch (RNException e) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(e.getMessage()));
				return null;
			}
			
		}
		return lista;
	}
	
	public List<Lancamento> getListaAteHoje() {
		if (listaAteHoje == null) {
			ContextoBean contextoBean = ContextoUtil.getContextoBean();
			Conta conta = contextoBean.getContaAtiva();
			
			Calendar hoje = new GregorianCalendar();
			
			LancamentoRN lancamentoRN = new LancamentoRN();
			listaAteHoje = lancamentoRN.listar(conta, null, hoje.getTime());
		}
		return listaAteHoje;
	}
	
	public List<Lancamento> getListaFuturos() {
		if (listaFuturos == null) {
			ContextoBean contextoBean = ContextoUtil.getContextoBean();
			Conta conta = contextoBean.getContaAtiva();
			
			Calendar amanha = new GregorianCalendar();
			amanha.add(Calendar.DAY_OF_MONTH, 1);
			
			LancamentoRN lancamentoRN = new LancamentoRN();
			listaFuturos = lancamentoRN.listar(conta, amanha.getTime(), null);
		}
		return null;
	}
	
	/*
	public List<Entidade> buscaEntidades(String nome) {
		List<String> paisesSugeridos = new ArrayList<>();

		for (String pais : this.paises) {
			if (pais.toLowerCase().startsWith(consulta.toLowerCase())) {
				paisesSugeridos.add(pais);
			}
		}

		return paisesSugeridos;
	}
	*/

	public List<Double> getSaldos() {
		return saldos;
	}

	public void setSaldos(List<Double> saldos) {
		this.saldos = saldos;
	}

	public float getSaldoGeral() {
		return saldoGeral;
	}

	public void setSaldoGeral(float saldoGeral) {
		this.saldoGeral = saldoGeral;
	}

	public Lancamento getEditado() {
		return editado;
	}

	public void setEditado(Lancamento editado) {
		this.editado = editado;
	}

	public void setLista(List<Lancamento> lista) {
		this.lista = lista;
	}

	public List<Entidade> getEntidades() {
		return entidades;
	}

	public void setEntidades(List<Entidade> entidades) {
		this.entidades = entidades;
	}
		
}
