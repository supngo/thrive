package com.naturecode.thrive.properties.controller;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.naturecode.thrive.properties.model.SiteIntegration;
import com.naturecode.thrive.properties.service.PropertyService;

@SpringBootTest
@AutoConfigureMockMvc
public class PropertyControllerTest {
  @Autowired
  private MockMvc mockMvc;
  
  @MockBean
  private PropertyService propertyServiceMock;
  
  @Test
  public void createSiteIntegrationTest_Success() throws Exception {
    doNothing().when(propertyServiceMock).createSiteIntegration(isA(SiteIntegration.class), isA(String.class));
    mockMvc.perform(MockMvcRequestBuilders.post("/properties/integration/site/ckrc88qf6000p01063tarr66f")
      .contentType(MediaType.APPLICATION_JSON)
      .content("{\"host\":\"api.com\", \"port\": 104, \"enabled\": true}"))
      .andExpect(status().isCreated());
  }
}
