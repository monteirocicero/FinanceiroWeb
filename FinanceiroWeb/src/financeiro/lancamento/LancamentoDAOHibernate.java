package financeiro.lancamento;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import financeiro.conta.Conta;

public class LancamentoDAOHibernate implements LancamentoDAO {
	
	private Session session;
	
	public void setSession(Session session) {
		this.session = session;
	}

	@Override
	public void salvar(Lancamento lancamento) {
		session.saveOrUpdate(lancamento);
	}

	@Override
	public void excluir(Lancamento lancamento) {
		session.delete(lancamento);
	}

	@Override
	public Lancamento carregar(Integer lancamento) {
		return (Lancamento) session.get(Lancamento.class, lancamento);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Lancamento> listar(Conta conta, Date dataInicio, Date dataFim) {
		Criteria criteria = session.createCriteria(Lancamento.class);
		
		if (dataInicio != null && dataFim != null) {
			criteria.add(Restrictions.between("data", dataInicio, dataFim));
		} else if (dataInicio != null) {
			criteria.add(Restrictions.ge("data", dataInicio));
		} else if (dataFim != null) {
			criteria.add(Restrictions.le("data", dataFim));
		}
		criteria.add(Restrictions.eq("conta", conta));
		criteria.addOrder(Order.asc("data"));
		return criteria.list();
	}

	@Override
	public float saldo(Conta conta, Date data) {
		StringBuffer sql = new StringBuffer();
		sql.append("select sum(l.valor * c.fator)");
		sql.append(" from lancamento l,");
		sql.append("   categoria c");
		sql.append(" where l.categoria = c.codigo");
		sql.append("  and l.conta = :conta");
		sql.append("  and l.data <= :data");
		SQLQuery query = session.createSQLQuery(sql.toString());
		query.setParameter("conta", conta.getConta());
		query.setParameter("data", data);
		BigDecimal saldo = (BigDecimal) query.uniqueResult();
		if (saldo != null) {
			return saldo.floatValue();
		}
		return 0f;
	}

}
