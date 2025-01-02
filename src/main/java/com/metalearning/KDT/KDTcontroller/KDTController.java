package com.metalearning.KDT.KDTcontroller;

import com.metalearning.KDT.KDTservice.KdtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class KDTController {
    private final KdtService kdtService;
}
