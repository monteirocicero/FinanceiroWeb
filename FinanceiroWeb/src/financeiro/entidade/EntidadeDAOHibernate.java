package financeiro.entidade;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

public class EntidadeDAOHibernate implements EntidadeDAO {
	
	private Session session;
	
	public void setSession(Session session) {
		this.session = session;
	}

	@Override
	public void salvar(Entidade entidade) {
		session.save(entidade);
	}

	@Override
	public List<Entidade> buscaPorNome(String nome) {
		String hql = "select e from Entidade e where lower(e.nome) like :nome order by p.nome";
		Query query =  session.createQuery(hql);
		query.setString("nome", nome + "%");
		return query.list();
	}

	@Override
	public Entidade carregar(Integer id) {
		return (Entidade) session.load(Entidade.class, id);
	}

	@Override
	public Entidade findOneByName(String nome) {
		String hql = "select 1 from Entidade e where lower(e.nome) :nome";
		Query query = session.createQuery(hql);
		query.setSerializable("nome", nome);
		return (Entidade) query.uniqueResult();
	}

}
