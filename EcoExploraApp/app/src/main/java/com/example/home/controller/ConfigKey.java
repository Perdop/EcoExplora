package com.example.home.controller;

import com.example.home.BuildConfig;

public class ConfigKey {
    public static String getApiKey() {
        return BuildConfig.API_KEY;
    }
}

