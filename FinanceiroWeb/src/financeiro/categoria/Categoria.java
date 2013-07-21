package financeiro.categoria;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.OrderBy;

import financeiro.usuario.Usuario;

@Entity
@Table(name = "categoria")
public class Categoria implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7145588000578855198L;
	
	@Id
	@GeneratedValue
	private Integer codigo;
	
	@ManyToOne
	@JoinColumn(name="categoria_pai", nullable=true)
	@ForeignKey(name="fk_categoria_categoria")
	private Categoria pai;
	
	@ManyToOne
	@JoinColumn(name="usuario")
	@OnDelete(action=OnDeleteAction.CASCADE)
	@ForeignKey(name="fk_categoria_usuario")
	private Usuario usuario;
	private String descricao;
	private int fator;
	
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.REMOVE)
	@JoinColumn(name="categoria_pai", updatable=false)
	@OrderBy(clause="descricao asc")
	private List<Categoria> filhos;
	
	public Categoria(Categoria pai, Usuario usuario, String descricao, int fator) {
		this.pai = pai;
		this.usuario = usuario;
		this.descricao = descricao;
		this.fator = fator;
	}

	public Categoria() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Categoria other = (Categoria) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Categoria getPai() {
		return pai;
	}

	public void setPai(Categoria pai) {
		this.pai = pai;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getFator() {
		return fator;
	}

	public void setFator(int fator) {
		this.fator = fator;
	}

	public List<Categoria> getFilhos() {
		return filhos;
	}

	public void setFilhos(List<Categoria> filhos) {
		this.filhos = filhos;
	}
	
}
