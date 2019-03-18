package com.nilangpatel.blogpress.config;

import java.net.InetAddress;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.transport.TransportAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.TransportClientFactoryBean;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.nilangpatel.blogpress.repository")
@ComponentScan(basePackages = { "com.nilangpatel.blogpress.config" })
public class ElasticDataConfig {

	 	@Value("${elasticsearch.host}")
	    private String esHost;

	    @Value("${elasticsearch.port}")
	    private int esPort;

	    @Value("${elasticsearch.clustername}")
	    private String esClusterName;

	    @Bean
	    public Client client() throws Exception {

	        /*Settings esSettings = Settings.builder()
	                .put("cluster.name", esClusterName)
	                .build();*/
	        //https://www.elastic.co/guide/en/elasticsearch/guide/current/_transport_client_versus_node_client.html
	        TransportClientFactoryBean transportClientFactory = new TransportClientFactoryBean();
	        transportClientFactory.setClusterName(esClusterName);
	        transportClientFactory.afterPropertiesSet();
	        
	        return transportClientFactory.getObject()
	                  .addTransportAddress(
					  new TransportAddress(InetAddress.getByName(esHost), esPort));
	    }

	    @Bean
	    public ElasticsearchTemplate elasticsearchTemplate() throws Exception {
	        return new ElasticsearchTemplate(client());
	    }

}
