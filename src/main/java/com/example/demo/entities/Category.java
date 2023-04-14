package com.example.demo.entities;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data //permet de remplacer tous les getters et setters des attributs.
@AllArgsConstructor 
@NoArgsConstructor
@Entity @Table
public class Category {
	

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(nullable=false)
	private String nom;
	
	public Category(int id, String nom) {
		super();
		this.id = id;
		this.nom = nom;
	}
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
	@JsonProperty(access=Access.WRITE_ONLY)//prend en charge la liste en mode d'ecriture
	List<Article>liste;
	public Category(String nom2) {
		nom=nom2;
	}
	public Category() {
		super();
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
}
