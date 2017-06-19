package com.mycompany.service.impl;

import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static java.util.Collections.unmodifiableSet;

public enum CountryDictionary {
    SWITZERLAND("Switzerland", new String[]{"070"}/*Add more country specific information*/),
    UKRAINE("Ukraine", new String[]{"067", "093", "050"});

    private final String countryName;
    private final Set<String> mobilePhoneCodes;

    private CountryDictionary(String countryName, String[] mobilePhoneCodes) {
        this.countryName = countryName;
        this.mobilePhoneCodes = new HashSet<>(asList(mobilePhoneCodes));
    }

    public static boolean isMobilePhone(String number) {

        boolean result = stream(values())
                .flatMap(country -> country.mobilePhoneCodes.stream())
                .filter(number::startsWith)
                .findFirst()
                .isPresent();

        return result;
    }

    public String getCountryName() {
        return countryName;
    }

    public Set<String> getMobilePhoneCodes() {
        return unmodifiableSet(mobilePhoneCodes);
    }
}


