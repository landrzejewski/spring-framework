package pl.training;

public record UserStatus(boolean isVisible, boolean isBusy) {

    public static UserStatus defaultStatus() {
        return new UserStatus(true, false);
    }

}
