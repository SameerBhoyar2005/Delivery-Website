package com.DelhiveryApplication.DelhiveryApplication.Repository;

import com.DelhiveryApplication.DelhiveryApplication.Data.DelhiveryAgent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentRepository extends JpaRepository<DelhiveryAgent,Long> {

    DelhiveryAgent findByEmail(String email);
}
