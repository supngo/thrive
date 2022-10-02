package com.naturecode.thrive.properties.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.hql.internal.QueryExecutionRequestException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.naturecode.thrive.properties.exception.PropertyException;
import com.naturecode.thrive.properties.model.Property;
import com.naturecode.thrive.properties.model.SiteIntegration;
import com.naturecode.thrive.properties.repository.PropertyRepo;
import com.naturecode.thrive.properties.util.PropertyUtil;

@SpringBootTest
public class PropertyServiceTest {
  @Mock
  private PropertyRepo propertyRepo;

  @InjectMocks
  PropertyService propertyService;

  @Test
  public void createIP_Success() throws PropertyException {
    PropertyUtil propertyUtil = new PropertyUtil();
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
  public void createIP_id_not_alphanumeric_400error() {
    PropertyException e = assertThrows(PropertyException.class, () -> propertyService.createSiteIntegration(new SiteIntegration("ckrc88qf6000p01063tarr66f", "api.com", 104, true), "ckrc88qf6000p01063tarr66!"));
    assertTrue(e.getMessage().contains("Property ID must be 25 alphanumeric"));
  }

  @Test
  public void createIP_id_less_than25_400error() {
    PropertyException e = assertThrows(PropertyException.class, () -> propertyService.createSiteIntegration(new SiteIntegration("ckrc88qf6000p01063tarr66f", "api.com", 104, true), "ckrc88qf6000p01063tarr6"));
    assertTrue(e.getMessage().contains("Property ID must be 25 alphanumeric"));
  }

  @Test
  public void createIP_500error() {
    Mockito.when(propertyRepo.findByPropId(isA(String.class))).thenThrow(new IllegalArgumentException("Exception"));
    PropertyException e = assertThrows(PropertyException.class, () -> propertyService.createSiteIntegration(new SiteIntegration("ckrc88qf6000p01063tarr66f", "api.com", 104, true), "ckrc88qf6000p01063tarr66f"));
    assertTrue(e.getMessage().contains("Exception"));
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

  @Test
  public void listSiteIntegrations_Exception() {
    Mockito.when(propertyRepo.findByPropId(isA(String.class))).thenThrow(new RuntimeException("Exception"));
    PropertyException e = assertThrows(PropertyException.class, () -> propertyService.listSiteIntegrations("ckrc88qf6000p01063tarr66f"));
    assertTrue(e.getMessage().contains("Exception"));
  }

  @Test
  public void deleteSiteIntegrations_Success() throws PropertyException {
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
    doNothing().when(propertyRepo).deleteByIdAndIndex(isA(String.class), isA(String.class));
    propertyService.deleteSiteIntegrations("ckrc88qf6000p01063tarr66f", "0");
    verify(propertyRepo, times(1)).findByPropId(isA(String.class));
    verify(propertyRepo, times(1)).deleteByIdAndIndex(isA(String.class), isA(String.class));
  }

  @Test
  public void deleteSiteIntegrations_empty_400error() {
    Mockito.when(propertyRepo.findByPropId(isA(String.class))).thenReturn(new ArrayList<>());
    PropertyException e = assertThrows(PropertyException.class, () -> propertyService.deleteSiteIntegrations("ckrc88qf6000p01063tarr66f", "1"));
    assertTrue(e.getMessage().contains("Property ID is invalid"));
  }

  @Test
  public void deleteSiteIntegrations_Index_Invalid_400error() {
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
    doNothing().when(propertyRepo).deleteByIdAndIndex(isA(String.class), isA(String.class));
    PropertyException e = assertThrows(PropertyException.class, () -> propertyService.deleteSiteIntegrations("ckrc88qf6000p01063tarr66f", "1"));
    assertTrue(e.getMessage().contains("Site Integration index is invalid"));
  }

  @Test
  public void deleteSiteIntegrations_500error() {
    Mockito.when(propertyRepo.findByPropId(isA(String.class))).thenThrow(new QueryExecutionRequestException("error", "exception"));
    PropertyException e = assertThrows(PropertyException.class, () -> propertyService.deleteSiteIntegrations("ckrc88qf6000p01063tarr66f", "1"));
    assertTrue(e.getMessage().contains("error"));
  }
}
