package com.KSDT.models.enums;

public enum SizeType {
    LARGE,
    MEDIUM,
    SMALL;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.name().charAt(0));
        for (int i = 1; i < this.name().length(); i++) {
            if (name().charAt(i) == '_') {
                sb.append(' ');
            }
            else {
                sb.append(name().toLowerCase().charAt(i));
            }
        }
        return sb.toString();
    }

}
