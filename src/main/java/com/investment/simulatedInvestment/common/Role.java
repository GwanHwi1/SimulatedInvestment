package com.investment.simulatedInvestment.common;

public enum Role {
    USER("user"),ADMIN("admin");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return role;
    }
}
