package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Project;
import util.ConnectionFactory;
/**
 *
 * @author wht_l
 */

public class ProjectController {
    
    public void save(Project project) {
        String sql = "INSERT INTO projects (name, description, "
                + "createdAt, updatedAt) "
                + "VALUES (?, ?, ?, ?)";

            Connection connection = null;
            PreparedStatement statement = null;

        try {
            //Cria uma conexão com o banco
            connection = ConnectionFactory.getConnection();
            //Cria um PreparedStatment, classe usada para executar a query
            statement = connection.prepareStatement(sql);
            
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));
            
            //Executa a sql para inserção dos dados
            statement.execute();

        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao salvar o projeto "
                    + ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }

    public void update(Project project) {
        
        String sql = "UPDATE projects SET "
                + "name = ?, description = ?,"                
                + "createdAt = ?, updatedAt = ?, WHERE id = ?";

            Connection connection = null;
            PreparedStatement statement = null;

        try {
            //Cria uma conexão com o banco de dados
            connection = ConnectionFactory.getConnection();
            
            //Cria um PreparedStatment, classe usada para preparar a query
            statement = connection.prepareStatement(sql);
            
            
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());            
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));
            statement.setInt(5, project.getId());
            
            //Executando a sql para inserção dos dados
            statement.execute();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao atualizar o projeto!", ex);
        } finally{
            ConnectionFactory.closeConnection(connection, statement);
        }

    }

    public List<Project> getAll() {
        
        String sql = "SELECT * FROM projects";
        
        List<Project> projects = new ArrayList<>();
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        //Classe que vai recuperar os dados do banco de dados
        ResultSet resultSet = null;       

        try {
            //Criação da conexão
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            
            resultSet = statement.executeQuery();
            
            //Enquanto existir dados no banco de dados, faça
            while (resultSet.next()) {

                Project project = new Project();
                
                project.setId(resultSet.getInt("id"));                
                project.setName(resultSet.getString("name"));
                project.setDescription(resultSet.getString("description"));
                project.setCreatedAt(resultSet.getDate("createdAt"));
                project.setUpdatedAt(resultSet.getDate("updatedAt"));

                projects.add(project);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao buscar os projetos!",ex);
        }finally{
            ConnectionFactory.closeConnection(connection, statement,resultSet);
        }        
        return projects;
    }
    
    public void removeById(int idProject) {
        String sql = "DELETE FROM projects WHERE id = ?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            //Criação da conexão com o banco de dados
            connection = ConnectionFactory.getConnection();
            
            //Preparando a query
            statement = connection.prepareStatement(sql);
            
            //Setando os valores
            statement.setInt(1, idProject);
            
            //Executando a query
            statement.execute();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao deletar o projeto!", ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }
    
}
