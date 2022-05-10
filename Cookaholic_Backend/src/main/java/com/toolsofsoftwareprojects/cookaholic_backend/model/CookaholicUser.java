package com.toolsofsoftwareprojects.cookaholic_backend.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Builder
@Entity
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CookaholicUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column
    private String name;

    @Column
    @NotNull
    private String username;

    @Column
    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String password;

    @Column
    private String email;

    @Column
    private UserTitle userTitle;

    @Column
    private String phone;

    @Column
    private String imageUrl;

    @Column(nullable = false, updatable = false)
    private String userCode;

    public enum UserTitle {
        ROLE_GUEST, ROLE_USER, ROLE_ADMIN
    }

    @Override
    public String toString() {
        return "CookaholicUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", userTitle='" + userTitle + '\'' +
                ", phone='" + phone + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", userCode='" + userCode + '\'' +
                '}';
    }
}
