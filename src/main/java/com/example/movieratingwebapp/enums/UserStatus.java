package com.example.movieratingwebapp.enums;

public enum UserStatus {
    NEWCOMER,
    ROOKIE,
    EXPERIENCED,
    EXPERT,
    MASTER,
    BANNED;

    public static UserStatus getStatusByUserRating(int rating) {
        if (rating < 10) {
            return NEWCOMER;
        }
        if (rating < 20) {
            return ROOKIE;
        }
        if (rating < 30) {
            return EXPERIENCED;
        }
        if (rating < 40) {
            return EXPERT;
        }
        return MASTER;
    }
}
