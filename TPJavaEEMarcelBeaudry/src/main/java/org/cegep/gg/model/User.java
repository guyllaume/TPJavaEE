package org.cegep.gg.model;

import java.time.LocalDate;

public class User {

	private String prenom;
	private String nom;
	private LocalDate date_de_naissance;
	private String telephone;
	private String email;
	private String password;
	
	private String adresse_client;
	private String ville_client;
	private String province_client;
	private String code_postal_client;
	private String pays_client;
	
	private String adresse_livraison;
	private String ville_livraison;
	private String province_livraison;
	private String code_postal_livraison;
	private String pays_livraison;
	
	
	public User() {
	}
	public User(
			String prenom,
			String nom,
			LocalDate date_de_naissance,
			String telephone,
			String email,
			String password,
			String adresse_client,
			String ville_client,
			String province_client,
			String code_postal_client,
			String pays_client,
			String adresse_livraison,
			String ville_livraison,
			String province_livraison,
			String code_postal_livraison,
			String pays_livraison) {
		this.prenom = prenom;
		this.nom = nom;
		this.date_de_naissance = date_de_naissance;
		this.telephone = telephone;
		this.email = email;
		this.password = password;
		this.adresse_client = adresse_client;
		this.ville_client = ville_client;
		this.province_client = province_client;
		this.code_postal_client = code_postal_client;
		this.pays_client = pays_client;
		this.adresse_livraison = adresse_livraison;
		this.ville_livraison = ville_livraison;
		this.province_livraison = province_livraison;
		this.code_postal_livraison = code_postal_livraison;
		this.pays_livraison = pays_livraison;
	}
	

	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public LocalDate getDate_de_naissance() {
		return date_de_naissance;
	}
	public void setDate_de_naissance(LocalDate date_de_naissance) {
		this.date_de_naissance = date_de_naissance;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAdresse_client() {
		return adresse_client;
	}
	public void setAdresse_client(String adresse_client) {
		this.adresse_client = adresse_client;
	}
	public String getVille_client() {
		return ville_client;
	}
	public void setVille_client(String ville_client) {
		this.ville_client = ville_client;
	}
	public String getProvince_client() {
		return province_client;
	}
	public void setProvince_client(String province_client) {
		this.province_client = province_client;
	}
	public String getCode_postal_client() {
		return code_postal_client;
	}
	public void setCode_postal_client(String code_postal_client) {
		this.code_postal_client = code_postal_client;
	}
	public String getPays_client() {
		return pays_client;
	}
	public void setPays_client(String pays_client) {
		this.pays_client = pays_client;
	}
	public String getAdresse_livraison() {
		return adresse_livraison;
	}
	public void setAdresse_livraison(String adresse_livraison) {
		this.adresse_livraison = adresse_livraison;
	}
	public String getVille_livraison() {
		return ville_livraison;
	}
	public void setVille_livraison(String ville_livraison) {
		this.ville_livraison = ville_livraison;
	}
	public String getProvince_livraison() {
		return province_livraison;
	}
	public void setProvince_livraison(String province_livraison) {
		this.province_livraison = province_livraison;
	}
	public String getCode_postal_livraison() {
		return code_postal_livraison;
	}
	public void setCode_postal_livraison(String code_postal_livraison) {
		this.code_postal_livraison = code_postal_livraison;
	}
	public String getPays_livraison() {
		return pays_livraison;
	}
	public void setPays_livraison(String pays_livraison) {
		this.pays_livraison = pays_livraison;
	}
}
