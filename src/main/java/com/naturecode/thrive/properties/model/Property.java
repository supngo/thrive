package com.naturecode.thrive.properties.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "properties")
@Getter
@Setter
@NoArgsConstructor
public class Property {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Long uid;

  private String id;
  private String key;
  private String value;
  private int port;
  private boolean enabled;

  public int getIndex() {
    return Integer.parseInt(key.substring(key.indexOf("[") + 1, key.indexOf("]")));
  }
}
