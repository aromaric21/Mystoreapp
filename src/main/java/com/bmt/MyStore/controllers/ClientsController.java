package com.bmt.MyStore.controllers;

import com.bmt.MyStore.Repositories.ClientsRepository;
import com.bmt.MyStore.models.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/clients")
public class ClientsController {

    @Autowired
    private ClientsRepository clientsRepository;

    @GetMapping
    public String getClients(Model model) {
        List<Client> clients = clientsRepository.getClients();
        model.addAttribute("clients", clients);
        return "clients/index";
    }
}
