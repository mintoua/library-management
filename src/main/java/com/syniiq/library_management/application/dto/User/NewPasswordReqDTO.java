package com.syniiq.library_management.application.dto.User;

public record NewPasswordReqDTO(String email, String code, String newPassword, String newPaswordConfirm ) {
}
