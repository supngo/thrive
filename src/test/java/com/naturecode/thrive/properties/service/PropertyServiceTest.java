package com.naturecode.thrive.properties.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.naturecode.thrive.properties.exception.PropertyException;
import com.naturecode.thrive.properties.model.Property;
import com.naturecode.thrive.properties.model.SiteIntegration;
import com.naturecode.thrive.properties.repository.PropertyRepo;

@SpringBootTest
public class PropertyServiceTest {
  @Mock
  private PropertyRepo propertyRepo;

  @InjectMocks
  PropertyService propertyService;

  @Test
  public void createIP_Success() throws PropertyException {
    List<Property> mockPropertyList = new ArrayList<>();
    Property prop = new Property();
    prop.setUid(1L);
    prop.setId("ckrc88qf6000p01063tarr66f");
    prop.setKey("integrations.sites." + prop.getId() + "[0].host");
    prop.setPort(109);
    prop.setValue("api.com");
    prop.setEnabled(true);
    mockPropertyList.add(prop);

    Mockito.when(propertyRepo.findByPropId(isA(String.class))).thenReturn(mockPropertyList);
    propertyService.createSiteIntegration(new SiteIntegration("ckrc88qf6000p01063tarr66f", "api.com", 104, true), "ckrc88qf6000p01063tarr66f");
    verify(propertyRepo, times(1)).findByPropId(isA(String.class));
  }

  @Test
  public void createIP_Empty_Success() throws PropertyException {
    Mockito.when(propertyRepo.findByPropId(isA(String.class))).thenReturn(new ArrayList<>());
    propertyService.createSiteIntegration(new SiteIntegration("ckrc88qf6000p01063tarr66f", "api.com", 104, true), "ckrc88qf6000p01063tarr66f");
    verify(propertyRepo, times(1)).findByPropId(isA(String.class));
  }

  @Test
  public void createIP_Null_Success() throws PropertyException {
    Mockito.when(propertyRepo.findByPropId(isA(String.class))).thenReturn(null);
    propertyService.createSiteIntegration(new SiteIntegration("ckrc88qf6000p01063tarr66f", "api.com", 104, true), "ckrc88qf6000p01063tarr66f");
    verify(propertyRepo, times(1)).findByPropId(isA(String.class));
  }

  @Test
  public void createIP_id_not_alphanumeric_400error() throws PropertyException {
    PropertyException e = assertThrows(PropertyException.class, () -> propertyService.createSiteIntegration(new SiteIntegration("ckrc88qf6000p01063tarr66f", "api.com", 104, true), "ckrc88qf6000p01063tarr66!"));
    assertTrue(e.getMessage().contains("Property ID must be 25 alphanumeric"));
  }

  @Test
  public void createIP_id_less_than25_400error() throws PropertyException {
    PropertyException e = assertThrows(PropertyException.class, () -> propertyService.createSiteIntegration(new SiteIntegration("ckrc88qf6000p01063tarr66f", "api.com", 104, true), "ckrc88qf6000p01063tarr6"));
    assertTrue(e.getMessage().contains("Property ID must be 25 alphanumeric"));
  }

  @Test
  public void createIP_500error() throws PropertyException {
    Mockito.when(propertyRepo.findByPropId(isA(String.class))).thenThrow(new IllegalArgumentException());
    PropertyException e = assertThrows(PropertyException.class, () -> propertyService.createSiteIntegration(new SiteIntegration("ckrc88qf6000p01063tarr66f", "api.com", 104, true), "ckrc88qf6000p01063tarr66f"));
    assertTrue(e.getMessage().contains("IllegalArgumentException"));
  }

  @Test
  public void listSiteIntegrations_Success() throws PropertyException {
    List<Property> mockPropertyList = new ArrayList<>();
    Property prop = new Property();
    prop.setUid(1L);
    prop.setId("ckrc88qf6000p01063tarr66f");
    prop.setKey("integrations.sites." + prop.getId() + "[0].host");
    prop.setPort(109);
    prop.setValue("api.com");
    prop.setEnabled(true);
    mockPropertyList.add(prop);

    Mockito.when(propertyRepo.findByPropId(isA(String.class))).thenReturn(mockPropertyList);
    List<SiteIntegration> siteIntList = propertyService.listSiteIntegrations("ckrc88qf6000p01063tarr66f");
    verify(propertyRepo, times(1)).findByPropId(isA(String.class));
    assertEquals(siteIntList.get(0).getId(), "ckrc88qf6000p01063tarr66f");
  }
}
