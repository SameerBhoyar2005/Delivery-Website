package com.DelhiveryApplication.DelhiveryApplication.Controller;

import com.DelhiveryApplication.DelhiveryApplication.Data.*;
import com.DelhiveryApplication.DelhiveryApplication.Repository.AgentRepository;
import com.DelhiveryApplication.DelhiveryApplication.Repository.OrderRepository;
import com.DelhiveryApplication.DelhiveryApplication.Repository.UserRepository;
import com.DelhiveryApplication.DelhiveryApplication.Security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class Login {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AgentRepository agentRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;


    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody LoginDTO login){
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getEmail(),
                        login.getPassword()));
        if (authentication.isAuthenticated()) {
            UserDetails userDetails  = (UserDetails) authentication.getPrincipal();

            assert userDetails != null;

            String role = userDetails.getAuthorities()
                    .iterator()
                    .next()
                    .getAuthority();
            Long id;
            String type;
            List<Orders> orders;

            assert role != null;
            if (role.equals("ROLE_AGENT")) {
               DelhiveryAgent agent = agentRepository.findByEmail(login.getEmail());
                id = agent.getId();
                orders = orderRepository.findAllByAgent(agent);
                type = "AGENT";
            } else {
                User user = userRepository.findByEmail(login.getEmail());
                id = user.getId();
                orders = orderRepository.findOrdersBySenderOrReceiver(id);
                type = "USER";
            }

            String token = jwtService.generateToken(
                    userDetails.getUsername(),
                    role,
                    id,
                    type
            );
            LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
            loginResponseDTO.setJwt(token);
            loginResponseDTO.setOrders(orders);

            return new ResponseEntity<>(loginResponseDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>("Unsuccessfull",HttpStatus.FORBIDDEN);
    }
}
