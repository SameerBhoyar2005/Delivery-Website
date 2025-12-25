package com.DelhiveryApplication.DelhiveryApplication.Repository;

import com.DelhiveryApplication.DelhiveryApplication.Utils.OrderStatus;
import com.DelhiveryApplication.DelhiveryApplication.Data.DelhiveryAgent;
import com.DelhiveryApplication.DelhiveryApplication.Data.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Long> {

    List<Orders> findAllBystatus(OrderStatus orderStatus);

    List<Orders> findAllByAgent(DelhiveryAgent agent);

    @Query("""
    SELECT o
    FROM Orders o
    WHERE o.sender.id = :userId
       OR o.receiver.id = :userId
    """)
    List<Orders> findOrdersBySenderOrReceiver(@Param("userId") Long userId);
}

