package com.metalearning.KDT.KDTcontroller;

import com.metalearning.KDT.KDTservice.KdtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor

public class KDTRestController {
    private final KdtService kdtService;



}



