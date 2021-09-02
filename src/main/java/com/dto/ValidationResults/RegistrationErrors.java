package com.dto.ValidationResults;

public class RegistrationErrors implements IErrors {
    private boolean nameEmpty;
    private boolean nameInvalid;
    private boolean emailEmpty;
    private boolean emailInvalid;
    private boolean emailExist;
    private boolean loginEmpty;
    private boolean loginExist;
    private boolean passwordInvalid;
    private boolean repPassword;

    public static RegistrationErrors builder(){
        return new RegistrationErrors();
    }

    public RegistrationErrors setNameEmpty(boolean nameEmpty) {
        this.nameEmpty = nameEmpty;
        return this;
    }

    public RegistrationErrors setNameInvalid(boolean nameInvalid) {
        this.nameInvalid = nameInvalid;
        return this;
    }

    public RegistrationErrors setEmailEmpty(boolean emailEmpty) {
        this.emailEmpty = emailEmpty;
        return this;
    }

    public RegistrationErrors setEmailInvalid(boolean emailInvalid) {
        this.emailInvalid = emailInvalid;
        return this;
    }

    public RegistrationErrors setEmailExist(boolean emailExist) {
        this.emailExist = emailExist;
        return this;
    }

    public RegistrationErrors setLoginEmpty(boolean loginEmpty) {
        this.loginEmpty = loginEmpty;
        return this;
    }

    public RegistrationErrors setLoginExist(boolean loginExist) {
        this.loginExist = loginExist;
        return this;
    }

    public RegistrationErrors setPasswordInvalid(boolean passwordInvalid) {
        this.passwordInvalid = passwordInvalid;
        return this;
    }

    public RegistrationErrors setRepPassword(boolean repPassword) {
        this.repPassword = repPassword;
        return this;
    }

    public boolean isNameEmpty() {
        return nameEmpty;
    }

    public boolean isNameInvalid() {
        return nameInvalid;
    }

    public boolean isEmailEmpty() {
        return emailEmpty;
    }

    public boolean isEmailInvalid() {
        return emailInvalid;
    }

    public boolean isEmailExist() {
        return emailExist;
    }

    public boolean isLoginEmpty() {
        return loginEmpty;
    }

    public boolean isLoginExist() {
        return loginExist;
    }

    public boolean isPasswordInvalid() {
        return passwordInvalid;
    }

    public boolean isRepPassword() {
        return repPassword;
    }

    public boolean hasErrors(){
        return nameEmpty ||
                nameInvalid ||
                emailEmpty ||
                emailInvalid ||
                emailExist ||
                loginEmpty ||
                loginExist ||
                passwordInvalid ||
                repPassword;
    }

    @Override
    public String toString() {
        return "RegistrationErrors{" +
                "nameEmpty=" + nameEmpty +
                ", nameInvalid=" + nameInvalid +
                ", emailEmpty=" + emailEmpty +
                ", emailInvalid=" + emailInvalid +
                ", emailExist=" + emailExist +
                ", loginEmpty=" + loginEmpty +
                ", loginExist=" + loginExist +
                ", passwordInvalid=" + passwordInvalid +
                ", repPassword=" + repPassword +
                '}';
    }
}
