package com.teknis.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.teknis.domain.Facture} entity.
 */
public class FactureDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Long numero;

    @NotNull
    private LocalDate datePiece;

    @NotNull
    private String client;

    private Boolean blVisa;

    @Size(max = 2000)
    private String commentaire;

    private LocalDate factureRecuMail;

    private LocalDate factureRecuPhysique;

    private LocalDate blRecuMail;

    private LocalDate blRecuphysique;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public LocalDate getDatePiece() {
        return datePiece;
    }

    public void setDatePiece(LocalDate datePiece) {
        this.datePiece = datePiece;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Boolean isBlVisa() {
        return blVisa;
    }

    public void setBlVisa(Boolean blVisa) {
        this.blVisa = blVisa;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public LocalDate getFactureRecuMail() {
        return factureRecuMail;
    }

    public void setFactureRecuMail(LocalDate factureRecuMail) {
        this.factureRecuMail = factureRecuMail;
    }

    public LocalDate getFactureRecuPhysique() {
        return factureRecuPhysique;
    }

    public void setFactureRecuPhysique(LocalDate factureRecuPhysique) {
        this.factureRecuPhysique = factureRecuPhysique;
    }

    public LocalDate getBlRecuMail() {
        return blRecuMail;
    }

    public void setBlRecuMail(LocalDate blRecuMail) {
        this.blRecuMail = blRecuMail;
    }

    public LocalDate getBlRecuphysique() {
        return blRecuphysique;
    }

    public void setBlRecuphysique(LocalDate blRecuphysique) {
        this.blRecuphysique = blRecuphysique;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FactureDTO)) {
            return false;
        }

        return id != null && id.equals(((FactureDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FactureDTO{" +
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
