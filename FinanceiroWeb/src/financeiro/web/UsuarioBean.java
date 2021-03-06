package financeiro.web;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.sun.xml.internal.ws.util.UtilException;

import financeiro.conta.Conta;
import financeiro.conta.ContaRN;
import financeiro.usuario.Usuario;
import financeiro.usuario.UsuarioRN;

@ManagedBean(name="usuarioBean")
@RequestScoped
public class UsuarioBean {
	
	private Usuario usuario = new Usuario();
	private String confirmarSenha;
	private List<Usuario> lista;
	private String destinoSalvar;
	private Conta conta = new Conta();
	private String senhaCriptografada;
			
	public String novo() {
		destinoSalvar = "usuarioSucesso";
		usuario.setAtivo(true);
		return "usuario";
	}
	
	public String editar() {
		confirmarSenha = usuario.getSenha();
		senhaCriptografada = usuario.getSenha();
		return "/publico/usuario";
	}
	
	public String salvar() {
		FacesContext context = FacesContext.getCurrentInstance();
		String senha = usuario.getSenha();
		
		if (senha != null && senha.trim().length() > 0 && !senha.equals(confirmarSenha)) {
			FacesMessage facesMessage = new FacesMessage("A senha não foi confirmada corretamente");
			context.addMessage(null, facesMessage);
			return null;
		}
		
		if (senha != null && senha.trim().length() == 0) {
			usuario.setSenha(senhaCriptografada);
		} else {
			try {
				MessageDigest md = MessageDigest.getInstance("SHA-256");
				md.update(senha.getBytes());
				String senhaCripto = new sun.misc.BASE64Encoder().encode(md.digest());
				usuario.setSenha(senhaCripto);
			} catch (NoSuchAlgorithmException e) {
				throw new UtilException("Erro ao criptografar a senha do usuário.", e);
			}
		}
		
		UsuarioRN usuarioRN = new UsuarioRN();
		usuarioRN.salvar(usuario);
		
		if (conta.getDescricao() != null) {
			conta.setUsuario(usuario);
			conta.setFavorita(true);
			ContaRN contaRN = new ContaRN();
			contaRN.salvar(conta);
		}
		
		return destinoSalvar;
	}
	
	public String excluir() {
		UsuarioRN usuarioRN = new UsuarioRN();
		usuarioRN.excluir(usuario);
		lista = null;
		return null;
	}
	
	public String ativar() {
		if (usuario.isAtivo())
			usuario.setAtivo(false);
		else
			usuario.setAtivo(true);
		
		UsuarioRN usuarioRN = new UsuarioRN();
		usuarioRN.salvar(usuario);
		return null;
	}
	
	public String atribuiPermissao(Usuario usuario, String permissao) {
		this.usuario = usuario;
		Set<String> permissoes = this.usuario.getPermissao();
		if (permissoes.contains(permissao)) {
			permissoes.remove(permissao);
		} else {
			permissoes.add(permissao);
		}
		return null;
	}
	
	public List<Usuario> getLista() {
		if (lista == null) {
			UsuarioRN usuarioRN = new UsuarioRN();
			lista = usuarioRN.listar();
		}
		return lista;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public String getConfirmarSenha() {
		return confirmarSenha;
	}
	
	public void setConfirmarSenha(String confirmarSenha) {
		this.confirmarSenha = confirmarSenha;
	}

	public String getDestinoSalvar() {
		return destinoSalvar;
	}

	public void setDestinoSalvar(String destinoSalvar) {
		this.destinoSalvar = destinoSalvar;
	}
	
	public void setConta(Conta conta) {
		this.conta = conta;
	}
	
	public Conta getConta() {
		return conta;
	}
	
	public void setSenhaCriptografada(String senhaCriptografada) {
		this.senhaCriptografada = senhaCriptografada;
	}
	
	public String getSenhaCriptografada() {
		return senhaCriptografada;
	}
			
}
