package com.wentsy.utilities;

import org.apache.commons.configuration2.*;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;
public class ConfigurationReader {
        private final CompositeConfiguration compositeConfiguration;

        public CompositeConfiguration getCompositeConfiguration() {
            return this.compositeConfiguration;
        }

        public ConfigurationReader() {
            this.compositeConfiguration = new CompositeConfiguration();

            try {
                // 1) Add system properties
                getCompositeConfiguration().addConfiguration(new SystemConfiguration());

                // 2) Add global configuration properties
                Configuration globalConfiguration = loadConfigurationFile("configuration.properties");
                getCompositeConfiguration().addConfiguration(globalConfiguration);

                // 3) Add os specific properties
                Configuration environmentConfiguration = loadConfigurationFile("src/test/resources/env/" + getTargetEnvironment() + ".properties");
                getCompositeConfiguration().addConfiguration(environmentConfiguration);
            } catch (ConfigurationException e) {
                System.out.println("Unable to load project configuration files");
            }
        }

        private String getTargetEnvironment() {
            return getCompositeConfiguration().getString("env");
        }

        private Configuration loadConfigurationFile(String name) throws ConfigurationException {
            FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                    new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                            .configure(new Parameters().fileBased().setFile(new File(name)));
            return builder.getConfiguration();
        }

        public String getProperty(String keyName) {
            return getCompositeConfiguration().getString(keyName);
        }
}
