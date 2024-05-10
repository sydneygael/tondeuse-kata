package org.application.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;

@ConfigurationProperties("batch")
public record BatchProperties(Resource inputFile, Resource resultFile) {
}
