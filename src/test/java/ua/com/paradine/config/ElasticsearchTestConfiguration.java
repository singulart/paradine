package ua.com.paradine.config;

import org.assertj.core.util.Files;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchProperties;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class ElasticsearchTestConfiguration {
    @Autowired
    public void elasticsearchProperties(ElasticsearchProperties elasticsearchProperties) {
        File tempdir = Files.newTemporaryFolder();
        elasticsearchProperties.getProperties().put("path.home", tempdir.getAbsolutePath());
    }
}
