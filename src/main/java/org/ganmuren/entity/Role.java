package org.ganmuren.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Role {

    public enum Authority {
        DBA(1, "数据库管理员"), ADMIN(2, "系统管理员"), USER(3, "普通用户");

        private Integer id;
        private String nameZh;

        Authority(Integer id, String nameZh) {
            this.id = id;
            this.nameZh = nameZh;
        }

        public Integer getId() {
            return id;
        }

        public String getNameZh() {
            return nameZh;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    private String nameZh;
}
