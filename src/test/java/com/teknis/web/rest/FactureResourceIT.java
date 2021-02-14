package com.teknis.web.rest;

import com.teknis.TeknisTunisieApp;
import com.teknis.domain.Facture;
import com.teknis.repository.FactureRepository;
import com.teknis.service.FactureService;
import com.teknis.service.dto.FactureDTO;
import com.teknis.service.mapper.FactureMapper;
import com.teknis.service.dto.FactureCriteria;
import com.teknis.service.FactureQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link FactureResource} REST controller.
 */
@SpringBootTest(classes = TeknisTunisieApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FactureResourceIT {

    private static final Long DEFAULT_NUMERO = 1L;
    private static final Long UPDATED_NUMERO = 2L;
    private static final Long SMALLER_NUMERO = 1L - 1L;

    private static final LocalDate DEFAULT_DATE_PIECE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_PIECE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_PIECE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_CLIENT = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_BL_VISA = false;
    private static final Boolean UPDATED_BL_VISA = true;

    private static final String DEFAULT_COMMENTAIRE = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FACTURE_RECU_MAIL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FACTURE_RECU_MAIL = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FACTURE_RECU_MAIL = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_FACTURE_RECU_PHYSIQUE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FACTURE_RECU_PHYSIQUE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FACTURE_RECU_PHYSIQUE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_BL_RECU_MAIL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BL_RECU_MAIL = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_BL_RECU_MAIL = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_BL_RECUPHYSIQUE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BL_RECUPHYSIQUE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_BL_RECUPHYSIQUE = LocalDate.ofEpochDay(-1L);

    @Autowired
    private FactureRepository factureRepository;

    @Autowired
    private FactureMapper factureMapper;

    @Autowired
    private FactureService factureService;

    @Autowired
    private FactureQueryService factureQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFactureMockMvc;

    private Facture facture;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Facture createEntity(EntityManager em) {
        Facture facture = new Facture()
            .numero(DEFAULT_NUMERO)
            .datePiece(DEFAULT_DATE_PIECE)
            .client(DEFAULT_CLIENT)
            .blVisa(DEFAULT_BL_VISA)
            .commentaire(DEFAULT_COMMENTAIRE)
            .factureRecuMail(DEFAULT_FACTURE_RECU_MAIL)
            .factureRecuPhysique(DEFAULT_FACTURE_RECU_PHYSIQUE)
            .blRecuMail(DEFAULT_BL_RECU_MAIL)
            .blRecuphysique(DEFAULT_BL_RECUPHYSIQUE);
        return facture;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Facture createUpdatedEntity(EntityManager em) {
        Facture facture = new Facture()
            .numero(UPDATED_NUMERO)
            .datePiece(UPDATED_DATE_PIECE)
            .client(UPDATED_CLIENT)
            .blVisa(UPDATED_BL_VISA)
            .commentaire(UPDATED_COMMENTAIRE)
            .factureRecuMail(UPDATED_FACTURE_RECU_MAIL)
            .factureRecuPhysique(UPDATED_FACTURE_RECU_PHYSIQUE)
            .blRecuMail(UPDATED_BL_RECU_MAIL)
            .blRecuphysique(UPDATED_BL_RECUPHYSIQUE);
        return facture;
    }

    @BeforeEach
    public void initTest() {
        facture = createEntity(em);
    }

    @Test
    @Transactional
    public void createFacture() throws Exception {
        int databaseSizeBeforeCreate = factureRepository.findAll().size();
        // Create the Facture
        FactureDTO factureDTO = factureMapper.toDto(facture);
        restFactureMockMvc.perform(post("/api/factures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(factureDTO)))
            .andExpect(status().isCreated());

        // Validate the Facture in the database
        List<Facture> factureList = factureRepository.findAll();
        assertThat(factureList).hasSize(databaseSizeBeforeCreate + 1);
        Facture testFacture = factureList.get(factureList.size() - 1);
        assertThat(testFacture.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testFacture.getDatePiece()).isEqualTo(DEFAULT_DATE_PIECE);
        assertThat(testFacture.getClient()).isEqualTo(DEFAULT_CLIENT);
        assertThat(testFacture.isBlVisa()).isEqualTo(DEFAULT_BL_VISA);
        assertThat(testFacture.getCommentaire()).isEqualTo(DEFAULT_COMMENTAIRE);
        assertThat(testFacture.getFactureRecuMail()).isEqualTo(DEFAULT_FACTURE_RECU_MAIL);
        assertThat(testFacture.getFactureRecuPhysique()).isEqualTo(DEFAULT_FACTURE_RECU_PHYSIQUE);
        assertThat(testFacture.getBlRecuMail()).isEqualTo(DEFAULT_BL_RECU_MAIL);
        assertThat(testFacture.getBlRecuphysique()).isEqualTo(DEFAULT_BL_RECUPHYSIQUE);
    }

    @Test
    @Transactional
    public void createFactureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = factureRepository.findAll().size();

        // Create the Facture with an existing ID
        facture.setId(1L);
        FactureDTO factureDTO = factureMapper.toDto(facture);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFactureMockMvc.perform(post("/api/factures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(factureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Facture in the database
        List<Facture> factureList = factureRepository.findAll();
        assertThat(factureList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = factureRepository.findAll().size();
        // set the field null
        facture.setNumero(null);

        // Create the Facture, which fails.
        FactureDTO factureDTO = factureMapper.toDto(facture);


        restFactureMockMvc.perform(post("/api/factures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(factureDTO)))
            .andExpect(status().isBadRequest());

        List<Facture> factureList = factureRepository.findAll();
        assertThat(factureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDatePieceIsRequired() throws Exception {
        int databaseSizeBeforeTest = factureRepository.findAll().size();
        // set the field null
        facture.setDatePiece(null);

        // Create the Facture, which fails.
        FactureDTO factureDTO = factureMapper.toDto(facture);


        restFactureMockMvc.perform(post("/api/factures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(factureDTO)))
            .andExpect(status().isBadRequest());

        List<Facture> factureList = factureRepository.findAll();
        assertThat(factureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClientIsRequired() throws Exception {
        int databaseSizeBeforeTest = factureRepository.findAll().size();
        // set the field null
        facture.setClient(null);

        // Create the Facture, which fails.
        FactureDTO factureDTO = factureMapper.toDto(facture);


        restFactureMockMvc.perform(post("/api/factures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(factureDTO)))
            .andExpect(status().isBadRequest());

        List<Facture> factureList = factureRepository.findAll();
        assertThat(factureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFactures() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList
        restFactureMockMvc.perform(get("/api/factures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(facture.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.intValue())))
            .andExpect(jsonPath("$.[*].datePiece").value(hasItem(DEFAULT_DATE_PIECE.toString())))
            .andExpect(jsonPath("$.[*].client").value(hasItem(DEFAULT_CLIENT)))
            .andExpect(jsonPath("$.[*].blVisa").value(hasItem(DEFAULT_BL_VISA.booleanValue())))
            .andExpect(jsonPath("$.[*].commentaire").value(hasItem(DEFAULT_COMMENTAIRE)))
            .andExpect(jsonPath("$.[*].factureRecuMail").value(hasItem(DEFAULT_FACTURE_RECU_MAIL.toString())))
            .andExpect(jsonPath("$.[*].factureRecuPhysique").value(hasItem(DEFAULT_FACTURE_RECU_PHYSIQUE.toString())))
            .andExpect(jsonPath("$.[*].blRecuMail").value(hasItem(DEFAULT_BL_RECU_MAIL.toString())))
            .andExpect(jsonPath("$.[*].blRecuphysique").value(hasItem(DEFAULT_BL_RECUPHYSIQUE.toString())));
    }
    
    @Test
    @Transactional
    public void getFacture() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get the facture
        restFactureMockMvc.perform(get("/api/factures/{id}", facture.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(facture.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO.intValue()))
            .andExpect(jsonPath("$.datePiece").value(DEFAULT_DATE_PIECE.toString()))
            .andExpect(jsonPath("$.client").value(DEFAULT_CLIENT))
            .andExpect(jsonPath("$.blVisa").value(DEFAULT_BL_VISA.booleanValue()))
            .andExpect(jsonPath("$.commentaire").value(DEFAULT_COMMENTAIRE))
            .andExpect(jsonPath("$.factureRecuMail").value(DEFAULT_FACTURE_RECU_MAIL.toString()))
            .andExpect(jsonPath("$.factureRecuPhysique").value(DEFAULT_FACTURE_RECU_PHYSIQUE.toString()))
            .andExpect(jsonPath("$.blRecuMail").value(DEFAULT_BL_RECU_MAIL.toString()))
            .andExpect(jsonPath("$.blRecuphysique").value(DEFAULT_BL_RECUPHYSIQUE.toString()));
    }


    @Test
    @Transactional
    public void getFacturesByIdFiltering() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        Long id = facture.getId();

        defaultFactureShouldBeFound("id.equals=" + id);
        defaultFactureShouldNotBeFound("id.notEquals=" + id);

        defaultFactureShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFactureShouldNotBeFound("id.greaterThan=" + id);

        defaultFactureShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFactureShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllFacturesByNumeroIsEqualToSomething() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where numero equals to DEFAULT_NUMERO
        defaultFactureShouldBeFound("numero.equals=" + DEFAULT_NUMERO);

        // Get all the factureList where numero equals to UPDATED_NUMERO
        defaultFactureShouldNotBeFound("numero.equals=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllFacturesByNumeroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where numero not equals to DEFAULT_NUMERO
        defaultFactureShouldNotBeFound("numero.notEquals=" + DEFAULT_NUMERO);

        // Get all the factureList where numero not equals to UPDATED_NUMERO
        defaultFactureShouldBeFound("numero.notEquals=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllFacturesByNumeroIsInShouldWork() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where numero in DEFAULT_NUMERO or UPDATED_NUMERO
        defaultFactureShouldBeFound("numero.in=" + DEFAULT_NUMERO + "," + UPDATED_NUMERO);

        // Get all the factureList where numero equals to UPDATED_NUMERO
        defaultFactureShouldNotBeFound("numero.in=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllFacturesByNumeroIsNullOrNotNull() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where numero is not null
        defaultFactureShouldBeFound("numero.specified=true");

        // Get all the factureList where numero is null
        defaultFactureShouldNotBeFound("numero.specified=false");
    }

    @Test
    @Transactional
    public void getAllFacturesByNumeroIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where numero is greater than or equal to DEFAULT_NUMERO
        defaultFactureShouldBeFound("numero.greaterThanOrEqual=" + DEFAULT_NUMERO);

        // Get all the factureList where numero is greater than or equal to UPDATED_NUMERO
        defaultFactureShouldNotBeFound("numero.greaterThanOrEqual=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllFacturesByNumeroIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where numero is less than or equal to DEFAULT_NUMERO
        defaultFactureShouldBeFound("numero.lessThanOrEqual=" + DEFAULT_NUMERO);

        // Get all the factureList where numero is less than or equal to SMALLER_NUMERO
        defaultFactureShouldNotBeFound("numero.lessThanOrEqual=" + SMALLER_NUMERO);
    }

    @Test
    @Transactional
    public void getAllFacturesByNumeroIsLessThanSomething() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where numero is less than DEFAULT_NUMERO
        defaultFactureShouldNotBeFound("numero.lessThan=" + DEFAULT_NUMERO);

        // Get all the factureList where numero is less than UPDATED_NUMERO
        defaultFactureShouldBeFound("numero.lessThan=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllFacturesByNumeroIsGreaterThanSomething() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where numero is greater than DEFAULT_NUMERO
        defaultFactureShouldNotBeFound("numero.greaterThan=" + DEFAULT_NUMERO);

        // Get all the factureList where numero is greater than SMALLER_NUMERO
        defaultFactureShouldBeFound("numero.greaterThan=" + SMALLER_NUMERO);
    }


    @Test
    @Transactional
    public void getAllFacturesByDatePieceIsEqualToSomething() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where datePiece equals to DEFAULT_DATE_PIECE
        defaultFactureShouldBeFound("datePiece.equals=" + DEFAULT_DATE_PIECE);

        // Get all the factureList where datePiece equals to UPDATED_DATE_PIECE
        defaultFactureShouldNotBeFound("datePiece.equals=" + UPDATED_DATE_PIECE);
    }

    @Test
    @Transactional
    public void getAllFacturesByDatePieceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where datePiece not equals to DEFAULT_DATE_PIECE
        defaultFactureShouldNotBeFound("datePiece.notEquals=" + DEFAULT_DATE_PIECE);

        // Get all the factureList where datePiece not equals to UPDATED_DATE_PIECE
        defaultFactureShouldBeFound("datePiece.notEquals=" + UPDATED_DATE_PIECE);
    }

    @Test
    @Transactional
    public void getAllFacturesByDatePieceIsInShouldWork() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where datePiece in DEFAULT_DATE_PIECE or UPDATED_DATE_PIECE
        defaultFactureShouldBeFound("datePiece.in=" + DEFAULT_DATE_PIECE + "," + UPDATED_DATE_PIECE);

        // Get all the factureList where datePiece equals to UPDATED_DATE_PIECE
        defaultFactureShouldNotBeFound("datePiece.in=" + UPDATED_DATE_PIECE);
    }

    @Test
    @Transactional
    public void getAllFacturesByDatePieceIsNullOrNotNull() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where datePiece is not null
        defaultFactureShouldBeFound("datePiece.specified=true");

        // Get all the factureList where datePiece is null
        defaultFactureShouldNotBeFound("datePiece.specified=false");
    }

    @Test
    @Transactional
    public void getAllFacturesByDatePieceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where datePiece is greater than or equal to DEFAULT_DATE_PIECE
        defaultFactureShouldBeFound("datePiece.greaterThanOrEqual=" + DEFAULT_DATE_PIECE);

        // Get all the factureList where datePiece is greater than or equal to UPDATED_DATE_PIECE
        defaultFactureShouldNotBeFound("datePiece.greaterThanOrEqual=" + UPDATED_DATE_PIECE);
    }

    @Test
    @Transactional
    public void getAllFacturesByDatePieceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where datePiece is less than or equal to DEFAULT_DATE_PIECE
        defaultFactureShouldBeFound("datePiece.lessThanOrEqual=" + DEFAULT_DATE_PIECE);

        // Get all the factureList where datePiece is less than or equal to SMALLER_DATE_PIECE
        defaultFactureShouldNotBeFound("datePiece.lessThanOrEqual=" + SMALLER_DATE_PIECE);
    }

    @Test
    @Transactional
    public void getAllFacturesByDatePieceIsLessThanSomething() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where datePiece is less than DEFAULT_DATE_PIECE
        defaultFactureShouldNotBeFound("datePiece.lessThan=" + DEFAULT_DATE_PIECE);

        // Get all the factureList where datePiece is less than UPDATED_DATE_PIECE
        defaultFactureShouldBeFound("datePiece.lessThan=" + UPDATED_DATE_PIECE);
    }

    @Test
    @Transactional
    public void getAllFacturesByDatePieceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where datePiece is greater than DEFAULT_DATE_PIECE
        defaultFactureShouldNotBeFound("datePiece.greaterThan=" + DEFAULT_DATE_PIECE);

        // Get all the factureList where datePiece is greater than SMALLER_DATE_PIECE
        defaultFactureShouldBeFound("datePiece.greaterThan=" + SMALLER_DATE_PIECE);
    }


    @Test
    @Transactional
    public void getAllFacturesByClientIsEqualToSomething() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where client equals to DEFAULT_CLIENT
        defaultFactureShouldBeFound("client.equals=" + DEFAULT_CLIENT);

        // Get all the factureList where client equals to UPDATED_CLIENT
        defaultFactureShouldNotBeFound("client.equals=" + UPDATED_CLIENT);
    }

    @Test
    @Transactional
    public void getAllFacturesByClientIsNotEqualToSomething() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where client not equals to DEFAULT_CLIENT
        defaultFactureShouldNotBeFound("client.notEquals=" + DEFAULT_CLIENT);

        // Get all the factureList where client not equals to UPDATED_CLIENT
        defaultFactureShouldBeFound("client.notEquals=" + UPDATED_CLIENT);
    }

    @Test
    @Transactional
    public void getAllFacturesByClientIsInShouldWork() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where client in DEFAULT_CLIENT or UPDATED_CLIENT
        defaultFactureShouldBeFound("client.in=" + DEFAULT_CLIENT + "," + UPDATED_CLIENT);

        // Get all the factureList where client equals to UPDATED_CLIENT
        defaultFactureShouldNotBeFound("client.in=" + UPDATED_CLIENT);
    }

    @Test
    @Transactional
    public void getAllFacturesByClientIsNullOrNotNull() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where client is not null
        defaultFactureShouldBeFound("client.specified=true");

        // Get all the factureList where client is null
        defaultFactureShouldNotBeFound("client.specified=false");
    }
                @Test
    @Transactional
    public void getAllFacturesByClientContainsSomething() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where client contains DEFAULT_CLIENT
        defaultFactureShouldBeFound("client.contains=" + DEFAULT_CLIENT);

        // Get all the factureList where client contains UPDATED_CLIENT
        defaultFactureShouldNotBeFound("client.contains=" + UPDATED_CLIENT);
    }

    @Test
    @Transactional
    public void getAllFacturesByClientNotContainsSomething() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where client does not contain DEFAULT_CLIENT
        defaultFactureShouldNotBeFound("client.doesNotContain=" + DEFAULT_CLIENT);

        // Get all the factureList where client does not contain UPDATED_CLIENT
        defaultFactureShouldBeFound("client.doesNotContain=" + UPDATED_CLIENT);
    }


    @Test
    @Transactional
    public void getAllFacturesByBlVisaIsEqualToSomething() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where blVisa equals to DEFAULT_BL_VISA
        defaultFactureShouldBeFound("blVisa.equals=" + DEFAULT_BL_VISA);

        // Get all the factureList where blVisa equals to UPDATED_BL_VISA
        defaultFactureShouldNotBeFound("blVisa.equals=" + UPDATED_BL_VISA);
    }

    @Test
    @Transactional
    public void getAllFacturesByBlVisaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where blVisa not equals to DEFAULT_BL_VISA
        defaultFactureShouldNotBeFound("blVisa.notEquals=" + DEFAULT_BL_VISA);

        // Get all the factureList where blVisa not equals to UPDATED_BL_VISA
        defaultFactureShouldBeFound("blVisa.notEquals=" + UPDATED_BL_VISA);
    }

    @Test
    @Transactional
    public void getAllFacturesByBlVisaIsInShouldWork() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where blVisa in DEFAULT_BL_VISA or UPDATED_BL_VISA
        defaultFactureShouldBeFound("blVisa.in=" + DEFAULT_BL_VISA + "," + UPDATED_BL_VISA);

        // Get all the factureList where blVisa equals to UPDATED_BL_VISA
        defaultFactureShouldNotBeFound("blVisa.in=" + UPDATED_BL_VISA);
    }

    @Test
    @Transactional
    public void getAllFacturesByBlVisaIsNullOrNotNull() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where blVisa is not null
        defaultFactureShouldBeFound("blVisa.specified=true");

        // Get all the factureList where blVisa is null
        defaultFactureShouldNotBeFound("blVisa.specified=false");
    }

    @Test
    @Transactional
    public void getAllFacturesByCommentaireIsEqualToSomething() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where commentaire equals to DEFAULT_COMMENTAIRE
        defaultFactureShouldBeFound("commentaire.equals=" + DEFAULT_COMMENTAIRE);

        // Get all the factureList where commentaire equals to UPDATED_COMMENTAIRE
        defaultFactureShouldNotBeFound("commentaire.equals=" + UPDATED_COMMENTAIRE);
    }

    @Test
    @Transactional
    public void getAllFacturesByCommentaireIsNotEqualToSomething() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where commentaire not equals to DEFAULT_COMMENTAIRE
        defaultFactureShouldNotBeFound("commentaire.notEquals=" + DEFAULT_COMMENTAIRE);

        // Get all the factureList where commentaire not equals to UPDATED_COMMENTAIRE
        defaultFactureShouldBeFound("commentaire.notEquals=" + UPDATED_COMMENTAIRE);
    }

    @Test
    @Transactional
    public void getAllFacturesByCommentaireIsInShouldWork() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where commentaire in DEFAULT_COMMENTAIRE or UPDATED_COMMENTAIRE
        defaultFactureShouldBeFound("commentaire.in=" + DEFAULT_COMMENTAIRE + "," + UPDATED_COMMENTAIRE);

        // Get all the factureList where commentaire equals to UPDATED_COMMENTAIRE
        defaultFactureShouldNotBeFound("commentaire.in=" + UPDATED_COMMENTAIRE);
    }

    @Test
    @Transactional
    public void getAllFacturesByCommentaireIsNullOrNotNull() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where commentaire is not null
        defaultFactureShouldBeFound("commentaire.specified=true");

        // Get all the factureList where commentaire is null
        defaultFactureShouldNotBeFound("commentaire.specified=false");
    }
                @Test
    @Transactional
    public void getAllFacturesByCommentaireContainsSomething() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where commentaire contains DEFAULT_COMMENTAIRE
        defaultFactureShouldBeFound("commentaire.contains=" + DEFAULT_COMMENTAIRE);

        // Get all the factureList where commentaire contains UPDATED_COMMENTAIRE
        defaultFactureShouldNotBeFound("commentaire.contains=" + UPDATED_COMMENTAIRE);
    }

    @Test
    @Transactional
    public void getAllFacturesByCommentaireNotContainsSomething() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where commentaire does not contain DEFAULT_COMMENTAIRE
        defaultFactureShouldNotBeFound("commentaire.doesNotContain=" + DEFAULT_COMMENTAIRE);

        // Get all the factureList where commentaire does not contain UPDATED_COMMENTAIRE
        defaultFactureShouldBeFound("commentaire.doesNotContain=" + UPDATED_COMMENTAIRE);
    }


    @Test
    @Transactional
    public void getAllFacturesByFactureRecuMailIsEqualToSomething() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where factureRecuMail equals to DEFAULT_FACTURE_RECU_MAIL
        defaultFactureShouldBeFound("factureRecuMail.equals=" + DEFAULT_FACTURE_RECU_MAIL);

        // Get all the factureList where factureRecuMail equals to UPDATED_FACTURE_RECU_MAIL
        defaultFactureShouldNotBeFound("factureRecuMail.equals=" + UPDATED_FACTURE_RECU_MAIL);
    }

    @Test
    @Transactional
    public void getAllFacturesByFactureRecuMailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where factureRecuMail not equals to DEFAULT_FACTURE_RECU_MAIL
        defaultFactureShouldNotBeFound("factureRecuMail.notEquals=" + DEFAULT_FACTURE_RECU_MAIL);

        // Get all the factureList where factureRecuMail not equals to UPDATED_FACTURE_RECU_MAIL
        defaultFactureShouldBeFound("factureRecuMail.notEquals=" + UPDATED_FACTURE_RECU_MAIL);
    }

    @Test
    @Transactional
    public void getAllFacturesByFactureRecuMailIsInShouldWork() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where factureRecuMail in DEFAULT_FACTURE_RECU_MAIL or UPDATED_FACTURE_RECU_MAIL
        defaultFactureShouldBeFound("factureRecuMail.in=" + DEFAULT_FACTURE_RECU_MAIL + "," + UPDATED_FACTURE_RECU_MAIL);

        // Get all the factureList where factureRecuMail equals to UPDATED_FACTURE_RECU_MAIL
        defaultFactureShouldNotBeFound("factureRecuMail.in=" + UPDATED_FACTURE_RECU_MAIL);
    }

    @Test
    @Transactional
    public void getAllFacturesByFactureRecuMailIsNullOrNotNull() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where factureRecuMail is not null
        defaultFactureShouldBeFound("factureRecuMail.specified=true");

        // Get all the factureList where factureRecuMail is null
        defaultFactureShouldNotBeFound("factureRecuMail.specified=false");
    }

    @Test
    @Transactional
    public void getAllFacturesByFactureRecuMailIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where factureRecuMail is greater than or equal to DEFAULT_FACTURE_RECU_MAIL
        defaultFactureShouldBeFound("factureRecuMail.greaterThanOrEqual=" + DEFAULT_FACTURE_RECU_MAIL);

        // Get all the factureList where factureRecuMail is greater than or equal to UPDATED_FACTURE_RECU_MAIL
        defaultFactureShouldNotBeFound("factureRecuMail.greaterThanOrEqual=" + UPDATED_FACTURE_RECU_MAIL);
    }

    @Test
    @Transactional
    public void getAllFacturesByFactureRecuMailIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where factureRecuMail is less than or equal to DEFAULT_FACTURE_RECU_MAIL
        defaultFactureShouldBeFound("factureRecuMail.lessThanOrEqual=" + DEFAULT_FACTURE_RECU_MAIL);

        // Get all the factureList where factureRecuMail is less than or equal to SMALLER_FACTURE_RECU_MAIL
        defaultFactureShouldNotBeFound("factureRecuMail.lessThanOrEqual=" + SMALLER_FACTURE_RECU_MAIL);
    }

    @Test
    @Transactional
    public void getAllFacturesByFactureRecuMailIsLessThanSomething() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where factureRecuMail is less than DEFAULT_FACTURE_RECU_MAIL
        defaultFactureShouldNotBeFound("factureRecuMail.lessThan=" + DEFAULT_FACTURE_RECU_MAIL);

        // Get all the factureList where factureRecuMail is less than UPDATED_FACTURE_RECU_MAIL
        defaultFactureShouldBeFound("factureRecuMail.lessThan=" + UPDATED_FACTURE_RECU_MAIL);
    }

    @Test
    @Transactional
    public void getAllFacturesByFactureRecuMailIsGreaterThanSomething() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where factureRecuMail is greater than DEFAULT_FACTURE_RECU_MAIL
        defaultFactureShouldNotBeFound("factureRecuMail.greaterThan=" + DEFAULT_FACTURE_RECU_MAIL);

        // Get all the factureList where factureRecuMail is greater than SMALLER_FACTURE_RECU_MAIL
        defaultFactureShouldBeFound("factureRecuMail.greaterThan=" + SMALLER_FACTURE_RECU_MAIL);
    }


    @Test
    @Transactional
    public void getAllFacturesByFactureRecuPhysiqueIsEqualToSomething() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where factureRecuPhysique equals to DEFAULT_FACTURE_RECU_PHYSIQUE
        defaultFactureShouldBeFound("factureRecuPhysique.equals=" + DEFAULT_FACTURE_RECU_PHYSIQUE);

        // Get all the factureList where factureRecuPhysique equals to UPDATED_FACTURE_RECU_PHYSIQUE
        defaultFactureShouldNotBeFound("factureRecuPhysique.equals=" + UPDATED_FACTURE_RECU_PHYSIQUE);
    }

    @Test
    @Transactional
    public void getAllFacturesByFactureRecuPhysiqueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where factureRecuPhysique not equals to DEFAULT_FACTURE_RECU_PHYSIQUE
        defaultFactureShouldNotBeFound("factureRecuPhysique.notEquals=" + DEFAULT_FACTURE_RECU_PHYSIQUE);

        // Get all the factureList where factureRecuPhysique not equals to UPDATED_FACTURE_RECU_PHYSIQUE
        defaultFactureShouldBeFound("factureRecuPhysique.notEquals=" + UPDATED_FACTURE_RECU_PHYSIQUE);
    }

    @Test
    @Transactional
    public void getAllFacturesByFactureRecuPhysiqueIsInShouldWork() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where factureRecuPhysique in DEFAULT_FACTURE_RECU_PHYSIQUE or UPDATED_FACTURE_RECU_PHYSIQUE
        defaultFactureShouldBeFound("factureRecuPhysique.in=" + DEFAULT_FACTURE_RECU_PHYSIQUE + "," + UPDATED_FACTURE_RECU_PHYSIQUE);

        // Get all the factureList where factureRecuPhysique equals to UPDATED_FACTURE_RECU_PHYSIQUE
        defaultFactureShouldNotBeFound("factureRecuPhysique.in=" + UPDATED_FACTURE_RECU_PHYSIQUE);
    }

    @Test
    @Transactional
    public void getAllFacturesByFactureRecuPhysiqueIsNullOrNotNull() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where factureRecuPhysique is not null
        defaultFactureShouldBeFound("factureRecuPhysique.specified=true");

        // Get all the factureList where factureRecuPhysique is null
        defaultFactureShouldNotBeFound("factureRecuPhysique.specified=false");
    }

    @Test
    @Transactional
    public void getAllFacturesByFactureRecuPhysiqueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where factureRecuPhysique is greater than or equal to DEFAULT_FACTURE_RECU_PHYSIQUE
        defaultFactureShouldBeFound("factureRecuPhysique.greaterThanOrEqual=" + DEFAULT_FACTURE_RECU_PHYSIQUE);

        // Get all the factureList where factureRecuPhysique is greater than or equal to UPDATED_FACTURE_RECU_PHYSIQUE
        defaultFactureShouldNotBeFound("factureRecuPhysique.greaterThanOrEqual=" + UPDATED_FACTURE_RECU_PHYSIQUE);
    }

    @Test
    @Transactional
    public void getAllFacturesByFactureRecuPhysiqueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where factureRecuPhysique is less than or equal to DEFAULT_FACTURE_RECU_PHYSIQUE
        defaultFactureShouldBeFound("factureRecuPhysique.lessThanOrEqual=" + DEFAULT_FACTURE_RECU_PHYSIQUE);

        // Get all the factureList where factureRecuPhysique is less than or equal to SMALLER_FACTURE_RECU_PHYSIQUE
        defaultFactureShouldNotBeFound("factureRecuPhysique.lessThanOrEqual=" + SMALLER_FACTURE_RECU_PHYSIQUE);
    }

    @Test
    @Transactional
    public void getAllFacturesByFactureRecuPhysiqueIsLessThanSomething() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where factureRecuPhysique is less than DEFAULT_FACTURE_RECU_PHYSIQUE
        defaultFactureShouldNotBeFound("factureRecuPhysique.lessThan=" + DEFAULT_FACTURE_RECU_PHYSIQUE);

        // Get all the factureList where factureRecuPhysique is less than UPDATED_FACTURE_RECU_PHYSIQUE
        defaultFactureShouldBeFound("factureRecuPhysique.lessThan=" + UPDATED_FACTURE_RECU_PHYSIQUE);
    }

    @Test
    @Transactional
    public void getAllFacturesByFactureRecuPhysiqueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where factureRecuPhysique is greater than DEFAULT_FACTURE_RECU_PHYSIQUE
        defaultFactureShouldNotBeFound("factureRecuPhysique.greaterThan=" + DEFAULT_FACTURE_RECU_PHYSIQUE);

        // Get all the factureList where factureRecuPhysique is greater than SMALLER_FACTURE_RECU_PHYSIQUE
        defaultFactureShouldBeFound("factureRecuPhysique.greaterThan=" + SMALLER_FACTURE_RECU_PHYSIQUE);
    }


    @Test
    @Transactional
    public void getAllFacturesByBlRecuMailIsEqualToSomething() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where blRecuMail equals to DEFAULT_BL_RECU_MAIL
        defaultFactureShouldBeFound("blRecuMail.equals=" + DEFAULT_BL_RECU_MAIL);

        // Get all the factureList where blRecuMail equals to UPDATED_BL_RECU_MAIL
        defaultFactureShouldNotBeFound("blRecuMail.equals=" + UPDATED_BL_RECU_MAIL);
    }

    @Test
    @Transactional
    public void getAllFacturesByBlRecuMailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where blRecuMail not equals to DEFAULT_BL_RECU_MAIL
        defaultFactureShouldNotBeFound("blRecuMail.notEquals=" + DEFAULT_BL_RECU_MAIL);

        // Get all the factureList where blRecuMail not equals to UPDATED_BL_RECU_MAIL
        defaultFactureShouldBeFound("blRecuMail.notEquals=" + UPDATED_BL_RECU_MAIL);
    }

    @Test
    @Transactional
    public void getAllFacturesByBlRecuMailIsInShouldWork() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where blRecuMail in DEFAULT_BL_RECU_MAIL or UPDATED_BL_RECU_MAIL
        defaultFactureShouldBeFound("blRecuMail.in=" + DEFAULT_BL_RECU_MAIL + "," + UPDATED_BL_RECU_MAIL);

        // Get all the factureList where blRecuMail equals to UPDATED_BL_RECU_MAIL
        defaultFactureShouldNotBeFound("blRecuMail.in=" + UPDATED_BL_RECU_MAIL);
    }

    @Test
    @Transactional
    public void getAllFacturesByBlRecuMailIsNullOrNotNull() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where blRecuMail is not null
        defaultFactureShouldBeFound("blRecuMail.specified=true");

        // Get all the factureList where blRecuMail is null
        defaultFactureShouldNotBeFound("blRecuMail.specified=false");
    }

    @Test
    @Transactional
    public void getAllFacturesByBlRecuMailIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where blRecuMail is greater than or equal to DEFAULT_BL_RECU_MAIL
        defaultFactureShouldBeFound("blRecuMail.greaterThanOrEqual=" + DEFAULT_BL_RECU_MAIL);

        // Get all the factureList where blRecuMail is greater than or equal to UPDATED_BL_RECU_MAIL
        defaultFactureShouldNotBeFound("blRecuMail.greaterThanOrEqual=" + UPDATED_BL_RECU_MAIL);
    }

    @Test
    @Transactional
    public void getAllFacturesByBlRecuMailIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where blRecuMail is less than or equal to DEFAULT_BL_RECU_MAIL
        defaultFactureShouldBeFound("blRecuMail.lessThanOrEqual=" + DEFAULT_BL_RECU_MAIL);

        // Get all the factureList where blRecuMail is less than or equal to SMALLER_BL_RECU_MAIL
        defaultFactureShouldNotBeFound("blRecuMail.lessThanOrEqual=" + SMALLER_BL_RECU_MAIL);
    }

    @Test
    @Transactional
    public void getAllFacturesByBlRecuMailIsLessThanSomething() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where blRecuMail is less than DEFAULT_BL_RECU_MAIL
        defaultFactureShouldNotBeFound("blRecuMail.lessThan=" + DEFAULT_BL_RECU_MAIL);

        // Get all the factureList where blRecuMail is less than UPDATED_BL_RECU_MAIL
        defaultFactureShouldBeFound("blRecuMail.lessThan=" + UPDATED_BL_RECU_MAIL);
    }

    @Test
    @Transactional
    public void getAllFacturesByBlRecuMailIsGreaterThanSomething() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where blRecuMail is greater than DEFAULT_BL_RECU_MAIL
        defaultFactureShouldNotBeFound("blRecuMail.greaterThan=" + DEFAULT_BL_RECU_MAIL);

        // Get all the factureList where blRecuMail is greater than SMALLER_BL_RECU_MAIL
        defaultFactureShouldBeFound("blRecuMail.greaterThan=" + SMALLER_BL_RECU_MAIL);
    }


    @Test
    @Transactional
    public void getAllFacturesByBlRecuphysiqueIsEqualToSomething() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where blRecuphysique equals to DEFAULT_BL_RECUPHYSIQUE
        defaultFactureShouldBeFound("blRecuphysique.equals=" + DEFAULT_BL_RECUPHYSIQUE);

        // Get all the factureList where blRecuphysique equals to UPDATED_BL_RECUPHYSIQUE
        defaultFactureShouldNotBeFound("blRecuphysique.equals=" + UPDATED_BL_RECUPHYSIQUE);
    }

    @Test
    @Transactional
    public void getAllFacturesByBlRecuphysiqueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where blRecuphysique not equals to DEFAULT_BL_RECUPHYSIQUE
        defaultFactureShouldNotBeFound("blRecuphysique.notEquals=" + DEFAULT_BL_RECUPHYSIQUE);

        // Get all the factureList where blRecuphysique not equals to UPDATED_BL_RECUPHYSIQUE
        defaultFactureShouldBeFound("blRecuphysique.notEquals=" + UPDATED_BL_RECUPHYSIQUE);
    }

    @Test
    @Transactional
    public void getAllFacturesByBlRecuphysiqueIsInShouldWork() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where blRecuphysique in DEFAULT_BL_RECUPHYSIQUE or UPDATED_BL_RECUPHYSIQUE
        defaultFactureShouldBeFound("blRecuphysique.in=" + DEFAULT_BL_RECUPHYSIQUE + "," + UPDATED_BL_RECUPHYSIQUE);

        // Get all the factureList where blRecuphysique equals to UPDATED_BL_RECUPHYSIQUE
        defaultFactureShouldNotBeFound("blRecuphysique.in=" + UPDATED_BL_RECUPHYSIQUE);
    }

    @Test
    @Transactional
    public void getAllFacturesByBlRecuphysiqueIsNullOrNotNull() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where blRecuphysique is not null
        defaultFactureShouldBeFound("blRecuphysique.specified=true");

        // Get all the factureList where blRecuphysique is null
        defaultFactureShouldNotBeFound("blRecuphysique.specified=false");
    }

    @Test
    @Transactional
    public void getAllFacturesByBlRecuphysiqueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where blRecuphysique is greater than or equal to DEFAULT_BL_RECUPHYSIQUE
        defaultFactureShouldBeFound("blRecuphysique.greaterThanOrEqual=" + DEFAULT_BL_RECUPHYSIQUE);

        // Get all the factureList where blRecuphysique is greater than or equal to UPDATED_BL_RECUPHYSIQUE
        defaultFactureShouldNotBeFound("blRecuphysique.greaterThanOrEqual=" + UPDATED_BL_RECUPHYSIQUE);
    }

    @Test
    @Transactional
    public void getAllFacturesByBlRecuphysiqueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where blRecuphysique is less than or equal to DEFAULT_BL_RECUPHYSIQUE
        defaultFactureShouldBeFound("blRecuphysique.lessThanOrEqual=" + DEFAULT_BL_RECUPHYSIQUE);

        // Get all the factureList where blRecuphysique is less than or equal to SMALLER_BL_RECUPHYSIQUE
        defaultFactureShouldNotBeFound("blRecuphysique.lessThanOrEqual=" + SMALLER_BL_RECUPHYSIQUE);
    }

    @Test
    @Transactional
    public void getAllFacturesByBlRecuphysiqueIsLessThanSomething() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where blRecuphysique is less than DEFAULT_BL_RECUPHYSIQUE
        defaultFactureShouldNotBeFound("blRecuphysique.lessThan=" + DEFAULT_BL_RECUPHYSIQUE);

        // Get all the factureList where blRecuphysique is less than UPDATED_BL_RECUPHYSIQUE
        defaultFactureShouldBeFound("blRecuphysique.lessThan=" + UPDATED_BL_RECUPHYSIQUE);
    }

    @Test
    @Transactional
    public void getAllFacturesByBlRecuphysiqueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        // Get all the factureList where blRecuphysique is greater than DEFAULT_BL_RECUPHYSIQUE
        defaultFactureShouldNotBeFound("blRecuphysique.greaterThan=" + DEFAULT_BL_RECUPHYSIQUE);

        // Get all the factureList where blRecuphysique is greater than SMALLER_BL_RECUPHYSIQUE
        defaultFactureShouldBeFound("blRecuphysique.greaterThan=" + SMALLER_BL_RECUPHYSIQUE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFactureShouldBeFound(String filter) throws Exception {
        restFactureMockMvc.perform(get("/api/factures?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(facture.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.intValue())))
            .andExpect(jsonPath("$.[*].datePiece").value(hasItem(DEFAULT_DATE_PIECE.toString())))
            .andExpect(jsonPath("$.[*].client").value(hasItem(DEFAULT_CLIENT)))
            .andExpect(jsonPath("$.[*].blVisa").value(hasItem(DEFAULT_BL_VISA.booleanValue())))
            .andExpect(jsonPath("$.[*].commentaire").value(hasItem(DEFAULT_COMMENTAIRE)))
            .andExpect(jsonPath("$.[*].factureRecuMail").value(hasItem(DEFAULT_FACTURE_RECU_MAIL.toString())))
            .andExpect(jsonPath("$.[*].factureRecuPhysique").value(hasItem(DEFAULT_FACTURE_RECU_PHYSIQUE.toString())))
            .andExpect(jsonPath("$.[*].blRecuMail").value(hasItem(DEFAULT_BL_RECU_MAIL.toString())))
            .andExpect(jsonPath("$.[*].blRecuphysique").value(hasItem(DEFAULT_BL_RECUPHYSIQUE.toString())));

        // Check, that the count call also returns 1
        restFactureMockMvc.perform(get("/api/factures/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFactureShouldNotBeFound(String filter) throws Exception {
        restFactureMockMvc.perform(get("/api/factures?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFactureMockMvc.perform(get("/api/factures/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingFacture() throws Exception {
        // Get the facture
        restFactureMockMvc.perform(get("/api/factures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFacture() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        int databaseSizeBeforeUpdate = factureRepository.findAll().size();

        // Update the facture
        Facture updatedFacture = factureRepository.findById(facture.getId()).get();
        // Disconnect from session so that the updates on updatedFacture are not directly saved in db
        em.detach(updatedFacture);
        updatedFacture
            .numero(UPDATED_NUMERO)
            .datePiece(UPDATED_DATE_PIECE)
            .client(UPDATED_CLIENT)
            .blVisa(UPDATED_BL_VISA)
            .commentaire(UPDATED_COMMENTAIRE)
            .factureRecuMail(UPDATED_FACTURE_RECU_MAIL)
            .factureRecuPhysique(UPDATED_FACTURE_RECU_PHYSIQUE)
            .blRecuMail(UPDATED_BL_RECU_MAIL)
            .blRecuphysique(UPDATED_BL_RECUPHYSIQUE);
        FactureDTO factureDTO = factureMapper.toDto(updatedFacture);

        restFactureMockMvc.perform(put("/api/factures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(factureDTO)))
            .andExpect(status().isOk());

        // Validate the Facture in the database
        List<Facture> factureList = factureRepository.findAll();
        assertThat(factureList).hasSize(databaseSizeBeforeUpdate);
        Facture testFacture = factureList.get(factureList.size() - 1);
        assertThat(testFacture.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testFacture.getDatePiece()).isEqualTo(UPDATED_DATE_PIECE);
        assertThat(testFacture.getClient()).isEqualTo(UPDATED_CLIENT);
        assertThat(testFacture.isBlVisa()).isEqualTo(UPDATED_BL_VISA);
        assertThat(testFacture.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
        assertThat(testFacture.getFactureRecuMail()).isEqualTo(UPDATED_FACTURE_RECU_MAIL);
        assertThat(testFacture.getFactureRecuPhysique()).isEqualTo(UPDATED_FACTURE_RECU_PHYSIQUE);
        assertThat(testFacture.getBlRecuMail()).isEqualTo(UPDATED_BL_RECU_MAIL);
        assertThat(testFacture.getBlRecuphysique()).isEqualTo(UPDATED_BL_RECUPHYSIQUE);
    }

    @Test
    @Transactional
    public void updateNonExistingFacture() throws Exception {
        int databaseSizeBeforeUpdate = factureRepository.findAll().size();

        // Create the Facture
        FactureDTO factureDTO = factureMapper.toDto(facture);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFactureMockMvc.perform(put("/api/factures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(factureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Facture in the database
        List<Facture> factureList = factureRepository.findAll();
        assertThat(factureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFacture() throws Exception {
        // Initialize the database
        factureRepository.saveAndFlush(facture);

        int databaseSizeBeforeDelete = factureRepository.findAll().size();

        // Delete the facture
        restFactureMockMvc.perform(delete("/api/factures/{id}", facture.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Facture> factureList = factureRepository.findAll();
        assertThat(factureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
