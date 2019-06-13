package com.cloud2.disribbon.web;

import com.cloud2.commons.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class DcController {

    public static final Logger logger = LoggerFactory.getLogger(DcController.class);

    @Autowired
    LoadBalancerClient loadBalancerClient;
    @Autowired
    RestTemplate restTemplate;

    @GetMapping(Constants.CLOUD2 + "/consumer")
    public String consumer() {
        ServiceInstance serviceInstance = loadBalancerClient.choose("z_cloud2_discover_client");
        String url = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() +Constants.CLOUD2 + "/dc";
        logger.info(url);
        return restTemplate.getForObject(url, String.class);
    }

}
