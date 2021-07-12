package main;

import main.api.response.InitResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultController {
    @Autowired
    TaskRepository taskRepository;
    private final InitResponse initResponse;

    public DefaultController(InitResponse initResponse) {
        this.initResponse = initResponse;
    }

    @RequestMapping("/posts/")
    public String index(Model model) {
/*        Iterable<Posts> postIterable = taskRepository.findAll();
        ArrayList<Posts> posts = new ArrayList<>();
        for(Posts post : postIterable) {
            posts.add(post);
        }
        model.addAttribute("posts",posts);
        model.addAttribute("postsCount",posts.size());
                model.addAttribute("someParameter",someParameter);*/
        return "index";
    }
}
