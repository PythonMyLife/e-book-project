package backend.Controller;

import backend.Service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/visit")
public class VisitController {
    @Autowired
    private VisitService visitService;

    @GetMapping("/count")
    public Long index() {
        return visitService.getCount();
    }
}
