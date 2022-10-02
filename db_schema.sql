DROP TABLE IF EXISTS properties;
  
CREATE TABLE properties (
  uid SERIAL PRIMARY KEY,
  id VARCHAR(50) NOT NULL,
  key VARCHAR(200) NOT NULL,
  value VARCHAR(100) NOT NULL,
  port numeric NOT NULL,
  enabled boolean NOT NULL
);