package com.joaocarv05.teste_tecnico_picpay.controllers;

import com.joaocarv05.teste_tecnico_picpay.domain.transfer.Transfer;
import com.joaocarv05.teste_tecnico_picpay.domain.transfer.TransferDTO;
import com.joaocarv05.teste_tecnico_picpay.services.TransferService;
import com.joaocarv05.teste_tecnico_picpay.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transfer")
public class TransferController {
    @Autowired
    TransferService transferService;
    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<Transfer> createTransfer(@RequestBody TransferDTO transferDTO){
        Transfer transfer = transferService.createTransferService(transferDTO);
        return ResponseEntity.ok(transfer);
    }
}
