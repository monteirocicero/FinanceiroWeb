package financeiro.testes;

import org.hibernate.Session;

import financeiro.util.HibernateUtil;

public class AtualizaBancoDeDados {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		sessao.beginTransaction();

	}

}
