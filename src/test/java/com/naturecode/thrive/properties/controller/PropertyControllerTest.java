package com.naturecode.thrive.properties.controller;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.containsString;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.naturecode.thrive.properties.exception.PropertyException;
import com.naturecode.thrive.properties.model.SiteIntegration;
import com.naturecode.thrive.properties.service.PropertyService;

@SpringBootTest
@AutoConfigureMockMvc
public class PropertyControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext webApplicationContext;
  
  @MockBean
  private PropertyService propertyServiceMock;

  @BeforeEach
  public void setup() throws Exception {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
  }
  
  @Test
  public void createSiteIntegrationTest_Success() throws Exception {
    doNothing().when(propertyServiceMock).createSiteIntegration(isA(SiteIntegration.class), isA(String.class));
    mockMvc.perform(MockMvcRequestBuilders.post("/properties/integration/site/ckrc88qf6000p01063tarr66f")
      .contentType(MediaType.APPLICATION_JSON)
      .content("{\"host\":\"api.com\", \"port\": 104, \"enabled\": true}"))
      .andExpect(status().isCreated());
  }

  @Test
  public void createSiteIntegrationTest_PropertyException () throws Exception {
    doThrow(new PropertyException("error")).when(propertyServiceMock).createSiteIntegration(isA(SiteIntegration.class), isA(String.class));
    mockMvc.perform(MockMvcRequestBuilders.post("/properties/integration/site/ckrc88qf6000p01063tarr66f")
      .contentType(MediaType.APPLICATION_JSON)
      .content("{\"host\":\"api.com\", \"port\": 104, \"enabled\": true}"))
      .andExpect(status().isBadRequest());
  }

  @Test
  public void createSiteIntegrationTest_Exception () throws Exception {
    doThrow(new RuntimeException()).when(propertyServiceMock).createSiteIntegration(isA(SiteIntegration.class), isA(String.class));
    mockMvc.perform(MockMvcRequestBuilders.post("/properties/integration/site/ckrc88qf6000p01063tarr66f")
      .contentType(MediaType.APPLICATION_JSON)
      .content("{\"host\":\"api.com\", \"port\": 104, \"enabled\": true}"))
      .andExpect(status().is5xxServerError());
  }

  @Test
  public void listSiteIntegrationsTest_Success() throws Exception {
    List<SiteIntegration> mockSiteIntegrationList = new ArrayList<SiteIntegration>();
    mockSiteIntegrationList.add(new SiteIntegration("ckrc88qf6000p01063tarr66f", "api.com", 104, true));
    mockSiteIntegrationList.add(new SiteIntegration("ckrc88qf6000p01063tarr66f", "api.net", 105, false));
    when(propertyServiceMock.listSiteIntegrations(isA(String.class))).thenReturn(mockSiteIntegrationList);

    mockMvc.perform(MockMvcRequestBuilders.get("/properties/integration/site/ckrc88qf6000p01063tarr66f")
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(content().string(containsString("[{\"id\":\"ckrc88qf6000p01063tarr66f\",\"host\":\"api.com\",\"port\":104,\"enabled\":true},{\"id\":\"ckrc88qf6000p01063tarr66f\",\"host\":\"api.net\",\"port\":105,\"enabled\":false}]")));
  }

  @Test
  public void listSiteIntegrationsTest_PropertyException() throws Exception {
    when(propertyServiceMock.listSiteIntegrations(isA(String.class))).thenThrow(new PropertyException("error"));
    mockMvc.perform(MockMvcRequestBuilders.get("/properties/integration/site/ckrc88qf6000p01063tarr66f")
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isBadRequest());
  }

  @Test
  public void listSiteIntegrationsTest_Exception() throws Exception {
    when(propertyServiceMock.listSiteIntegrations(isA(String.class))).thenThrow(new RuntimeException());
    mockMvc.perform(MockMvcRequestBuilders.get("/properties/integration/site/ckrc88qf6000p01063tarr66f")
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().is5xxServerError());
  }

  @Test
  public void deleteSiteIntegrations_Success() throws Exception {
    doNothing().when(propertyServiceMock).deleteSiteIntegrations(isA(String.class), isA(String.class));
    mockMvc.perform(MockMvcRequestBuilders.delete("/properties/integration/site/ckrc88qf6000p01063tarr66f/index/1")
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk());  
  }

  @Test
  public void deleteSiteIntegrations_PropertyException() throws Exception {
    doThrow(new PropertyException("error")).when(propertyServiceMock).deleteSiteIntegrations(isA(String.class), isA(String.class));
    mockMvc.perform(MockMvcRequestBuilders.delete("/properties/integration/site/ckrc88qf6000p01063tarr66f/index/1")
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isBadRequest());  
  }

  @Test
  public void deleteSiteIntegrations_Exception() throws Exception {
    doThrow(new RuntimeException()).when(propertyServiceMock).deleteSiteIntegrations(isA(String.class), isA(String.class));
    mockMvc.perform(MockMvcRequestBuilders.delete("/properties/integration/site/ckrc88qf6000p01063tarr66f/index/1")
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().is5xxServerError());  
  }
}
