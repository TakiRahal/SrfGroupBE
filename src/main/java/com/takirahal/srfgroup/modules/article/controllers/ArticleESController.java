package com.takirahal.srfgroup.modules.article.controllers;

import com.takirahal.srfgroup.modules.article.entities.Article;
import com.takirahal.srfgroup.modules.article.repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/article")
public class ArticleESController {

    @Autowired
    ArticleRepository articleRepository;

    @PostMapping("public/create")
    public Article createArticle(Article article){

        return articleRepository.save(article);
    }

    /**
     * {@code SEARCH /_search/users/:query} : search for the User corresponding to the query.
     *
     * @return the result of the search.
     */
    @GetMapping("public/search")
    public Page<Article> search(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }
}
