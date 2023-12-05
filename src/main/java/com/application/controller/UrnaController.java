package com.application.controller;

import com.application.domain.TabCandidatos;
import com.application.domain.dto.VotesDTO;
import com.application.service.UrnaService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/candidato")
@RequiredArgsConstructor
public class UrnaController {

    @Autowired
    private final UrnaService urnaService;

    @GetMapping("/{canNumNumero}/{canVldTipo}")
    public ResponseEntity<TabCandidatos> findByIdTabUser(@PathVariable @NotNull Long canNumNumero, @PathVariable @NotNull Long canVldTipo) throws Exception {
        return urnaService.findCandidato(canNumNumero, canVldTipo);
    }

    @PostMapping("/register/votes")
    public ResponseEntity registerVotes(@RequestBody @Valid VotesDTO votes) throws Exception {
        return urnaService.registerVotes(votes);
    }

}