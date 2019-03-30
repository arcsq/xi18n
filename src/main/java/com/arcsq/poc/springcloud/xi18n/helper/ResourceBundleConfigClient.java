package com.arcsq.poc.springcloud.xi18n.helper;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "lang", url = "http://localhost:8888")
public interface ResourceBundleConfigClient {

    @GetMapping(path = "/config/{realm}/{resource}-{language}.properties")
    String getLanguagePack(@PathVariable("realm") String realm, @PathVariable("resource") String resource,
                           @PathVariable("language") String langId);

}
