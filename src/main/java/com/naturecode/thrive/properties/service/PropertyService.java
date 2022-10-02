package com.naturecode.thrive.properties.service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.hql.internal.QueryExecutionRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.naturecode.thrive.properties.exception.PropertyException;
import com.naturecode.thrive.properties.model.Property;
import com.naturecode.thrive.properties.model.SiteIntegration;
import com.naturecode.thrive.properties.repository.PropertyRepo;
import com.naturecode.thrive.properties.util.PropertyUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class PropertyService {
  private final PropertyRepo propertyRepo;

  @Autowired
  public PropertyService(PropertyRepo propertyRepo) {
    this.propertyRepo = propertyRepo;
  }

  public void createSiteIntegration(SiteIntegration siteInt, String id) throws PropertyException {
    try {
      if(!StringUtils.isAlphanumeric(id) || id.length() < 25) {
        throw new PropertyException("Property ID must be 25 alphanumeric");
      }
      List<Property> currentProps = propertyRepo.findByPropId(id);
      Property prop = PropertyUtil.translate(siteInt, id, currentProps);
      propertyRepo.save(prop);
    } catch (IllegalArgumentException e) {
      log.error("Exception in createSiteIntegration(): {}", e.getMessage());
      throw new PropertyException(e.getMessage());
    }
  }

  public List<SiteIntegration> listSiteIntegrations(String id) throws PropertyException {
    try {
      Function<Property, SiteIntegration> verboseLambda = (Property x)-> { 
        SiteIntegration siteInt = new SiteIntegration();
        siteInt.setId(x.getId());
        siteInt.setHost(x.getKey().substring(x.getKey().indexOf("sites.") + 1, x.getKey().indexOf("[")));
        siteInt.setPort(x.getPort());
        siteInt.setEnabled(x.isEnabled());
        return siteInt;
      };

      List<Property> props = propertyRepo.findByPropId(id);
      List<SiteIntegration> siteInts = props.stream()
        .map(p -> verboseLambda.apply(p))
        .collect(Collectors.toList());
      return siteInts;
    } catch (Exception e) {
      log.error("Exception in listSiteIntegrations(): {}", e.getMessage());
      throw new PropertyException(e.getMessage());
    }
  }

  public void deleteSiteIntegrations(String id, String index) throws PropertyException {
    try {
      List<Property> props = propertyRepo.findByPropId(id);
      if (props == null || props.isEmpty()) {
        throw new PropertyException("Property ID is invalid");
      }
      String propKey = PropertyUtil.containIndex(props, index);
      if (propKey == null) {
        throw new PropertyException("Site Integration index is invalid");
      } else {
        propertyRepo.deleteByIdAndIndex(id, propKey);
      }
    } catch (QueryExecutionRequestException e) {
      log.error("Exception in deleteSiteIntegrations(): {}", e.getMessage());
      throw new PropertyException(e.getMessage());
    }
  }
}
