package com.naturecode.thrive.properties.util;

import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PropertyUtilTest {

  @Test
  public void containIndex() {
    String result = PropertyUtil.containIndex(new ArrayList<>(), "0");
    assertNull(result);
  }
}
