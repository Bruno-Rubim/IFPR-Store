package repositories;

import connection.ConnectionFactory;
import exceptions.DatabaseIntegrityException;
import models.Department;
import models.Seller;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.*;

public class DepartmentRepository {

    Connection connection;

    public DepartmentRepository(){
        connection = ConnectionFactory.getConnection();
    }

    public void delete(Integer id){

        String sql = "DELETE FROM department WHERE Id = ?";

        try {

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            Integer rowsDeleted = statement.executeUpdate();

            if(rowsDeleted > 0){
                System.out.println("Rows deleted: " + rowsDeleted);
            }

        } catch (Exception e){
            throw new DatabaseIntegrityException(e.getMessage());
        }

    }

    public List<Department> getDepartments(){
        List<Department> departments = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM department");
            while (result.next()) {
                Department department = instantiateDepartment(result);
                departments.add(department);
            }
            result.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionFactory.closeConnection();
        }
        return departments;
    }

    public Department instantiateDepartment(ResultSet resultSet) throws SQLException {
        Department department = new Department();
        department.setId(resultSet.getInt("Id"));
        department.setName(resultSet.getString("Name"));
        return department;
    }
}