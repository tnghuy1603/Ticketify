package project.intro2se.ticketify.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.intro2se.ticketify.domain.Theater;
import project.intro2se.ticketify.repository.TheaterRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TheaterService {
    private final TheaterRepository theaterRepository;
    public List<Theater> findAll(){
        List<Theater> theaters =  theaterRepository.findAll();
        log.info(theaters.get(1).toString());
        return theaters;
    }
}
