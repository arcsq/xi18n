package com.arcsq.poc.springcloud.xi18n.controller;

import com.arcsq.poc.springcloud.xi18n.model.LanguagePack;
import com.arcsq.poc.springcloud.xi18n.service.LanguageResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LanguageController {

    @Autowired
    private LanguageResourceService languageResourceService;

    @GetMapping(path = "/language-pack/{resource}/{lang}", produces = MediaType.APPLICATION_JSON_VALUE)
    public LanguagePack getLanguagePack(@PathVariable("resource") final String resource,
                                        @PathVariable("lang") final String lang) throws Exception {
        return languageResourceService.getResourceBundle(resource, lang);
    }

    @GetMapping(path = "/refresh-language-pack/{resource}/{lang}")
    public String evictLanguagePackCache(@PathVariable("resource") final String resource,
                                         @PathVariable("lang") final String lang) {
        languageResourceService.clearLanguagePackCache(resource, lang);
        return "Cache cleared for App: " + resource;
    }

}
