package com.rfsystems.estudos.spring.batch.controller;

import com.rfsystems.estudos.spring.batch.model.GraphicIpDTO;
import com.rfsystems.estudos.spring.batch.model.GraphicUserAgentDTO;
import com.rfsystems.estudos.spring.batch.model.LogDTO;
import com.rfsystems.estudos.spring.batch.model.LogEntity;
import com.rfsystems.estudos.spring.batch.service.FileService;
import com.rfsystems.estudos.spring.batch.service.LogService;
import com.rfsystems.estudos.spring.batch.utils.DataUtils;
import com.sun.istack.Nullable;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/logs")
@CrossOrigin(origins = "http://localhost:4200")
@Api(value = "API upload file")
public class LogController {

    @Autowired
    private FileService fileService;

    @Autowired
    private LogService logService;

    @PostMapping("/upload")
    public ResponseEntity handleFileUpload(@RequestParam("file") MultipartFile file) {
        System.out.println(file.getOriginalFilename());

        fileService.fileStorage(file);
//        redirectAttributes.addFlashAttribute("message",
//                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return ResponseEntity.status(HttpStatus.OK).body("File: " +  file.getOriginalFilename() + " received!");
    }



    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<LogDTO> addLog(@RequestBody LogDTO log) {

        LogEntity logEntity = LogEntity.convertFrom(log);
        LogEntity saved = logService.save(logEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(LogDTO.convertFrom(saved));
    }

    @PutMapping(value ="{id}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<LogDTO> updateLog(@PathVariable("id")Long id, @RequestBody LogDTO log) {

        LogEntity logEntity = LogEntity.convertFrom(log);
        LogEntity saved = logService.save(logEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(LogDTO.convertFrom(saved));
    }


    @DeleteMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<LogDTO> removeLog(Long id) {

        Optional<LogEntity> optionalLogEntity = logService.findBy(id);
        if(optionalLogEntity.isPresent()){
            logService.remove(optionalLogEntity.get());
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
//
    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Page<LogEntity>> addMember(@RequestParam(defaultValue = "0") Integer pageNo,
                                                     @RequestParam(defaultValue = "20") Integer pageSize) {
        pageSize = limitPageSize(pageSize);
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("data"));
        Page<LogEntity> logEntities = logService.findAll(paging);
        if(logEntities.getTotalElements() > 0){
            return ResponseEntity.status(HttpStatus.OK).body(logEntities);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(logEntities);
        }
    }

    @GetMapping(value = "/ips", produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<List<GraphicIpDTO>> getIsByRequest() {
        List<GraphicIpDTO> graphicIp = logService.findGraphicIp();
        return  ResponseEntity.ok().body(graphicIp);
    }

    @GetMapping(value = "/users", produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<List<GraphicUserAgentDTO>> getUserbyRequest() {
        List<GraphicUserAgentDTO> graphicIp = logService.findGraphicUserAgent();
        return  ResponseEntity.ok().body(graphicIp);
    }

    @GetMapping(value = "/search", produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<List<LogEntity>> pesquisa(@RequestParam(value = "datade", required = false) String datade,
                                                    @RequestParam(value="dataate", required = false) String dataate,
                                                     @RequestParam(value="ip", required = false) String ip) {

        List<LogEntity> logEntities = logService.pequisa(DataUtils.convertFrom(datade), DataUtils.convertFrom(dataate), ip);
        if(logEntities.size() > 0){
            return ResponseEntity.status(HttpStatus.OK).body(logEntities);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(logEntities);
        }
    }



    @GetMapping(value ="{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<LogEntity> find(@PathVariable Long id) {
        Optional<LogEntity> log = logService.find(id);
        if(log.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(log.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    private int limitPageSize(Integer size){
        if(size > 20){ return 20; }
        return size;
    }

}
