package com.arcsq.poc.springcloud.xi18n.controller;

import com.arcsq.poc.springcloud.xi18n.model.LanguagePack;
import com.arcsq.poc.springcloud.xi18n.service.LanguageResourceService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public LanguagePack getLanguagePack(@PathVariable("app") final String app,
                                        @PathVariable("lang") final String lang) {
        return languageResourceService.getResourceBundle(app, lang);
    }

}
