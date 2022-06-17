package io.bhimsur.cf14seabe.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user_profile")
public class UserProfile implements Serializable {

    private static final long serialVersionUID = 4779913844546999333L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "name_alias")
    private String nameAlias;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Column(name = "create_date")
    private Timestamp createDate;

}
