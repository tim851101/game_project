package webapp.others.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import webapp.others.dto.NewsDTO;
import webapp.others.pojo.News;
import webapp.others.repository.NewsRepository;

import java.util.List;

import static webapp.others.repository.NewsRepository.HASH_KEY;

@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsRepository newsRepository;

    @PostMapping("/add-one")
    public News save(@Validated @RequestBody NewsDTO news){
        News addnews = new News();
        addnews.setNewsTitle(news.getNewsTitle());
        addnews.setNewsDesc(news.getNewsDesc());
        addnews.setDueDate(news.getDueDate());
        Long setSecs= news.getSeconds();
        return newsRepository.save(addnews,(Long) setSecs);
    }

    @GetMapping("/list-all")
    public List<News> getfindAll() {
        return newsRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public String deleteNewsById(@PathVariable String id){
        String keyId=HASH_KEY + ":"+id;
        return newsRepository.deleteNewsById(keyId);
    }


    @GetMapping("/get-one/{id}")
    public News getNewsById(@PathVariable String id){
        System.out.println(id);
//        String keyId=HASH_KEY + ":"+id;
//        System.out.println(keyId);
        return newsRepository.findNewsById(id);
    }
}

