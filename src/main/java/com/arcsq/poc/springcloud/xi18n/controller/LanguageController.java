package com.arcsq.poc.springcloud.xi18n.controller;

import com.arcsq.poc.springcloud.xi18n.model.LanguagePack;
import com.arcsq.poc.springcloud.xi18n.service.LanguageResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LanguageController {

    @Autowired
    private LanguageResourceService languageResourceService;

    @GetMapping(path = "/language-pack/{app}/{lang}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Cacheable("languagepacks")
    public LanguagePack getLanguagePack(@PathVariable("app") final String app,
                                        @PathVariable("lang") final String lang) throws Exception {
        return languageResourceService.getResourceBundle(app, lang);
    }

    @GetMapping(path = "/refresh-language-pack/{app}/{lang}")
    @CacheEvict("languagepacks")
    public String evictLanguagePackCache(@PathVariable("app") final String app,
                                       @PathVariable("lang") final String lang) {
        return "Cache cleared for App: " + app + " and Lang id: " + lang;
    }

}
