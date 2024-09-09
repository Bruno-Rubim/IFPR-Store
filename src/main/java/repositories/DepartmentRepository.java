package repositories;

import connection.ConnectionFactory;
import exceptions.DatabaseException;
import exceptions.DatabaseIntegrityException;
import models.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentRepository {

    Connection connection;

    public DepartmentRepository(){
        connection = ConnectionFactory.getConnection();
    }


    public List<Department> getAll() {

        List<Department> departments = new ArrayList<>();

        String sql = "SELECT * FROM department";

        try {

            Statement statement = connection.createStatement();

            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {

                Department department = new Department();
                department.setId(result.getInt("Id"));
                department.setName(result.getString("Name"));
                departments.add(department);
            }


        }catch (SQLException e){
            throw new DatabaseException(e.getMessage());
        }

        return departments;

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

    public Department insert(Department department) {

        String sql = "INSERT INTO department (Name) " +
                "VALUES(?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, department.getName());

            Integer rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {

                ResultSet id = statement.getGeneratedKeys();

                id.next();

                Integer departmentId = id.getInt(1);

                System.out.println("Rows inserted: " + rowsInserted);
                System.out.println("Id: " + departmentId);

                department.setId(departmentId);

            }

        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }

        return department;
    }

    public void update(Department department) {
        String sql = "UPDATE department SET Name = ? WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, department.getName());
            statement.setInt(2, department.getId());

            int rowsAffected = statement.executeUpdate();

            System.out.println("Rows affected: " + rowsAffected);

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

    }

    public Department getById(Integer id) {

        Department department;

        String sql = "SELECT * FROM department WHERE department.Id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                department = this.instantiateDepartment(resultSet);

            } else {
                throw new DatabaseException("Departamento não encontrado");
            }

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

        return department;
    }

    public Department instantiateDepartment(ResultSet resultSet) throws SQLException {

        Department department = new Department();

        System.out.println(resultSet.getMetaData());
        department.setId(Integer.parseInt(resultSet.getString("id")));
        department.setName(resultSet.getString("name"));

        return department;
    }

}