package io.zhijian.base.entity;

import javax.persistence.*;

@MappedSuperclass
public class PK {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    public PK() {

    }

    public PK(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
