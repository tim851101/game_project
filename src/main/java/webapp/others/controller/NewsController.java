package webapp.others.controller;

import com.google.gson.Gson;
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
        return newsRepository.save(addnews);
    }

    @GetMapping("/list-all")
    public List<News> getfindAll() {
        return newsRepository.findAll();
    }

    @DeleteMapping("/delete-one/{id}")
    public String deleteNewsById(@PathVariable String id){
        String keyId=HASH_KEY + ":"+id;
        if (newsRepository.deleteNewsById(keyId))
            return new Gson().toJson("刪除成功");
        else
            return new Gson().toJson("刪除失敗");
    }

    @GetMapping("/get-one/{id}")
    public News getNewsById(@PathVariable String id){
        return newsRepository.findNewsById(id);
    }

    @PostMapping("/find-one-and-update")
    public News updateNewsById(@RequestBody NewsDTO news){
        News addnews = new News();
        addnews.setNewsTitle(news.getNewsTitle());
        addnews.setNewsDesc(news.getNewsDesc());
        addnews.setDueDate(news.getDueDate());
        System.out.println(news.getOldNewsTitle().equals(news.getNewsTitle()));
        if (news.getOldNewsTitle().equals(news.getNewsTitle())) {
            return newsRepository.save(addnews);
        }else {
            newsRepository.deleteNewsById(HASH_KEY + ":"+news.getOldNewsTitle());
            return newsRepository.save(addnews);
        }
    }

}

