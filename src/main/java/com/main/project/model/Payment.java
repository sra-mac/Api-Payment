package com.main.project.model;


import org.hibernate.validator.constraints.br.CPF;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	@NotNull
	Integer cod_debito;
	@NotBlank
	@CPF
	String tp_pessoa;
	@NotNull
	String metodoPagamento;
	Integer numCartao;
	@NotNull
	@Min(value = 0)
	Float valor;
	@NotEmpty
	String status;

	public static Payment converter (Payment p) {
		var pay = new Payment();
		pay.setId(p.getId());
		pay.setCod_debito(p.getCod_debito());
		pay.setMetodoPagamento(p.getMetodoPagamento());
		pay.setNumCartao(p.getNumCartao());
		pay.setStatus(p.getStatus());
		pay.setTp_pessoa(p.getTp_pessoa());
		pay.setValor(p.getValor());
		return pay;
	}

	public Integer getCod_debito() {
		return cod_debito;
	}
	public void setCod_debito(Integer cod_debito) {
		this.cod_debito = cod_debito;
	}
	public String getTp_pessoa() {
		return tp_pessoa;
	}
	public void setTp_pessoa(String tp_pessoa) {
		this.tp_pessoa = tp_pessoa;
	}
	public String getMetodoPagamento() {
		return metodoPagamento;
	}
	public void setMetodoPagamento(String metodoPagamento) {
		this.metodoPagamento = metodoPagamento;
	}
	public Integer getNumCartao() {
		return numCartao;
	}
	public void setNumCartao(Integer numCartao) {
		this.numCartao = numCartao;
	}
	public Float getValor() {
		return valor;
	}
	public void setValor(Float valor) {
		this.valor = valor;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getId() {
		return id;
	}

}
