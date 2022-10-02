package com.naturecode.thrive.properties.util;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.naturecode.thrive.properties.model.Property;
import com.naturecode.thrive.properties.model.SiteIntegration;

public class PropertyUtil {
  public static Property translate(SiteIntegration siteInt, String id, List<Property> currentProps) {
    Property prop = new Property();
    if (currentProps == null || currentProps.isEmpty()) {
      prop.setId(id);
      prop.setKey("integrations.sites." + prop.getId() + "[0].host");
      prop.setValue(siteInt.getHost());
      prop.setEnabled(siteInt.isEnabled());
      prop.setPort(siteInt.getPort());
    } else {
      Optional<Property> latestProp = currentProps
      .stream()
      .min(Comparator.comparing(Property::getIndex));

      prop.setId(latestProp.get().getId());
      int index = latestProp.get().getIndex() + 1;
      prop.setKey("integrations.sites." + prop.getId() + "[" + index + "]" + ".host");
      prop.setValue(siteInt.getHost());
      prop.setEnabled(siteInt.isEnabled());
      prop.setPort(siteInt.getPort());
    }
    return prop;
  }

  public static String containIndex(List<Property> currentProps, String index) {
    List<Property> result = currentProps
      .stream()
      .filter(p -> p.getIndex() == Integer.parseInt(index))
      .collect(Collectors.toList());

    return result.isEmpty() ? null : result.get(0).getKey();
  }
}
