package com.example.library_spring_boot.controller;

import com.example.library_spring_boot.model.BookModel;
import com.example.library_spring_boot.model.BookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/html/library")
public class BookHtmlController {

    private final BookRepository bookRepository;

    public BookHtmlController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("")
    public String getAllBooks(Model model) {
        List<BookModel> books = bookRepository.findAll();
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("/{id}")
    public ModelAndView getBookById(@PathVariable Long id) {
        Optional<BookModel> bookOptional = bookRepository.findById(id);
        ModelAndView mav = new ModelAndView();
        if (bookOptional.isPresent()) {
            mav.setViewName("Book");
            mav.addObject("book", bookOptional.get());
        } else {
            mav.setViewName("Error");
            mav.addObject("message", "ERROR. BOOK NOT FOUND.");
        }
        return mav;
    }



    @GetMapping("/category/{category}")
    public ModelAndView getBooksByCategory(@PathVariable String category) {
        List<BookModel> books = bookRepository.findByCategory(category);
        ModelAndView mav = new ModelAndView("Books");
        mav.addObject("title", "Books in category: " + category);
        mav.addObject("books", books);
        return mav;
    }

    @GetMapping("/author/{author}")
    public ModelAndView getBooksByAuthor(@PathVariable String author) {
        List<BookModel> books = bookRepository.findByAuthor(author);
        ModelAndView mav = new ModelAndView("Books");
        mav.addObject("title", "Books by author: " + author);
        mav.addObject("books", books);
        return mav;
    }


}
