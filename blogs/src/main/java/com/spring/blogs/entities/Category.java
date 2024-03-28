package com.spring.blogs.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue( strategy= GenerationType.IDENTITY)
    private int cId;
    @Column(length = 100,nullable = false)
    private String title;
    @Column(length = 100,nullable = false)
    private String description;

    //cascadeType.all means when we delete from category all the posts related to that category should be delated
    @OneToMany( mappedBy = "category",cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

}
