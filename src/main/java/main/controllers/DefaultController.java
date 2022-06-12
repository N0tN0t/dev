package main.controllers;

import main.api.response.InitResponse;
import main.respositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DefaultController {
    private final InitResponse initResponse;

    public DefaultController(InitResponse initResponse) {
        this.initResponse = initResponse;
    }

    @RequestMapping("/")
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
    @RequestMapping(method = {RequestMethod.OPTIONS, RequestMethod.GET}, value = "/**/{path:[^\\.]*}")
    public String redirectToIndex() {
        return "index";
    }
}
