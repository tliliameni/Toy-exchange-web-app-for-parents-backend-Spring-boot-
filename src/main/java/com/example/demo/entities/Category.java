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

@Data // permet de remplacer tous les getters et setters des attributs.
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Category {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	@Column(nullable = false)
	private String photo;
	@Column(nullable = false)
	private String nom;

	public Category(int id, String nom,String photo) {
		super();
		this.id = id;
		this.nom = nom;
		this.photo=photo;
	}

	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
	@JsonProperty(access = Access.WRITE_ONLY) // prend en charge la liste en mode d'ecriture
	List<Article> liste;

	public Category(String nom2,String photo) {
		nom = nom2;
		this.photo=photo;
	}

	public Category() {
		super();
	}

	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo= photo;
	}
	

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
}
