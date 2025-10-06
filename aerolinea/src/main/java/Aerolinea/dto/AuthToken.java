package Aerolinea.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@AllArgsConstructor
public class AuthToken {
    private String token;
    private String type;
    private Long expiresIn;

    public AuthToken(String token) {
        this.token = token;
        this.type = "Bearer";
        this.expiresIn = 3600L;
    }
}
