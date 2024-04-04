package com.wenxt.base.security;

import org.jasypt.util.password.PasswordEncryptor;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;

//public class JasyptPasswordEncoder implements PasswordEncoder {
////
////    private final PasswordEncryptor passwordEncryptor;
////
////    public JasyptPasswordEncoder() {
////        this.passwordEncryptor = new StrongPasswordEncryptor();
////    }
////
////    @Override
////    public String encode(CharSequence rawPassword) {
////        return passwordEncryptor.encryptPassword(rawPassword.toString());
////    }
////
////    @Override
////    public boolean matches(CharSequence rawPassword, String encodedPassword) {
////        return passwordEncryptor.checkPassword(rawPassword.toString(), encodedPassword);
////    }
//}
