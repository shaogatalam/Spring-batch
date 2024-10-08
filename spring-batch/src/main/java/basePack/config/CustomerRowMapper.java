package basePack.config;

import basePack.entity.Customer;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRowMapper implements RowMapper<Customer> {

    @Override
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Customer cus = new Customer();
        cus.setId(rs.getLong("id"));
        cus.setName(rs.getString("name"));
        cus.setEmail(rs.getString("email"));
        cus.setAddress(rs.getString("address"));
        cus.setRevenue(rs.getInt("revenue"));
        return cus;
    }
}
