package com.spring.jdbc;

public class TransactionDefinition {

    public static final int PROPAGATION_REQUIRES_NEW = 1;
    public static final int PROPAGATION_NESTED = 2;
    public static final int PROPAGATION_NOT_SUPPORTED = 3;
    public static final int PROPAGATION_NEVER = 4;

    private int propagationBehavior;

    public TransactionDefinition(int propagationBehavior) {
        this.propagationBehavior = propagationBehavior;
    }

    public int getPropagationBehavior() {
        return propagationBehavior;
    }

    public boolean isNested() {
        return propagationBehavior == PROPAGATION_NESTED;
    }
}
