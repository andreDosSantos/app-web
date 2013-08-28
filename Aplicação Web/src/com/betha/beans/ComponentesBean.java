package com.betha.beans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

import com.betha.business.Pessoa;
import com.betha.business.PessoaComparator;
import com.betha.daos.PessoaDAO;
import com.betha.util.FabricaSessao;
import com.betha.util.FacesUtil;

@ManagedBean
@ViewScoped
public class ComponentesBean {
	private List<Pessoa> lista;
	private List<Pessoa> listaBanco;
	private List<Pessoa> filtrados;
	private List<Pessoa> filtradosBanco;
	private List<Pessoa> selecionados;
	private List<Pessoa> selecionadosBanco;
	private Pessoa pessoa;
	private boolean checkboxSelecionado;
	private boolean sorted;
	private boolean asc;
	private String filtro;
	
	private Pessoa pessoaSelecionada;

	public ComponentesBean() {
		PessoaDAO pessoaDao = new PessoaDAO();
		this.lista = pessoaDao.listarTodas();
		this.selecionados = new ArrayList<Pessoa>();
		this.filtrados = new ArrayList<Pessoa>();
		this.listaBanco = pessoaDao.listarTodasBanco();
		this.selecionadosBanco = new ArrayList<Pessoa>();
		this.filtradosBanco = new ArrayList<Pessoa>();
	}

	public List<Pessoa> getLista() {
		return lista;
	}

	public void setLista(List<Pessoa> lista) {
		this.lista = lista;
	}
	
	

	public List<Pessoa> getListaBanco() {
		return listaBanco;
	}

	public void setListaBanco(List<Pessoa> listaBanco) {
		this.listaBanco = listaBanco;
	}

	public List<Pessoa> getSelecionados() {
		return selecionados;
	}

	public void setSelecionados(List<Pessoa> selecionados) {
		this.selecionados = selecionados;
	}
	

	public List<Pessoa> getSelecionadosBanco() {
		return selecionadosBanco;
	}

	public void setSelecionadosBanco(List<Pessoa> selecionadosBanco) {
		this.selecionadosBanco = selecionadosBanco;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public boolean isCheckboxSelecionado() {
		return checkboxSelecionado;
	}

	public void setCheckboxSelecionado(boolean checkboxSelecionado) {
		this.checkboxSelecionado = checkboxSelecionado;
	}

	public boolean isAsc() {
		return asc;
	}

	public void setAsc(boolean asc) {
		this.asc = asc;
	}

	public boolean isSorted() {
		return sorted;
	}

	public void setSorted(boolean sorted) {
		this.sorted = sorted;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public List<Pessoa> getFiltrados() {
		return filtrados;
	}

	public void setFiltrados(List<Pessoa> filtrados) {
		this.filtrados = filtrados;
	}
	
	

	public List<Pessoa> getFiltradosBanco() {
		return filtradosBanco;
	}

	public void setFiltradosBanco(List<Pessoa> filtradosBanco) {
		this.filtradosBanco = filtradosBanco;
	}

	public void selecionar() {
		for (int i = 0; i < selecionados.size(); i++) {
			System.out.println(selecionados.get(i).getNome());
		}
	}

	public void marcarTodos() {
		for (int i = 0; i < this.lista.size(); i++) {
			this.lista.get(i).setSelecionado(this.checkboxSelecionado);
		}
	}
	
	public void marcarTodosBanco() {
		for (int i = 0; i < this.listaBanco.size(); i++) {
			this.listaBanco.get(i).setSelecionado(this.checkboxSelecionado);
		}
	}

	public void sort() {
		this.setSorted(true);
		this.setAsc(!this.asc);
		if (this.filtro != null && this.filtro.length() > 0) {
			Collections.sort(this.filtrados, new PessoaComparator(this.asc));
		} else {
			Collections.sort(this.lista, new PessoaComparator(this.asc));
		}
	}
	
	public void sortBanco() {
		this.setSorted(true);
		this.setAsc(!this.asc);
		if (this.filtro != null && this.filtro.length() > 0) {
			Collections.sort(this.filtradosBanco, new PessoaComparator(this.asc));
		} else {
			Collections.sort(this.listaBanco, new PessoaComparator(this.asc));
		}
	}

	public void buscar() {
		this.filtrados = new ArrayList<Pessoa>();
		for (int i = 0; i < this.lista.size(); i++) {
			if (this.lista.get(i).getNome().toLowerCase()
					.contains(this.filtro.toLowerCase())) {
				this.filtrados.add(this.lista.get(i));
			}
		}
	}
	
	public void buscarBanco() {
		Session session = FabricaSessao.abrirSessao();
		
		Transaction t = session.beginTransaction();
		
		List<Pessoa> pessoas = session.createCriteria(Pessoa.class).addOrder(Order.asc("nome")).list();
		
		
		
		session.close();//fechando a sessao;
		
	}
	
	public void editar(Pessoa pessoa)
	{
		if(this.pessoaSelecionada == null || this.pessoaSelecionada != pessoa){
			this.pessoaSelecionada = pessoa;
			Session session = FabricaSessao.abrirSessao();//abrindo a sessao
			Transaction t = session.beginTransaction();
			
		}else{
			this.pessoaSelecionada = null;
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Cadastro salvo com sucesso!", ""));
		}
	}
	
	public void editarBanco(Pessoa pessoa)
	{
		if(this.pessoaSelecionada == null || this.pessoaSelecionada != pessoa){
			this.pessoaSelecionada = pessoa;
		}else{
			this.pessoaSelecionada = null;
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Cadastro salvo com sucesso!", ""));
		}
	}
	
	public void excluir(Pessoa pessoa){
		this.lista.remove(pessoa);
		
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Pessoa excluída com sucesso!", ""));
	}
	public Pessoa getPessoaSelecionada() {
		return pessoaSelecionada;
	}

	public void setPessoaSelecionada(Pessoa pessoaSelecionada) {
		this.pessoaSelecionada = pessoaSelecionada;
	}
}
