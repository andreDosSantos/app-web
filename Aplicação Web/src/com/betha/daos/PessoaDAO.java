package com.betha.daos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.betha.business.Pessoa;
import com.betha.util.FabricaSessao;

public class PessoaDAO {
	private List<Pessoa> lista;

	public PessoaDAO() {
		this.lista = new ArrayList<Pessoa>();
		this.lista.add(new Pessoa(1, "AntÃ´nio JoÃ£o Shultz",
				"Rua Ã�lvaro de Oliveira", "4141-4545"));
		this.lista.add(new Pessoa(2, "Maria JosÃ© da Silva",
				"Rua Geral, sem nÃºmero", "3432-0000"));
		this.lista.add(new Pessoa(3, "JoÃ£o Pereira",
				"Avenida CentenÃ¡rio, 1542", "3437-7445"));
		this.lista.add(new Pessoa(4, "Eduardo GonÃ§alves",
				"Rua Machado de Assis, 345", "8841-6952"));
		this.lista.add(new Pessoa(5, "Joana GusmÃ£o",
				"Travessa Elias Benedetti", "2106-4545"));
		this.lista.add(new Pessoa(6, "FÃ¡bio de Souza Machado",
				"Rua Carlos Henrique Medeiros, 234", "4545-2421"));
		this.lista.add(new Pessoa(7, "JoÃ£o Pereira",
				"Avenida CentenÃ¡rio, 1542", "3437-7445"));
	}

	public Pessoa buscarPorCodigo(Integer codigo) {

		for (int i = 0; i < this.lista.size(); i++) {
			if(this.lista.get(i).getCodigo() == codigo)
				return this.lista.get(i);
		}
		return null;
	}
	public ArrayList<Pessoa> listarTodas(){
		return (ArrayList<Pessoa>) this.lista;
	}
	
	public ArrayList<Pessoa> listarTodasBanco(){
		Session session = FabricaSessao.abrirSessao();//abrindo a sessao
		
		Transaction t = session.beginTransaction();
		
		List<Pessoa> pessoas = session.createCriteria(Pessoa.class).list();
		
		return (ArrayList<Pessoa>) pessoas;
	}
	
}
