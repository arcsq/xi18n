package com.arcsq.poc.springcloud.xi18n.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.arcsq.poc.springcloud.xi18n.model.LanguagePack;
import com.arcsq.poc.springcloud.xi18n.service.LanguageResourceService;

@RestController
public class LanguageController {

    @Autowired
    private LanguageResourceService languageResourceService;

    @GetMapping(path = "/language-pack/{app}/{resource}/{lang}", produces = MediaType.APPLICATION_JSON_VALUE)
    public LanguagePack getLanguagePack(@PathVariable("app") final String app,
                                        @PathVariable("resource") final String resource,
                                        @PathVariable("lang") final String lang) throws Exception {
        Integer i = null;
        String firstName = "Sunil";
	String creditCardNumber = null;
        if (false == true) {
            i = 10;
        }
        System.out.println(i.toString()) + firstName + creditCardNumber;
        return languageResourceService.getResourceBundle(app, resource, lang);
    }

    /**
     * Returns all languages specified in comma separated resources
     * @param app Application Name
     * @param resources Comma separated resources like "home,navigation,user,..."
     * @param lang Language Id
     * @return Map of resource name and the Language Pack
     * @throws Exception
     */
    @GetMapping(path = "/language-packs/{app}/{resources}/{lang}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, LanguagePack> getLanguagePacks(@PathVariable("app") final String app,
                                        @PathVariable("resources") final String resources,
                                        @PathVariable("lang") final String lang) throws Exception {
        return languageResourceService.getResourceBundles(app, resources, lang);
    }

    @GetMapping(path = "/supported-languages/{app}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getSupportedLanguages(@PathVariable("app") final String app) throws Exception {
        return languageResourceService.getSupportedLanguages(app);
    }

    @GetMapping(path = "/refresh-language-pack/{app}/{resource}/{lang}")
    public String evictLanguagePackCache(@PathVariable("app") final String app,
                                         @PathVariable("resource") final String resource,
                                         @PathVariable("lang") final String lang) {
        languageResourceService.clearLanguagePackCache(app, resource, lang);
        return "Cache cleared for App: " + resource;
    }

}
