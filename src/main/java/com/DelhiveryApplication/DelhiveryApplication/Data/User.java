package com.DelhiveryApplication.DelhiveryApplication.Data;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "user_table")
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String name;

    @Column(unique = true,nullable = false)
    private String email;

    @Column(unique = true)
    private String mobile;

    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private Boolean verified = false;

    @OneToMany(mappedBy = "sender")
    private List<Orders> sentOrders = new ArrayList<>();

    @OneToMany(mappedBy = "receiver")
    private List<Orders> receivedOrders = new ArrayList<>();

}
