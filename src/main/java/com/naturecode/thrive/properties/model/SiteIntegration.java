package com.naturecode.thrive.properties.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SiteIntegration {
  private String id;
  private String host;
  private int port;
  private boolean enabled;
}
