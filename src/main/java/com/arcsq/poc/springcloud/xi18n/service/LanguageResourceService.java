package com.arcsq.poc.springcloud.xi18n.service;

import com.arcsq.poc.springcloud.xi18n.helper.ProfileMapping;
import com.arcsq.poc.springcloud.xi18n.helper.ResourceBundleConfigClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LanguageResourceService {

    @Value("${spring.profiles.active:local}")
    private String profile;

    @Autowired
    private ResourceBundleConfigClient resourceBundleConfigClient;

    public Map<String, String> getResourceBundle(final String appName, final String langId) {
        final ProfileMapping mapping = ProfileMapping.findByProfile(profile);
        final String bundle = resourceBundleConfigClient.getLanguagePack(mapping.getBranch(), appName, langId);
        return parseBundle(bundle);
    }

    private Map<String, String> parseBundle(final String bundle) {
        final String[] properties = StringUtils.split(bundle, "\n");
        final Map<String, String> map = new HashMap<>();
        for (final String property : properties) {
            final String[] resource = property.split(":", 2);
            map.put(StringUtils.trim(resource[0]), resource.length == 2 ? StringUtils.trim(resource[1]) : "");
        }
        return map;
    }

}

//config-client-dev.properties
