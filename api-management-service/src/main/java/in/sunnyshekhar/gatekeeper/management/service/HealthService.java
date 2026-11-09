package in.sunnyshekhar.gatekeeper.management.service;

import org.springframework.data.jpa.repository.query.JpqlQueryBuilder.Select;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;


@Service 
@RequiredArgsConstructor 
public class HealthService {
    private final JdbcTemplate jdbcTemplate;

    public String getHealth(){
        String databaseStatus="DOWN";

        try{
            Integer result=jdbcTemplate.queryForObject("SELECT 1",Integer.class);

            if(Integer.valueOf(1).equals(result)) databaseStatus="UP";
        }catch(Exception e){
            System.out.println("MySQL health check failed "+e.getMessage());
        }

        return databaseStatus;
    }

}
