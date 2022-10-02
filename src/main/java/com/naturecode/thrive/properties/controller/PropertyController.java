package com.naturecode.thrive.properties.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.naturecode.thrive.properties.exception.PropertyException;
import com.naturecode.thrive.properties.model.SiteIntegration;
import com.naturecode.thrive.properties.service.PropertyService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "/properties/integration/site")
public class PropertyController {
  private final PropertyService propertyService;

  @Autowired
  public PropertyController(PropertyService propertyService) {
    this.propertyService = propertyService;
  }

  @CrossOrigin
  @Operation(summary = "Create Site Integration", description = "Create a new site integration for a property")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Created"),
    @ApiResponse(responseCode = "400", description = "Bad Request"),
    @ApiResponse(responseCode = "500", description = "Internal Server Error"),
  })
  @PostMapping("/{id}")
  public ResponseEntity<?> createSiteIntegration(@RequestBody SiteIntegration prop, @PathVariable(name = "id") String id) {
    try {
      propertyService.createSiteIntegration(prop, id);
      log.info("Site Integration {} has been created", prop.getHost());
      return new ResponseEntity<>(HttpStatus.CREATED);
    } catch (PropertyException e) {
      log.error("PropertyException in createSiteIntegration(): {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
    catch (Exception e) {
      log.error("Exception in createSiteIntegration(): {}", e);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @CrossOrigin
  @Operation(summary = "List Site Integrations", description = "List all the site integration for a property")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Success"),
    @ApiResponse(responseCode = "400", description = "Bad Request"),
    @ApiResponse(responseCode = "500", description = "Internal Server Error"),
  })
  @GetMapping("/{id}")
  public ResponseEntity<?> listSiteIntegrations(@PathVariable(name = "id") String id) {
    try {
      List<SiteIntegration> list = propertyService.listSiteIntegrations(id);
      log.info("get all site integrations of {}", id);
      return ResponseEntity.ok(list);
    } catch (PropertyException e) {
      log.error("PropertyException in listSiteIntegrations(): {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
    catch (Exception e) {
      log.error("Exception in listSiteIntegrations(): {}", e);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @CrossOrigin
  @Operation(summary = "Delete Site Integration", description = "delete a site integration from a property")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Success"),
    @ApiResponse(responseCode = "400", description = "Bad Request"),
    @ApiResponse(responseCode = "500", description = "Internal Server Error"),
  })
  @DeleteMapping("/{id}/index/{index}")
  public ResponseEntity<?> deleteSiteIntegrations(@PathVariable(name = "id") String id, @PathVariable(name = "index") String index) {
    try {
      propertyService.deleteSiteIntegrations(id, index);
      log.info("get all site integrations of {}", id);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (PropertyException e) {
      log.error("PropertyException in deleteSiteIntegrations(): {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
    catch (Exception e) {
      log.error("Exception in deleteSiteIntegrations(): {}", e);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
