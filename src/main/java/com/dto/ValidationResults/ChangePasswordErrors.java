package com.dto.ValidationResults;

public class ChangePasswordErrors implements IErrors {
    private boolean oldPassEmpty;
    private boolean oldPassIncorrect;
    private boolean newPassEmpty;
    private boolean newPassInvalid;

    public static ChangePasswordErrors builder(){
        return new ChangePasswordErrors();
    }

    public ChangePasswordErrors setOldPassEmpty(boolean oldPassEmpty) {
        this.oldPassEmpty = oldPassEmpty;
        return this;
    }

    public ChangePasswordErrors setOldPassIncorrect(boolean oldPassIncorrect) {
        this.oldPassIncorrect = oldPassIncorrect;
        return this;
    }

    public ChangePasswordErrors setNewPassEmpty(boolean newPassEmpty) {
        this.newPassEmpty = newPassEmpty;
        return this;
    }

    public ChangePasswordErrors setNewPassInvalid(boolean newPassInvalid) {
        this.newPassInvalid = newPassInvalid;
        return this;
    }

    public boolean isOldPassEmpty() {
        return oldPassEmpty;
    }

    public boolean isOldPassIncorrect() {
        return oldPassIncorrect;
    }

    public boolean isNewPassEmpty() {
        return newPassEmpty;
    }

    public boolean isNewPassInvalid() {
        return newPassInvalid;
    }

    @Override
    public boolean hasErrors() {
        return oldPassEmpty ||
                oldPassIncorrect ||
                newPassEmpty ||
                newPassInvalid;
    }
}
