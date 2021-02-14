package com.teknis.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.teknis.domain.Facture} entity. This class is used
 * in {@link com.teknis.web.rest.FactureResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /factures?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FactureCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter numero;

    private LocalDateFilter datePiece;

    private StringFilter client;

    private BooleanFilter blVisa;

    private StringFilter commentaire;

    private LocalDateFilter factureRecuMail;

    private LocalDateFilter factureRecuPhysique;

    private LocalDateFilter blRecuMail;

    private LocalDateFilter blRecuphysique;

    public FactureCriteria() {
    }

    public FactureCriteria(FactureCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.numero = other.numero == null ? null : other.numero.copy();
        this.datePiece = other.datePiece == null ? null : other.datePiece.copy();
        this.client = other.client == null ? null : other.client.copy();
        this.blVisa = other.blVisa == null ? null : other.blVisa.copy();
        this.commentaire = other.commentaire == null ? null : other.commentaire.copy();
        this.factureRecuMail = other.factureRecuMail == null ? null : other.factureRecuMail.copy();
        this.factureRecuPhysique = other.factureRecuPhysique == null ? null : other.factureRecuPhysique.copy();
        this.blRecuMail = other.blRecuMail == null ? null : other.blRecuMail.copy();
        this.blRecuphysique = other.blRecuphysique == null ? null : other.blRecuphysique.copy();
    }

    @Override
    public FactureCriteria copy() {
        return new FactureCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getNumero() {
        return numero;
    }

    public void setNumero(LongFilter numero) {
        this.numero = numero;
    }

    public LocalDateFilter getDatePiece() {
        return datePiece;
    }

    public void setDatePiece(LocalDateFilter datePiece) {
        this.datePiece = datePiece;
    }

    public StringFilter getClient() {
        return client;
    }

    public void setClient(StringFilter client) {
        this.client = client;
    }

    public BooleanFilter getBlVisa() {
        return blVisa;
    }

    public void setBlVisa(BooleanFilter blVisa) {
        this.blVisa = blVisa;
    }

    public StringFilter getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(StringFilter commentaire) {
        this.commentaire = commentaire;
    }

    public LocalDateFilter getFactureRecuMail() {
        return factureRecuMail;
    }

    public void setFactureRecuMail(LocalDateFilter factureRecuMail) {
        this.factureRecuMail = factureRecuMail;
    }

    public LocalDateFilter getFactureRecuPhysique() {
        return factureRecuPhysique;
    }

    public void setFactureRecuPhysique(LocalDateFilter factureRecuPhysique) {
        this.factureRecuPhysique = factureRecuPhysique;
    }

    public LocalDateFilter getBlRecuMail() {
        return blRecuMail;
    }

    public void setBlRecuMail(LocalDateFilter blRecuMail) {
        this.blRecuMail = blRecuMail;
    }

    public LocalDateFilter getBlRecuphysique() {
        return blRecuphysique;
    }

    public void setBlRecuphysique(LocalDateFilter blRecuphysique) {
        this.blRecuphysique = blRecuphysique;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FactureCriteria that = (FactureCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(numero, that.numero) &&
            Objects.equals(datePiece, that.datePiece) &&
            Objects.equals(client, that.client) &&
            Objects.equals(blVisa, that.blVisa) &&
            Objects.equals(commentaire, that.commentaire) &&
            Objects.equals(factureRecuMail, that.factureRecuMail) &&
            Objects.equals(factureRecuPhysique, that.factureRecuPhysique) &&
            Objects.equals(blRecuMail, that.blRecuMail) &&
            Objects.equals(blRecuphysique, that.blRecuphysique);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        numero,
        datePiece,
        client,
        blVisa,
        commentaire,
        factureRecuMail,
        factureRecuPhysique,
        blRecuMail,
        blRecuphysique
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FactureCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (numero != null ? "numero=" + numero + ", " : "") +
                (datePiece != null ? "datePiece=" + datePiece + ", " : "") +
                (client != null ? "client=" + client + ", " : "") +
                (blVisa != null ? "blVisa=" + blVisa + ", " : "") +
                (commentaire != null ? "commentaire=" + commentaire + ", " : "") +
                (factureRecuMail != null ? "factureRecuMail=" + factureRecuMail + ", " : "") +
                (factureRecuPhysique != null ? "factureRecuPhysique=" + factureRecuPhysique + ", " : "") +
                (blRecuMail != null ? "blRecuMail=" + blRecuMail + ", " : "") +
                (blRecuphysique != null ? "blRecuphysique=" + blRecuphysique + ", " : "") +
            "}";
    }

}
