package com.arcsq.poc.springcloud.xi18n.model;

import lombok.Data;

import java.util.Map;

@Data
public class LanguagePack {

    private String language;
    private String resource;
    private String realm;
    private Map<String, String> resources;

}
