package com.szs.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.List;

@Data
@Schema(description = "회원 DTO")
public class UserDto {

        @Schema(hidden = true) // id 필드 숨김
        private Long id;

        @NotBlank
        @Schema(description = "회원ID", example = "userId", required = true)
        private String userId;

        @NotBlank
        @Schema(description = "비밀번호", example = "password", required = true)
        private String password; // 암호화된 비밀번호를 저장

        @NotBlank
        @Schema(description = "이름", example = "홍길동", required = true)
        private String name;

        @NotBlank
        @Pattern(regexp = "\\d{6}-\\d{7}", message = "주민등록번호는 6자리-7자리 형식이어야 합니다.")
        @Schema(description = "주민번호", example = "860824-1655068", required = true)
        private String regNo; // 암호화된 주민등록번호를 저장

}

