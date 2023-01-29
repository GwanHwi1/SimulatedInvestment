package com.investment.simulatedInvestment.common;

public enum Role {
    USER("ROLE_USER"),ADMIN("ROLE_ADMIN");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return role;
    }
}
