package com.bmt.MyStore.Repositories;

import com.bmt.MyStore.models.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ClientsRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Client> getClients(){
        var clients = new ArrayList<Client>();

        String sql = "SELECT * FROM clients ORDER BY id DESC";
        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql);

        while (rows.next()){
            Client client = new Client();
            client.setId(rows.getInt("id"));
            client.setFirstName(rows.getString("first_name"));
            client.setLastName(rows.getString("last_name"));
            client.setEmail(rows.getString("email"));
            client.setPhone(rows.getString("phone"));
            client.setAddress(rows.getString("address"));
            client.setCreatedAt(rows.getString("created_at"));

            clients.add(client);
        }

        return clients;
    }

    public Client getClient(int id){

        String sql = "SELECT * FROM clients WHERE id=?";
        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, id);

        while (rows.next()){
            Client client = new Client();
            client.setId(rows.getInt("id"));
            client.setFirstName(rows.getString("first_name"));
            client.setLastName(rows.getString("last_name"));
            client.setEmail(rows.getString("email"));
            client.setPhone(rows.getString("phone"));
            client.setAddress(rows.getString("address"));
            client.setCreatedAt(rows.getString("created_at"));

            return client;
        }

        return null;
    }


    public Client getClient(String email){

        String sql = "SELECT * FROM clients WHERE email=?";
        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, email);

        while (rows.next()){
            Client client = new Client();
            client.setId(rows.getInt("id"));
            client.setFirstName(rows.getString("first_name"));
            client.setLastName(rows.getString("last_name"));
            client.setEmail(rows.getString("email"));
            client.setPhone(rows.getString("phone"));
            client.setAddress(rows.getString("address"));
            client.setCreatedAt(rows.getString("created_at"));

            return client;
        }

        return null;
    }

    public Client createClient(Client client){
        String sql = "INSERT INTO clients (first_name, last_name, email, phone, address, created_at) " +
                 "VALUES (?, ?, ?, ?, ?, ?)";

        int count = jdbcTemplate.update(sql, client.getFirstName(), client.getLastName()
                , client.getEmail(), client.getPhone(), client.getAddress(), client.getCreatedAt());

        if (count > 0) {
            int id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID", Integer.class);
            return getClient(id);
        }
        return  null;
    }

    public Client updateClient(Client client) {
        String sql = "UPDATE clients SET first_name=?, last_name=?, email=?, phone=?, address=?, created_at=?) " +
                "WHERE id=?";
        jdbcTemplate.update(sql, client.getFirstName(), client.getLastName()
                , client.getEmail(), client.getPhone(), client.getAddress(), client.getCreatedAt()
                , client.getId());
        return getClient(client.getId());
    }

    public void deleteClient(int id){
        String sql = "DELETE FROM clients WHERE id=?";
        jdbcTemplate.update(sql, id);
    }
}
