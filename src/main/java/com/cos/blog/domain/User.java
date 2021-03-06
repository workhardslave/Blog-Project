package com.cos.blog.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;


//@DynamicInsert // insert할 때 null인 필드 제외
@NoArgsConstructor
@Getter
// ORM : JAVA(다른언어) Object -> DB 테이블로 매핑해주는 기술
@Entity // User 클래스가 MySQL에 테이블이 생성된다.
public class User {

    @Id // PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 DB의 넘버링 전략을 따라감
    private Long id; // sequence, auto_increment

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @Column(nullable = false, length = 100) // 123456 => 해쉬(패스워드 암호화)
    private String password;

    @Column(nullable = false, length = 30)
    private String username;

//    @ColumnDefault(("'user'"))
    // DB는 RoleType 타입이 없으므로 String이라는 것을 명시
    @Enumerated(EnumType.STRING)
    private RoleType role; // USER, ADMIN

    private String provider;

    private String providerId;

    @Builder
    public User(String email, String password, String username, RoleType role, String provider, String providerId) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
    }

    public void updateUser(String email, String password) {
        this.email=email;
        this.password=password;
    }

    public String getRoleValue() {
        return this.role.getValue();
    }

}
