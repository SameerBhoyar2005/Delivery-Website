package com.DelhiveryApplication.DelhiveryApplication.Data;

import com.DelhiveryApplication.DelhiveryApplication.Utils.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    @JsonIgnore
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    @JsonIgnore
    private User receiver;

    @ManyToOne
    @JoinColumn(name = "agent")
    @JsonIgnore
    private DelhiveryAgent agent;

    private String pickupAddress;

    private String dropAddress;

    @CreationTimestamp
    private LocalDateTime placedAt;

    private LocalDateTime droppedAt;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private String instructions;

    private String pickupOtp;

    private String dropOtp;

    private LocalDateTime pickupTime;

    private LocalDateTime dropTime;

}
