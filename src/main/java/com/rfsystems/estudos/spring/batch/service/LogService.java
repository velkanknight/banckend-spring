package com.rfsystems.estudos.spring.batch.service;


import com.rfsystems.estudos.spring.batch.model.GraphicIpDTO;
import com.rfsystems.estudos.spring.batch.model.GraphicUserAgentDTO;
import com.rfsystems.estudos.spring.batch.model.LogEntity;
import com.rfsystems.estudos.spring.batch.repository.LogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LogService {

    @Autowired
    private LogRepo logRepo;

    public LogEntity save(LogEntity log){
        return logRepo.save(log);
    }

    public Page<LogEntity> findAll(Pageable pageable){
        return logRepo.findAll(pageable);
    }

    public void remove(LogEntity log){
        logRepo.delete(log);
    }

    public Optional<LogEntity> findBy(Long id){
        return logRepo.findById(id);
    }

    public Optional<LogEntity> find(Long id) {
        return logRepo.findById(id);
    }

    public List<GraphicIpDTO> findGraphicIp(){
        return logRepo.findIpByRequests();
    };

    public List<GraphicUserAgentDTO> findGraphicUserAgent(){
        return logRepo.findUserByRequests();
    };

    public List<LogEntity> pequisa(LocalDateTime inicio, LocalDateTime fim, String ip){
        if(ip == null && inicio != null && fim != null){
            return logRepo.findByDataBetween(inicio, fim);
        } else if(ip != null && inicio == null && fim == null){
            return  logRepo.findByIp(ip);
        } else if (ip != null && inicio != null && fim != null) {
            return logRepo.findByDataBetweenAndIp(inicio, fim, ip);
        }
        return  new ArrayList<>();
    }
}
