package com.image.gallery.search.service;

import com.image.gallery.search.model.auth.Token;

public interface AuthorizationService {
    Token authorise();

    String getToken();
}
