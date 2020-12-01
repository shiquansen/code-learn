package com.sbzl.framework.admin.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MyPasswordEncoder implements PasswordEncoder{
    @Value("${myPasswordEncoder.security.encode}")
    private String encode;

    @Override
    public String encode(CharSequence charSequence) {
        PasswordEncoder encoder = new Pbkdf2PasswordEncoder(encode);
        return encoder.encode(charSequence);
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        PasswordEncoder encoder = new Pbkdf2PasswordEncoder(encode);
        return encoder.matches(charSequence, s);
    }

    @Override
    public boolean upgradeEncoding(String encodedPassword) {
        PasswordEncoder encoder = new Pbkdf2PasswordEncoder(encode);
        return encoder.upgradeEncoding(encodedPassword);
    }
}
