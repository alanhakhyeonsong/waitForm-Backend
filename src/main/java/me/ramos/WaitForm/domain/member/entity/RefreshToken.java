package me.ramos.WaitForm.domain.member.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor
@Table(name = "refresh_token")
@Entity
public class RefreshToken {

    @Id
    private String master;
    private String value;

    public RefreshToken updateValue(String token) {
        this.value = token;
        return this;
    }

    @Builder
    public RefreshToken(String master, String value) {
        this.master = master;
        this.value = value;
    }
}
