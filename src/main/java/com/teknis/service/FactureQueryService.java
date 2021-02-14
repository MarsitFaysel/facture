package com.teknis.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.teknis.domain.Facture;
import com.teknis.domain.*; // for static metamodels
import com.teknis.repository.FactureRepository;
import com.teknis.service.dto.FactureCriteria;
import com.teknis.service.dto.FactureDTO;
import com.teknis.service.mapper.FactureMapper;

/**
 * Service for executing complex queries for {@link Facture} entities in the database.
 * The main input is a {@link FactureCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FactureDTO} or a {@link Page} of {@link FactureDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FactureQueryService extends QueryService<Facture> {

    private final Logger log = LoggerFactory.getLogger(FactureQueryService.class);

    private final FactureRepository factureRepository;

    private final FactureMapper factureMapper;

    public FactureQueryService(FactureRepository factureRepository, FactureMapper factureMapper) {
        this.factureRepository = factureRepository;
        this.factureMapper = factureMapper;
    }

    /**
     * Return a {@link List} of {@link FactureDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FactureDTO> findByCriteria(FactureCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Facture> specification = createSpecification(criteria);
        return factureMapper.toDto(factureRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FactureDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FactureDTO> findByCriteria(FactureCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Facture> specification = createSpecification(criteria);
        return factureRepository.findAll(specification, page)
            .map(factureMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FactureCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Facture> specification = createSpecification(criteria);
        return factureRepository.count(specification);
    }

    /**
     * Function to convert {@link FactureCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Facture> createSpecification(FactureCriteria criteria) {
        Specification<Facture> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Facture_.id));
            }
            if (criteria.getNumero() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumero(), Facture_.numero));
            }
            if (criteria.getDatePiece() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDatePiece(), Facture_.datePiece));
            }
            if (criteria.getClient() != null) {
                specification = specification.and(buildStringSpecification(criteria.getClient(), Facture_.client));
            }
            if (criteria.getBlVisa() != null) {
                specification = specification.and(buildSpecification(criteria.getBlVisa(), Facture_.blVisa));
            }
            if (criteria.getCommentaire() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCommentaire(), Facture_.commentaire));
            }
            if (criteria.getFactureRecuMail() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFactureRecuMail(), Facture_.factureRecuMail));
            }
            if (criteria.getFactureRecuPhysique() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFactureRecuPhysique(), Facture_.factureRecuPhysique));
            }
            if (criteria.getBlRecuMail() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBlRecuMail(), Facture_.blRecuMail));
            }
            if (criteria.getBlRecuphysique() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBlRecuphysique(), Facture_.blRecuphysique));
            }
        }
        return specification;
    }
}
