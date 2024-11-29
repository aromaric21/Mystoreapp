package com.bmt.MyStore.controllers;

import com.bmt.MyStore.Repositories.ClientsRepository;
import com.bmt.MyStore.models.Client;
import com.bmt.MyStore.models.ClientDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/clients")
public class ClientsController {

    @Autowired
    private ClientsRepository clientsRepository;

    private final int pageSize = 5;

    @GetMapping
    public String getClients(Model model, @RequestParam(defaultValue = "1") int pageIndex) {
        var page = clientsRepository.getClients(pageIndex, pageSize);
        model.addAttribute("page", page);
        return "clients/index";
    }

    @GetMapping("/create")
    public String showCreatePage(Model model){
        ClientDto clientDto = new ClientDto();
        model.addAttribute("clientDto", clientDto);
        return "clients/create";
    }

    @PostMapping("/create")
    public String createClient(
            @Valid @ModelAttribute ClientDto clientDto, BindingResult result
    ){
        if (clientsRepository.getClient(clientDto.getEmail()) != null){
            result.addError(
                    new FieldError("clientDto", "email",clientDto.getEmail()
                            ,false, null, null,"Email address is already used")
            );
        }

        if (result.hasErrors()){
            return "clients/create";
        }

        Client client = new Client();
        client.setFirstName(clientDto.getFirstName());
        client.setLastName(clientDto.getLastName());
        client.setEmail(clientDto.getEmail());
        client.setPhone(clientDto.getPhone());
        client.setAddress(clientDto.getAddress());
        client.setCreatedAt(new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date()));

        clientsRepository.createClient(client);

        return "redirect:/clients";
    }

    @GetMapping("/edit")
    public String showEditPage(Model model, @RequestParam int id){
        Client client = clientsRepository.getClient(id);
        if (client == null){
            return "redirect:/clients";
        }

        model.addAttribute("client", client);

        ClientDto clientDto = new ClientDto();
        clientDto.setFirstName(client.getFirstName());
        clientDto.setLastName(client.getLastName());
        clientDto.setEmail(client.getEmail());
        clientDto.setPhone(client.getPhone());
        clientDto.setAddress(client.getAddress());

        model.addAttribute("client", client);
        model.addAttribute("clientDto", clientDto);

        return "clients/edit";
    }

    @PostMapping("/edit")
    public String editClient(
            Model model, @RequestParam int id,
            @Valid @ModelAttribute ClientDto clientDto,
            BindingResult result
    ){

        Client client = clientsRepository.getClient(id);
        if (client == null){
            return "redirect:/clients";
        }

        model.addAttribute("client", client);

        if (result.hasErrors()){
            return "clients/edit";
        }

        // Update client details
        client.setFirstName(clientDto.getFirstName());
        client.setLastName(clientDto.getLastName());
        client.setEmail(clientDto.getEmail());
        client.setPhone(clientDto.getPhone());
        client.setAddress(clientDto.getAddress());

        clientsRepository.updateClient(client);

        return "redirect:/clients";
    }

    @GetMapping("/delete")
    public String deleteClient(@RequestParam int id){

        clientsRepository.deleteClient(id);
        return "redirect:/clients";
    }

}
