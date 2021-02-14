package com.teknis.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Facture.
 */
@Entity
@Table(name = "facture")
public class Facture implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "numero", nullable = false, unique = true)
    private Long numero;

    @NotNull
    @Column(name = "date_piece", nullable = false)
    private LocalDate datePiece;

    @NotNull
    @Column(name = "client", nullable = false)
    private String client;

    @Column(name = "bl_visa")
    private Boolean blVisa;

    @Size(max = 2000)
    @Column(name = "commentaire", length = 2000)
    private String commentaire;

    @Column(name = "facture_recu_mail")
    private LocalDate factureRecuMail;

    @Column(name = "facture_recu_physique")
    private LocalDate factureRecuPhysique;

    @Column(name = "bl_recu_mail")
    private LocalDate blRecuMail;

    @Column(name = "bl_recuphysique")
    private LocalDate blRecuphysique;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumero() {
        return numero;
    }

    public Facture numero(Long numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public LocalDate getDatePiece() {
        return datePiece;
    }

    public Facture datePiece(LocalDate datePiece) {
        this.datePiece = datePiece;
        return this;
    }

    public void setDatePiece(LocalDate datePiece) {
        this.datePiece = datePiece;
    }

    public String getClient() {
        return client;
    }

    public Facture client(String client) {
        this.client = client;
        return this;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Boolean isBlVisa() {
        return blVisa;
    }

    public Facture blVisa(Boolean blVisa) {
        this.blVisa = blVisa;
        return this;
    }

    public void setBlVisa(Boolean blVisa) {
        this.blVisa = blVisa;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public Facture commentaire(String commentaire) {
        this.commentaire = commentaire;
        return this;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public LocalDate getFactureRecuMail() {
        return factureRecuMail;
    }

    public Facture factureRecuMail(LocalDate factureRecuMail) {
        this.factureRecuMail = factureRecuMail;
        return this;
    }

    public void setFactureRecuMail(LocalDate factureRecuMail) {
        this.factureRecuMail = factureRecuMail;
    }

    public LocalDate getFactureRecuPhysique() {
        return factureRecuPhysique;
    }

    public Facture factureRecuPhysique(LocalDate factureRecuPhysique) {
        this.factureRecuPhysique = factureRecuPhysique;
        return this;
    }

    public void setFactureRecuPhysique(LocalDate factureRecuPhysique) {
        this.factureRecuPhysique = factureRecuPhysique;
    }

    public LocalDate getBlRecuMail() {
        return blRecuMail;
    }

    public Facture blRecuMail(LocalDate blRecuMail) {
        this.blRecuMail = blRecuMail;
        return this;
    }

    public void setBlRecuMail(LocalDate blRecuMail) {
        this.blRecuMail = blRecuMail;
    }

    public LocalDate getBlRecuphysique() {
        return blRecuphysique;
    }

    public Facture blRecuphysique(LocalDate blRecuphysique) {
        this.blRecuphysique = blRecuphysique;
        return this;
    }

    public void setBlRecuphysique(LocalDate blRecuphysique) {
        this.blRecuphysique = blRecuphysique;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Facture)) {
            return false;
        }
        return id != null && id.equals(((Facture) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Facture{" +
            "id=" + getId() +
            ", numero=" + getNumero() +
            ", datePiece='" + getDatePiece() + "'" +
            ", client='" + getClient() + "'" +
            ", blVisa='" + isBlVisa() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
            ", factureRecuMail='" + getFactureRecuMail() + "'" +
            ", factureRecuPhysique='" + getFactureRecuPhysique() + "'" +
            ", blRecuMail='" + getBlRecuMail() + "'" +
            ", blRecuphysique='" + getBlRecuphysique() + "'" +
            "}";
    }
}
