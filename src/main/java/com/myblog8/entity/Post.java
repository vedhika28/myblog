package com.myblog8.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.*;




@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "posts", uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})})
public class Post {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;


    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "content", nullable = false)
    private String content;
    //cascade for eg if you delete any video automatically assasciated comments get deleted which is done by cascade.

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)//cascade only in parent i.e post,where as comment is child.
    private List<Comment> comments;
}
