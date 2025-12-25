package com.DelhiveryApplication.DelhiveryApplication.Service;

import com.DelhiveryApplication.DelhiveryApplication.Data.DelhiveryAgent;
import com.DelhiveryApplication.DelhiveryApplication.Repository.AgentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgentDetailsServiceImpl implements UserDetailsService {
    private final AgentRepository agentRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        DelhiveryAgent agent = agentRepository.findByEmail(email);
        if (agent != null){
            return org.springframework.security.core.userdetails.User.builder()
                    .username(agent.getEmail())
                    .password(agent.getPassword())
                    .authorities("ROLE_AGENT")
                    .build();
        }
        throw  new UsernameNotFoundException("Agent not found");
    }
}
