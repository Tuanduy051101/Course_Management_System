package com.company.projects.course.coursemanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.Collection;

@Entity
@Table(name = "tbl_permission")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
public class PermissionEntity extends BaseEntity {
    @Column(nullable = false, unique = true)
    String name;

    @ManyToMany(mappedBy = "permissions")
    @JsonManagedReference
    Collection<RoleEntity> roles;
}
