package com.teknis.service;

import com.teknis.service.dto.FactureDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.teknis.domain.Facture}.
 */
public interface FactureService {

    /**
     * Save a facture.
     *
     * @param factureDTO the entity to save.
     * @return the persisted entity.
     */
    FactureDTO save(FactureDTO factureDTO);

    /**
     * Get all the factures.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FactureDTO> findAll(Pageable pageable);


    /**
     * Get the "id" facture.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FactureDTO> findOne(Long id);

    /**
     * Delete the "id" facture.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
