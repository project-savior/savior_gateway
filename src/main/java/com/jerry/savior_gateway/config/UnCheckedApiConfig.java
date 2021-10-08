package com.jerry.savior_gateway.config;

import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 22454
 */
@Configuration
public class UnCheckedApiConfig {
    private final List<String> uncheckedApis = new ArrayList<String>() {
        {
            add("/login/*");
        }
    };

    public boolean isUnchecked(String url) {
        if (uncheckedApis.contains(url)) {
            return true;
        }
        for (String uncheckedApi : uncheckedApis) {
            if (uncheckedApi.endsWith("*")) {
                String prefix = uncheckedApi.substring(0, uncheckedApi.lastIndexOf("/") + 1);
                if (url.startsWith(prefix)) {
                    return true;
                }
            }
        }
        return false;
    }

}
