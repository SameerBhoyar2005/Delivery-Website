package com.DelhiveryApplication.DelhiveryApplication.Data;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DelhiveryAgent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;

    @Column(unique = true,nullable = false)
    private String email;

    @Column(unique = true)
    private String mobile;

    private String password;

    private String vehicleNo;

    @CreationTimestamp
    private LocalDateTime createdAt=LocalDateTime.now();

    private Boolean verified = false;

    private String address;

    @OneToMany(mappedBy = "agent")
    private List<Orders> allOrders = new ArrayList<>();


}
