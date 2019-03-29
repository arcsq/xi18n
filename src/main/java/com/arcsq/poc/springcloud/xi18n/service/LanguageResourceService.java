package com.arcsq.poc.springcloud.xi18n.service;

import com.arcsq.poc.springcloud.xi18n.helper.ProfileMapping;
import com.arcsq.poc.springcloud.xi18n.helper.ResourceBundleConfigClient;
import com.arcsq.poc.springcloud.xi18n.model.LanguagePack;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;
import java.util.TreeMap;

@Service
public class LanguageResourceService {

    private static final String CACHE_NAME = "languagepacks";

    @Value("${spring.profiles.active:local}")
    private String profile;

    @Autowired
    private ResourceBundleConfigClient resourceBundleConfigClient;

    @Cacheable(value = CACHE_NAME)
    public LanguagePack getResourceBundle(final String appName, final String langId) {
        System.out.println("Reading pack...");
        final ProfileMapping mapping = ProfileMapping.findByProfile(profile);
        final String bundle = resourceBundleConfigClient.getLanguagePack(mapping.getBranch(), appName, langId);
        return parseBundle(bundle);
    }

    @CacheEvict(value = CACHE_NAME)
    public void clearLanguagePackCache(final String app, final String langId) {
    }


    private LanguagePack parseBundle(final String bundle) {
        final String[] properties = StringUtils.split(bundle, "\n");
        final Map<String, String> map = new TreeMap<>();
        LanguagePack pack = new LanguagePack();
        for (final String property : properties) {
            final String[] resource = property.split(":", 2);
            if ("resource.version".equalsIgnoreCase(resource[0])) {
                pack.setVersion(resource.length == 2 ? resource[1] : "Unknown");
            }
            else if ("resource.language".equalsIgnoreCase(resource[0])) {
                pack.setLanguage(resource.length == 2 ? resource[1] : "Unknown");
            }
            else if ("resource.release-date".equalsIgnoreCase(resource[0])) {
                pack.setReleaseDate(resource.length == 2 ? resource[1] : "Unknown");
            }
            else if ("resource.realm".equalsIgnoreCase(resource[0])) {
                pack.setRealm(resource.length == 2 ? resource[1] : "Unknown");
            }
            else {
                map.put(StringUtils.trim(resource[0]), resource.length == 2 ? StringUtils.trim(resource[1]) : "");
            }
        }
        pack.setResources(map);
        return pack;
    }

}

//config-client-dev.properties
