package com.arcsq.poc.springcloud.xi18n.service;

import com.arcsq.poc.springcloud.xi18n.helper.ProfileMapping;
import com.arcsq.poc.springcloud.xi18n.helper.ResourceBundleConfigClient;
import com.arcsq.poc.springcloud.xi18n.model.LanguagePack;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeMap;

@Service
@Slf4j
public class LanguageResourceService {
    private static final String CACHE_NAME = "languagepacks";

    @Value("${spring.profiles.active:local}")
    private String profile;

    @Autowired
    private ResourceBundleConfigClient resourceBundleConfigClient;

    private static Map<String, String> parseBundle(final String bundle) {
        final String[] properties = StringUtils.split(bundle, "\n");
        final Map<String, String> map = new TreeMap<>();
        for (final String property : properties) {
            final String[] resource = property.split(":", 2);
            map.put(StringUtils.trim(resource[0]), resource.length == 2 ? StringUtils.trim(resource[1]) : "");
        }
        return map;
    }

    @Cacheable(value = CACHE_NAME)
    public LanguagePack getResourceBundle(final String resource, final String langId) {
        log.info("Loading parsed bundle...");
        final ProfileMapping mapping = ProfileMapping.findByProfile(profile);
        final String bundle = resourceBundleConfigClient.getLanguagePack(mapping.getBranch(), resource, langId);
        final LanguagePack pack = new LanguagePack();
        pack.setLanguage(langId);
        pack.setRealm(profile);
        pack.setResource(resource);
        pack.setResources(parseBundle(bundle));
        return pack;
    }

    @CacheEvict(value = CACHE_NAME)
    public void clearLanguagePackCache(final String resource, final String langId) {
    }

}

//config-client-dev.properties
