package com.teknis.service.mapper;


import com.teknis.domain.*;
import com.teknis.service.dto.FactureDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Facture} and its DTO {@link FactureDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FactureMapper extends EntityMapper<FactureDTO, Facture> {



    default Facture fromId(Long id) {
        if (id == null) {
            return null;
        }
        Facture facture = new Facture();
        facture.setId(id);
        return facture;
    }
}
