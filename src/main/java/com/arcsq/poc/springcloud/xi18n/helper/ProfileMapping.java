package com.arcsq.poc.springcloud.xi18n.helper;

public enum ProfileMapping {

    LOCAL("local", "dev"),
    NATIVE("native", "dev"),
    QA("qa", "qa"),
    PROD("prod", "master"),
    OTHERS("", "dev");

    private final String profileName;
    private final String branch;

    ProfileMapping(final String profileName, final String branch) {
        this.profileName = profileName;
        this.branch = branch;
    }

    public static ProfileMapping findByProfile(final String profileName) {
        for (final ProfileMapping pm : ProfileMapping.values()) {
            if (pm.profileName.equals(profileName)) {
                return pm;
            }
        }
        return OTHERS;
    }

    public String getBranch() {
        return branch;
    }
}
