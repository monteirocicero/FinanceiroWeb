package financeiro.web;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import financeiro.conta.Conta;
import financeiro.conta.ContaRN;
import financeiro.usuario.Usuario;
import financeiro.usuario.UsuarioRN;

@ManagedBean
public class ContextoBean {
	
	private Usuario usuarioLogado = null;
	private Conta contaAtiva = null;
	
	public Usuario getUsuarioLogado() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();
		String login = external.getRemoteUser();
		if (usuarioLogado == null || !login.equals(usuarioLogado.getLogin())) {
			if (login != null) {
				UsuarioRN usuarioRN = new UsuarioRN();
				usuarioLogado = usuarioRN.buscarPorLogin(login);
				contaAtiva = null;
			}
		}
		return usuarioLogado;
	}
	
	public Conta getContaAtiva() {
		if (contaAtiva == null) {
			Usuario usuario = getUsuarioLogado();
			ContaRN contaRN = new ContaRN();
			contaAtiva = contaRN.buscarFavorita(usuario);
			if (contaAtiva == null) {
				List<Conta> contas = contaRN.listar(usuario);
				if (contas != null) {
					for (Conta conta : contas) {
						contaAtiva = conta;
						break;
					}
				}
			}
		}
		return contaAtiva;
	}
	
	public void setContaAtiva(ValueChangeEvent event) {
		Integer conta = (Integer) event.getNewValue();
		ContaRN contaRN = new ContaRN();
		contaAtiva = contaRN.carregar(conta);
	}
	
	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

}
