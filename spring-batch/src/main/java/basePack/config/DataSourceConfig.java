package basePack.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

@Configuration
public class DataSourceConfig { // Renamed to avoid conflict with javax.sql.DataSource

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
            .driverClassName("com.mysql.cj.jdbc.Driver")
            .url("jdbc:mysql://localhost:3306/springboot")
            .username("root")
            .password("") // Set a password if needed
            .build();
    }
}
