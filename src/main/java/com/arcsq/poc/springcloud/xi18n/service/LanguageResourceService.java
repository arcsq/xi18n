package com.arcsq.poc.springcloud.xi18n.service;

import com.arcsq.poc.springcloud.xi18n.helper.ProfileMapping;
import com.arcsq.poc.springcloud.xi18n.helper.ResourceBundleConfigClient;
import com.arcsq.poc.springcloud.xi18n.model.LanguagePack;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeMap;

@Service
public class LanguageResourceService {

    @Value("${spring.profiles.active:local}")
    private String profile;

    @Autowired
    private ResourceBundleConfigClient resourceBundleConfigClient;

    public LanguagePack getResourceBundle(final String appName, final String langId) {
        final ProfileMapping mapping = ProfileMapping.findByProfile(profile);
        final String bundle = resourceBundleConfigClient.getLanguagePack(mapping.getBranch(), appName, langId);
        return parseBundle(bundle);
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
