package com.ericlam.enums;

public enum ReasonType {
    ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9);

    String title;

    ReasonType(int no) {
        this.title = "§e違反伺服器守則第 §a" + no + " §e條";
    }

    public String getTitle() {
        return title;
    }
}
