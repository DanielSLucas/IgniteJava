package br.com.daniellucas.gestao_vagas.modules.company.controllers;


import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.daniellucas.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.daniellucas.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.daniellucas.gestao_vagas.modules.company.repositories.CompanyRepository;
import br.com.daniellucas.gestao_vagas.utils.TestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CreateJobControllerTest {
  private MockMvc mvc;

  @Autowired
  private WebApplicationContext context;

  @Autowired
  private CompanyRepository companyRepository;

  @Before
  public void setup() {
    mvc = MockMvcBuilders
      .webAppContextSetup(context)
      .apply(SecurityMockMvcConfigurers.springSecurity())
      .build();
  }

  @Test
  public void should_be_able_to_create_a_new_job() throws Exception {
    var company = CompanyEntity.builder()
      .description("Test description")
      .email("company@email.com")
      .password("0123456789")
      .username("testusername")
      .name("test name")
      .build();
    
    company = this.companyRepository.saveAndFlush(company);

    CreateJobDTO createJobDTO = CreateJobDTO.builder()
      .benefits("Test benefits")
      .description("Test description")
      .level("test level")
      .build();

    var result = mvc.perform(
      MockMvcRequestBuilders.post("/jobs/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(TestUtils.objectToJSON(createJobDTO))
        .header("Authorization", TestUtils.generateToken(company.getId()))
    ).andExpect(MockMvcResultMatchers.status().isCreated());
  }

  @Test
  public void should_no_be_able_to_create_a_new_job_if_company_not_found() throws Exception {
    CreateJobDTO createJobDTO = CreateJobDTO.builder()
      .benefits("Test benefits")
      .description("Test description")
      .level("test level")
      .build();

    mvc.perform(
      MockMvcRequestBuilders.post("/jobs/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(TestUtils.objectToJSON(createJobDTO))
        .header("Authorization", TestUtils.generateToken(UUID.randomUUID()))
    ).andExpect(MockMvcResultMatchers.status().is(400));
  }

}
