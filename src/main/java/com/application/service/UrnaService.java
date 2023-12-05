package com.application.service;

import com.application.config.FbrRequestData;
import com.application.config.ListRequest;
import com.application.domain.TabCandidatos;
import com.application.domain.TabUser;
import com.application.domain.dto.VotesDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Validated
public class UrnaService {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    FbrRequestData requestData;

    @Transactional
    public ResponseEntity<TabUser> findByIdTabUser(Long usnCod) throws Exception {
        return ResponseEntity.ok().body(TabUser.findById(usnCod));
    }

    @Transactional
    public ResponseEntity<TabCandidatos> findCandidato(Long canNumNumero, Long canVldTipo) throws Exception {
        //O numero do canditado é unico, então o findList smp retorna 1.
        return ResponseEntity.ok().body(TabCandidatos.findList(ListRequest.createListRequest() //
                        .addFilter("canNumNumero", canNumNumero) //
                        .addFilter("canVldTipo", canVldTipo)) //
                .get(0));
    }

    @Transactional
    public ResponseEntity registerVotes(VotesDTO votes) throws Exception {
        var user = TabUser.findById(requestData.getUsnCod());

        if (user.getUsnVldVotou().equals(1L))
            throw new RuntimeException("Usuario já votou.");

        if (votes.getVotes().size() > 3)
            throw new RuntimeException("Voto Invalido, permitido somente 2 votos, senador e presidente.");

        //Não permite q o usuario vote duas vezes no mesmo candidato, considera NULO.
        var votos = votes.getVotes().stream()
                .distinct()
                .collect(Collectors.toList());

        for (Long i : votos) {
            var candidato = TabCandidatos.findList(ListRequest.createListRequest().addFilter("canCod", i));
            if (!candidato.isEmpty()) {
                var tmpCandidato = candidato.get(0);
                tmpCandidato.setCanNumVotos(tmpCandidato.getCanNumVotos() + 1);
                tmpCandidato.update();
            }
        }


        user.setUsnVldVotou(1L);
        user.update();

        return ResponseEntity.ok().build();
    }
}
